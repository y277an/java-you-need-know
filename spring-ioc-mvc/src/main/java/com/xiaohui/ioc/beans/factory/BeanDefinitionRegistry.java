package com.xiaohui.ioc.beans.factory;

import com.xiaohui.ioc.beans.factory.config.BeanDefinition;

import java.util.Map;

public interface BeanDefinitionRegistry {

    /**
     * 获取工厂内的所有bean集合
     *
     * @return
     */
    Map<String, Object> getBeans();
    /**
     * 注册bean定义到bean工厂
     * @param beanName
     * @param beanName
     */
    void register(String beanName, BeanDefinition beanDefinition);
}
