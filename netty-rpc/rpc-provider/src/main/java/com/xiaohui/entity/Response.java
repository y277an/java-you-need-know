package com.xiaohui.entity;


/**
 * @Author: xiaohui
 * @Description: 响应实体类
 * @Date: 2019/6/1 9:26
 */
public class Response {

    private String requestId;//请求的唯一码
    private int    code;//响应状态吗
    private String error_msg;//错误提示
    private Object data;//响应内容

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
