package com.xiaohui.netty.constant;

/**
 * @Author: xiaohui
 * @Description:
 * @Date: 2019/6/2 08:49
 */
public class CodeMsg {
    private int    code;
    private String msg;

    // 通用的错误码
    public static CodeMsg SUCCESS      = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(1, "服务端异常");

    private CodeMsg() {
    }

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
