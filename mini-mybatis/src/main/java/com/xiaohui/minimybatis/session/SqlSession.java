package com.xiaohui.minimybatis.session;

import com.xiaohui.minimybatis.binding.MapperData;
import com.xiaohui.minimybatis.config.Configuration;

import java.sql.Connection;
import java.util.List;

/**
 * @Author: xiaohui
 * @Description:
 */
public interface SqlSession {

    <T> T getMapper(Class<T> var1);

    <T> T selectOne(MapperData mapperData, Object... parameter) throws Exception;

    <E> List<E> selectList(MapperData mapperData, Object... parameter) throws Exception;

    int insert(String var1);

    Configuration getConfiguration();

    Connection getConnection();

}
