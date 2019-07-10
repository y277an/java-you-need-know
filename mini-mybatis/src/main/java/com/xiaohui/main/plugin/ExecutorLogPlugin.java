package com.xiaohui.main.plugin;

import com.xiaohui.minimybatis.binding.MapperData;
import com.xiaohui.minimybatis.plugin.Interceptor;
import com.xiaohui.minimybatis.plugin.Intercepts;
import com.xiaohui.minimybatis.plugin.Invocation;
import com.xiaohui.minimybatis.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * <p>执行日志输出插件</p>
 *
 * @author grand 2018/6/21
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人}
 * @modify by reason:{方法名}:{原因}
 */
@Intercepts({})
public class ExecutorLogPlugin implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(ExecutorLogPlugin.class);


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MapperData mapperData = (MapperData)invocation.getArgs()[0];
        Object[] parameter = (Object[])invocation.getArgs()[1];
        logger.info("ExecutorLogPlugin is in processing....");
        logger.info("mapperData is :"+ mapperData);
        for (int i=0;i<parameter.length;i++) {
            logger.info("parameter "+ i +" is　:" + parameter[i]);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object var1) {
        return Plugin.wrap(var1,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
