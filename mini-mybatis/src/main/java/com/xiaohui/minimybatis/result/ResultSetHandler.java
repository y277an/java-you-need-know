package com.xiaohui.minimybatis.result;

import com.xiaohui.minimybatis.config.Configuration;
import com.xiaohui.minimybatis.config.MapperRegistory;
import com.xiaohui.minimybatis.utils.StringUtils;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: xiaohui
 * @Description:
 */
public class ResultSetHandler {

    private final Configuration configuration;

    public ResultSetHandler(Configuration configuration) {
        this.configuration = configuration;
    }


    public <E> E handle(PreparedStatement pstmt, MapperRegistory.MapperData mapperData) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object obj = new DefaultObjectFactory().create(mapperData.getType());

        /**
         * 执行SQL，rs为返回结果
         */
        ResultSet rs = pstmt.getResultSet();
        while (rs.next()) {
            for (Field field : obj.getClass().getDeclaredFields()) {
                setValue(obj, field, rs);
            }
        }

        return (E) obj;
    }

    /**
     * 利用反射获取到User的set方法，然后将User的每个字段进行赋值
     * @param obj 本例中就是User
     * @param field User的字段（id和name）
     * @param rs SQL为返回结果
     */
    private void setValue(Object obj, Field field, ResultSet rs) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        Method method = obj.getClass().getMethod("set" + StringUtils.firstCharSwapHigh(field.getName()), field.getType());
        method.invoke(obj, getResult(field, rs));
    }


    /**
     * 根据User里的字段类型，进行不同的转换
     * @param field User的字段（id和name）
     * @param rs
     */
    private Object getResult(Field field, ResultSet rs) throws SQLException {
        Class<?> type = field.getType();
        if (Integer.class == type) {
            return rs.getInt(field.getName());
        }
        if (String.class == type) {
            return rs.getString(field.getName());
        }
        return rs.getString(field.getName());
    }


}
