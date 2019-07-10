package com.xiaohui.minimybatis.binding;

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
        MapperData data = this.sqlSession.getConfiguration().getMapperRegistry().getMethodMaping()
                .get(s);
        if (null != data){
            return this.sqlSession.selectOne(data,args);
        }

        return method.invoke(proxy, args);
    }
}
