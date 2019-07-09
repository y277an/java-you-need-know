package com.xiaohui.minimybatis.session;

import com.xiaohui.minimybatis.config.Configuration;
import com.xiaohui.minimybatis.config.MapperRegistory;

import java.sql.Connection;
import java.util.List;

/**
 * @Author: xiaohui
 * @Description:
 */
public interface SqlSession {

    <T> T getMapper(Class<T> var1);

    <T> T selectOne(MapperRegistory.MapperData mapperData, Object... parameter) throws Exception;

    <E> List<E> selectList(MapperRegistory.MapperData mapperData, Object... parameter) throws Exception;

    int insert(String var1);

    Configuration getConfiguration();

    Connection getConnection();

}
