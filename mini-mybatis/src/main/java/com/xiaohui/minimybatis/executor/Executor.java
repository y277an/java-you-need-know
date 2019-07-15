package com.xiaohui.minimybatis.executor;


import com.xiaohui.minimybatis.binding.MappedStatement;

/**
 * @Author: xiaohui
 * @Description:执行器
 */
public interface Executor {

    <T> T query(MappedStatement mapperStatement, Object... parameter) throws Exception;

}
