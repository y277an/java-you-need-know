package com.xiaohui.configurer.rpc;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Proxy;

/**
 * @Author: xiaohui
 * @Description: RPC的工厂bean
 * @Date: 2019/6/1 17:40
 */
public class RpcFactoryBean<T> implements FactoryBean<T> {

    private Class<T> rpcInterface;

    @Autowired
    RpcFactory<T> factory;

    public RpcFactoryBean() {
    }

    public RpcFactoryBean(Class<T> rpcInterface) {
        this.rpcInterface = rpcInterface;
    }
    // 返回的对象实例
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(rpcInterface.getClassLoader(), new Class[]{rpcInterface}, factory);
    }
    // Bean的类型
    public Class<?> getObjectType() {
        return this.rpcInterface;
    }

    public boolean isSingleton() {
        return true;
    }

}
