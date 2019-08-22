package com.xiaohui.ioc.beans.factory;



/**
 * 提供IOC容器最基本的形式，为IOC容器的实现提供了规范
 **/
public interface BeanFactory {

    Object getBean(String beanName) throws Exception;
}
