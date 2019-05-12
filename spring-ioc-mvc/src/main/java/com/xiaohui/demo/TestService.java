package com.xiaohui.demo;

import com.xiaohui.ioc.annotation.Component;

@Component(value = "testService")
public class TestService {
    public void say(String str) {
        System.out.println(str);
    }
}
