package com.xiaohui.minimybatis.proxy;

import com.xiaohui.minimybatis.config.MapperRegistory;
import com.xiaohui.minimybatis.session.SqlSession;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: xiaohui
 * @Description:
 */
@Data
public class MapperProxy implements InvocationHandler, Serializable {

    private final SqlSession sqlSession;

    private final Class clazz;

    public MapperProxy(SqlSession sqlSession, Class clazz) {
        this.sqlSession = sqlSession;
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String s = clazz.getName() + "." + method.getName();
        MapperRegistory.MapperData data = this.sqlSession.getConfiguration().getMapperRegistory().getMethodMaping()
                .get(s);
        if (null != data){
            return this.sqlSession.selectOne(data,args);
        }

        return method.invoke(proxy, args);
    }
}
