package com.xiaohui.netty.server;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.bcel.internal.classfile.Code;
import com.xiaohui.entity.Request;
import com.xiaohui.entity.Response;
import com.xiaohui.netty.constant.CodeMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author: xiaohui
 * @Description: Netty服务器的handler
 * @Date: 2019/6/2 8:09
 */
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private final Map<String, Object> serviceMap;

    public NettyServerHandler(Map<String, Object> serviceMap) {
        this.serviceMap = serviceMap;
    }

    /**
     * netty连接成功后的回调
     *
     * @param ctx
     */
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("客户端连接成功!" + ctx.channel().remoteAddress());
    }

    /**
     * netty失去连接后的回调
     *
     * @param ctx
     */
    public void channelInactive(ChannelHandlerContext ctx) {
        logger.info("客户端断开连接!{}", ctx.channel().remoteAddress());
        ctx.channel().close();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Request request = JSON.parseObject(msg.toString(), Request.class);

        if ("heartBeat".equals(request.getMethodName())) {
            logger.info("客户端心跳信息..." + ctx.channel().remoteAddress());
        } else {
            logger.info("RPC客户端请求接口:" + request.getClassName() + "   方法名:" + request.getMethodName());
            Response response = new Response();
            response.setRequestId(request.getId());
            try {
                Object result = this.handler(request);
                response.setCode(CodeMsg.SUCCESS.getCode());
                response.setData(result);
            } catch (Throwable e) {
                e.printStackTrace();
                response.setCode(CodeMsg.SERVER_ERROR.getCode());
                response.setError_msg(e.toString());
                logger.error(CodeMsg.SERVER_ERROR.getMsg(), e);
            }
            ctx.writeAndFlush(response);
        }
    }

    /**
     * 通过反射，执行本地方法
     *
     * @param request
     * @return
     * @throws Throwable
     */
    private Object handler(Request request) throws Throwable {
        String className = request.getClassName();
        Object serviceBean = serviceMap.get(className);

        if (serviceBean != null) {
            Class<?> serviceClass = serviceBean.getClass();
            String methodName = request.getMethodName();
            Class<?>[] parameterTypes = request.getParameterTypes();
            Object[] parameters = request.getParameters();

            Method method = serviceClass.getMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(serviceBean, getParameters(parameterTypes, parameters));
        } else {
            throw new Exception("未找到服务接口,请检查配置!:" + className + "#" + request.getMethodName());
        }
    }

    /**
     * 获取参数列表
     *
     * @param parameterTypes
     * @param parameters
     * @return
     */
    private Object[] getParameters(Class<?>[] parameterTypes, Object[] parameters) {
        if (parameters == null || parameters.length == 0) {
            return parameters;
        } else {
            Object[] new_parameters = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                new_parameters[i] = JSON.parseObject(parameters[i].toString(), parameterTypes[i]);
            }
            return new_parameters;
        }
    }

    /**
     * 心跳检测条件满足后触发
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
                logger.info("客户端已超过60秒未读写数据,关闭连接.{}", ctx.channel().remoteAddress());
                ctx.channel().close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * 只会catch inbound handler的exception. outbound exceptions 需要在writeAndFlush方法里加上listener来监听消息是否发送成功，
     * 生产级别代码，最好在每个outbound handler的处理类里加上try catch
     *
     * @param ctx
     * @param cause
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.info(cause.getMessage());
        ctx.close();
    }
}
