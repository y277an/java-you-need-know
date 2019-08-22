package com.xiaohui.demo;

import com.xiaohui.ioc.beans.factory.annotation.Component;

/**
 * 用于测试的service服务
 */
@Component(value = "testService")
public class TestService {
    public void say(String str) {
        System.out.println(str);
    }
}
