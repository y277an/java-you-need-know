package com.xiaohui.ioc.beans.factory;

import com.xiaohui.ioc.beans.factory.config.BeanDefinition;

import java.util.List;

public interface BeanRegister {
    /**
     * 向工厂内注册BeanDefinition
     *
     * @param bds
     */
    void registBeanDefinition(List<BeanDefinition> bds);

    /**
     * 向工厂内注册bean实例对象
     *
     * @param id
     * @param instance
     */
    void registInstanceMapping(String id, Object instance);
}
