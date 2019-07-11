package com.xiaohui.minimybatis.executor.statement;

import com.xiaohui.minimybatis.binding.MapperData;
import com.xiaohui.minimybatis.config.Configuration;
import com.xiaohui.minimybatis.executor.resultset.ResultSetHandler;
import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author: xiaohui
 * @Description:语句处理器
 */
@Data
public class StatementHandler {

    private final Configuration configuration;

    private final ResultSetHandler resultSetHandler;

    public StatementHandler(Configuration configuration) {
        this.configuration = configuration;
        resultSetHandler = new ResultSetHandler(configuration);
    }

    /**
     * 处理查询
     */
    public <E> E query(MapperData mapperData, Object... parameter) throws Exception {
        try {
            // JDBC
            Connection conn = getConnection();
            //TODO ParamenterHandler
            PreparedStatement pstmt = conn.prepareStatement(String.format(mapperData.getSql(), mapperData.getTableName(), parameter[0]));
            pstmt.execute();
            // ResultSetHandler
            return (E) resultSetHandler.handle(pstmt, mapperData);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Connection getConnection() throws SQLException {
        String driver = Configuration.DRIVER;
        String url = Configuration.URL;
        String username = Configuration.USER_NAME;
        String password = Configuration.PASS_WORD;
        Connection conn = null;
        try {
            //classLoader,加载对应驱动
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


}
