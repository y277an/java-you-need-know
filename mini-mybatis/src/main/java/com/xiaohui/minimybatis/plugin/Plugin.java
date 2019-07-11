package com.xiaohui.minimybatis.plugin;


import com.xiaohui.minimybatis.executor.Executor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: xiaohui
 * @Description: mybatis的插件模块
 */
public class Plugin implements InvocationHandler {

    private Object                               target;
    private Interceptor interceptor;

    private Plugin(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    /**
     * 源码中是需要进行签名校验的，本工程为了简洁不作检验。
     */
    public static Object wrap(Object target, Interceptor interceptor) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),new Class[]{Executor.class},new Plugin(target,interceptor));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.interceptor.intercept(new Invocation(target,method,args));
    }

}
