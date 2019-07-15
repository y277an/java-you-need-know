package com.xiaohui.minimybatis.session;

import com.xiaohui.minimybatis.binding.MappedStatement;
import com.xiaohui.minimybatis.config.Configuration;

import java.sql.Connection;
import java.util.List;

/**
 * @Author: xiaohui
 * @Description: 这是MyBatis主要的一个类，用来执行SQL，获取映射器，管理事务
 *通常情况下，我们在应用程序中使用的Mybatis的API就是这个接口定义的方法。
 */
public interface SqlSession {

    <T> T getMapper(Class<T> var1);

    <T> T selectOne(MappedStatement mapperStatement, Object... parameter) throws Exception;

    <E> List<E> selectList(MappedStatement mapperStatement, Object... parameter) throws Exception;

    int insert(String var1);

    Configuration getConfiguration();

    Connection getConnection();

}
