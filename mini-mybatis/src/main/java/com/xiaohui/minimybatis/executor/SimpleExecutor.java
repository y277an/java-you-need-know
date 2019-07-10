package com.xiaohui.minimybatis.executor;

import com.xiaohui.minimybatis.config.Configuration;
import com.xiaohui.minimybatis.executor.statement.*;
import com.xiaohui.minimybatis.binding.*;
import lombok.Data;

/**
 * @Author: xiaohui
 * @Description:
 */
@Data
public class SimpleExecutor implements Executor {

    private Configuration configuration;

    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T query(MapperData mapperData, Object... parameter) throws Exception {
        StatementHandler handler = new StatementHandler(configuration);

        return handler.query(mapperData, parameter);
    }
}
