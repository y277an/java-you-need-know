package com.xiaohui.ioc.beans.aware;

import com.xiaohui.ioc.beans.factory.BeanDefinitionRegistry;

public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanDefinitionRegistry beanDefinitionRegistry);
}
