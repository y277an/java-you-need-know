package com.xiaohui.demo;

import com.xiaohui.ioc.annotation.Autowired;
import com.xiaohui.ioc.annotation.Controller;
import com.xiaohui.ioc.annotation.RequestMapping;
import com.xiaohui.ioc.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
