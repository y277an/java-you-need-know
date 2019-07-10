package com.xiaohui.minimybatis;

import com.xiaohui.minimybatis.config.Configuration;
import com.xiaohui.minimybatis.entity.User;
import com.xiaohui.minimybatis.executor.SimpleExecutor;
import com.xiaohui.minimybatis.mapper.UserMapper;
import com.xiaohui.minimybatis.session.defaults.DefaultSqlSession;

/**
 * @Author: xiaohui
 * @Description: 要先修改config/Configuration的数据库连接配置
 */
public class Bootstrap {

    public static void main(String[] args) {

//        MapperRegistry registory = new MapperRegistry("com.xiaohui.minimybatis.mapper");
//        Map<String, MapperRegistry.MapperData> map = registory.getMethodMaping();
//        for (Map.Entry<String, MapperRegistry.MapperData> entry : map.entrySet()){
//            System.out.println("methodName" + entry.getKey() + "|| sql:" + entry.getValue().getSql()+ "|| className:"
//                    + entry.getValue().getType().getSimpleName());
//        }

        Configuration configuration = new Configuration("com.xiaohui.minimybatis.mapper","com.xiaohui.main.plugin.ExecutorLogPlugin");
        DefaultSqlSession sqlSession = new DefaultSqlSession(configuration);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectByPrimiryKey(1);
        System.out.println(user);

    }

}

