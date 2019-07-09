package com.xiaohui.minimybatis;

import com.xiaohui.minimybatis.config.Configuration;
import com.xiaohui.minimybatis.entity.User;
import com.xiaohui.minimybatis.executor.SimpleExecutor;
import com.xiaohui.minimybatis.mapper.UserMapper;
import com.xiaohui.minimybatis.session.DefaultSqlSession;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaohui
 * @Description:
 */
public class Bootstrap {

    public static void main(String[] args) {

        /*MapperRegistory registory = new MapperRegistory("com.xiaohui.minimybatis.mapper");
        Map<String, MapperRegistory.MapperData> map = registory.getMethodMaping();
        for (Map.Entry<String, MapperRegistory.MapperData> entry : map.entrySet()){
            System.out.println("methodName" + entry.getKey() + "|| sql:" + entry.getValue().getSql()+ "|| className:"
                    + entry.getValue().getType().getSimpleName());
        }*/


        Configuration configuration = new Configuration("com.xiaohui.minimybatis.mapper");
        DefaultSqlSession sqlSession = new DefaultSqlSession(configuration, new SimpleExecutor(configuration));
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectByPrimiryKey(1);
        System.out.println(user);

    }

}

