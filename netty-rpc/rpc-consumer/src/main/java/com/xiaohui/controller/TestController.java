package com.xiaohui.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaohui.entity.InfoUser;
import com.xiaohui.service.InfoUserService;
import com.xiaohui.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: xiaohui
 * @Description: 消费者的请求入口，方便测试
 * @Date: 2019/6/1 18:38
 */
@Controller
public class TestController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    InfoUserService userService;

    @GetMapping("test")
    @ResponseBody
    public String index() {
        return "testtest";
    }

    /**
     * 并发插入用户数据
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("insert")
    @ResponseBody
    public List<InfoUser> getUserList() throws InterruptedException {

        long start = System.currentTimeMillis();
        int thread_count = 100;
        CountDownLatch countDownLatch = new CountDownLatch(thread_count);
        for (int i = 0; i < thread_count; i++) {
            new Thread(() -> {
                InfoUser infoUser = new InfoUser(IdUtil.getId(), "beibei", "BeiJing");
                List<InfoUser> users = userService.insertInfoUser(infoUser);
                logger.info("返回用户信息记录:{}", JSON.toJSONString(users));
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        logger.info("线程数：{},执行时间:{}", thread_count, (end - start));
        return null;
    }

    @GetMapping("getById")
    @ResponseBody
    public InfoUser getById(@RequestParam String id) {
        logger.info("根据ID查询用户信息:{}", id);
        return userService.getInfoUserById(id);
    }

    @GetMapping("deleteById")
    @ResponseBody
    public void deleteById(@RequestParam String id) {
        logger.info("根据ID删除用户信息:{}", id);
        userService.deleteInfoUserById(id);
    }

    @GetMapping("getNameById")
    @ResponseBody
    public String getNameById(@RequestParam String id) {
        logger.info("根据ID查询用户名称:{}", id);
        return userService.getNameById(id);
    }

    /**
     * 模拟并发获取所有用户数据
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("getAllUser")
    @ResponseBody
    public Map<String, InfoUser> getAllUser() throws InterruptedException {

        long start = System.currentTimeMillis();
        int thread_count = 2000;
        CountDownLatch countDownLatch = new CountDownLatch(thread_count);
        for (int i = 0; i < thread_count; i++) {
            new Thread(() -> {
                Map<String, InfoUser> allUser = userService.getAllUser();
                logger.info("查询所有用户信息：{}", JSONObject.toJSONString(allUser));
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        logger.info("线程数：{},执行时间:{}", thread_count, (end - start));

        return null;
    }
}
