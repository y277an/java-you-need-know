package com.xiaohui.minimybatis.executor;

import com.xiaohui.minimybatis.config.MapperRegistory;

/**
 * @Author: xiaohui
 * @Description:
 */
public interface Executor {

    <T> T query(MapperRegistory.MapperData mapperData, Object... parameter) throws Exception;

}
