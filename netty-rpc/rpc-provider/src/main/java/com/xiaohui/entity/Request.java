package com.xiaohui.entity;


/**
 * @Author: xiaohui
 * @Description: 请求实体类
 * @Date: 2019/6/1 9:24
 */
public class Request {

    private String     id;//唯一码，雪花算法计算
    private String     className;// 类名
    private String     methodName;// 函数名称
    private Class<?>[] parameterTypes;// 参数类型
    private Object[]   parameters;// 参数列表

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

}
