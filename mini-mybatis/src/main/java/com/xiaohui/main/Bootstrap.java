package com.xiaohui.main;

import com.xiaohui.minimybatis.config.Configuration;
import com.xiaohui.main.entity.User;
import com.xiaohui.main.mapper.UserMapper;
import com.xiaohui.minimybatis.session.defaults.DefaultSqlSession;

/**
 * @Author: xiaohui
 * @Description: 。运行前要先修改config/Configuration的数据库连接配置。
 */
public class Bootstrap {

    public static void main(String[] args) {
        Configuration configuration = new Configuration("com.xiaohui.main.mapper","com.xiaohui.main.plugin.ExecutorLogPlugin");
        DefaultSqlSession sqlSession = new DefaultSqlSession(configuration);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectByPrimiryKey(1);
        System.out.println(user);
    }

}

