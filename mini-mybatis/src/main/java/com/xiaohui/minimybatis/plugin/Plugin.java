package com.xiaohui.minimybatis.plugin;


import com.xiaohui.minimybatis.executor.Executor;
import com.xiaohui.minimybatis.reflection.ExceptionUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author: xiaohui
 * @Description:
 */
public class Plugin implements InvocationHandler {

    private Object                               target;
    private Interceptor interceptor;

    private Plugin(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object wrap(Object target, Interceptor interceptor) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),new Class[]{Executor.class},new Plugin(target,interceptor));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.interceptor.intercept(new Invocation(target,method,args));
    }

}
