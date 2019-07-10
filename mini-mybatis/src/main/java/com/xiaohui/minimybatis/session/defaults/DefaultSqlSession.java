package com.xiaohui.minimybatis.session.defaults;

import com.xiaohui.minimybatis.binding.MapperData;
import com.xiaohui.minimybatis.binding.MapperProxy;
import com.xiaohui.minimybatis.config.Configuration;
import com.xiaohui.minimybatis.executor.Executor;
import com.xiaohui.minimybatis.session.SqlSession;
import lombok.Data;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.List;

/**
 * @Author: xiaohui
 * @Description:
 */
@Data
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz}, new MapperProxy(this, clazz));
    }

    @Override
    public <T> T selectOne(MapperData mapperData, Object... parameter) throws Exception {

        return executor.query(mapperData, parameter);
    }

    @Override
    public <E> List<E> selectList(MapperData mapperData, Object... parameter) throws Exception {
        return executor.query(mapperData, parameter);
    }

    @Override
    public int insert(String var1) {
        return 0;
    }


    @Override
    public Connection getConnection() {
        return null;
    }
}
