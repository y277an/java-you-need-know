package com.xiaohui.minimybatis.executor;

import com.xiaohui.minimybatis.config.Configuration;
import com.xiaohui.minimybatis.config.MapperRegistory;
import com.xiaohui.minimybatis.statement.StatementHandler;
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
    public <T> T query(MapperRegistory.MapperData mapperData, Object... parameter) throws Exception {
        StatementHandler handler = new StatementHandler(configuration);

        return handler.query(mapperData, parameter);
    }
}
