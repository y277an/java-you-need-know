package com.xiaohui.ioc.beans.factory;

import java.util.Map;

public interface BeanDefinitionRegistry {
    /**
     * 根据id获取bean
     *
     * @param id
     * @return
     */
    Object getBean(String id);

    /**
     * 根据id获取特定类型的bean,完成强转
     *
     * @param id
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getBean(String id, Class<T> clazz);

    /**
     * 获取工厂内的所有bean集合
     *
     * @return
     */
    Map<String, Object> getBeans();
}
