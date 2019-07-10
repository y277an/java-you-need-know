package com.xiaohui.minimybatis.executor;


import com.xiaohui.minimybatis.binding.MapperData;

/**
 * @Author: xiaohui
 * @Description:
 */
public interface Executor {

    <T> T query(MapperData mapperData, Object... parameter) throws Exception;

}
