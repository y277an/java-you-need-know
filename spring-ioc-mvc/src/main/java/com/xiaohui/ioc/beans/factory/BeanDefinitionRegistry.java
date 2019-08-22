package com.xiaohui.ioc.beans.factory;

import java.util.Map;

public interface BeanDefinitionRegistry {

    /**
     * 获取工厂内的所有bean集合
     *
     * @return
     */
    Map<String, Object> getBeans();
}
