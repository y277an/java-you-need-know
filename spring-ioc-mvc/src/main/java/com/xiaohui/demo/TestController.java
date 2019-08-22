package com.xiaohui.demo;

import com.xiaohui.ioc.beans.factory.annotation.Autowired;
import com.xiaohui.ioc.beans.factory.annotation.Controller;
import com.xiaohui.ioc.beans.factory.annotation.RequestMapping;
import com.xiaohui.ioc.beans.factory.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于测试的服务器接口
 */
@Controller
public class TestController {

    @Autowired("testService")
    TestService testService;

    @RequestMapping("/test")
    public void test(HttpServletRequest request,
                     HttpServletResponse response,
                     @RequestParam("word") String word) {
        // 服务器控制台输出
        testService.say("receive word: " + word);
        try {
            response.getWriter().print("server received word: " + word);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
