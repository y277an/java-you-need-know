package com.xiaohui.ioc.beans.factory.config;

/**
 * Bean的元数据类
 */
public class BeanDefinition {
    private String id;
    private Class  clazz;

    public BeanDefinition(String id, Class clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Object getInstance() {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
