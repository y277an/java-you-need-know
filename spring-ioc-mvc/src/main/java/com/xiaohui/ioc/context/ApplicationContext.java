package com.xiaohui.ioc.context;


import com.xiaohui.ioc.beans.factory.BeanFactory;

/**
 * 应用上下文
 * 1. 以通用的方式加载文件资源的能力。2. 将事件发布到注册监听器的功能。3. 解析消息的能力，支持国际化。4. 从父上下文继承的特性
 */
public interface ApplicationContext extends BeanFactory {

}
