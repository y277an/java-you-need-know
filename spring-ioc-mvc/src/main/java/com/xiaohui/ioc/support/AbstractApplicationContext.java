package com.xiaohui.ioc.support;



import com.xiaohui.ioc.beans.factory.BeanFactory;
import com.xiaohui.ioc.context.ApplicationContext;


public abstract class AbstractApplicationContext implements ApplicationContext {

    protected BeanFactory beanFactory;

    public AbstractApplicationContext(String location) {
        this.beanFactory = new AnnotationBeanFactory(location);
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        return beanFactory.getBean(beanName);
    }

}
