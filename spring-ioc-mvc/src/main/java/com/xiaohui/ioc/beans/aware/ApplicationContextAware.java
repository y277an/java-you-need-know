package com.xiaohui.ioc.beans.aware;


import com.xiaohui.ioc.context.ApplicationContext;

/**
 * 实现该接口的类可以直接获取spring配置文件中的bean对象。
 */
public interface ApplicationContextAware {
    void setApplicationContext(ApplicationContext applicationContext);
}
