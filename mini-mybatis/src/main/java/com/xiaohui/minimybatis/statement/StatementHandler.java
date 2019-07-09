package com.xiaohui.minimybatis.statement;

import com.xiaohui.minimybatis.config.Configuration;
import com.xiaohui.minimybatis.config.MapperRegistory;
import com.xiaohui.minimybatis.result.ResultSetHandler;
import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author: xiaohui
 * @Description:
 */
@Data
public class StatementHandler {

    private final Configuration configuration;

    private final ResultSetHandler resultSetHandler;

    public StatementHandler(Configuration configuration) {
        this.configuration = configuration;
        resultSetHandler = new ResultSetHandler(configuration);
    }


    public <E> E query(MapperRegistory.MapperData mapperData, Object... parameter) throws Exception {
        try {
            //JDBC
            Connection conn = getConnection();
            //TODO ParamenterHandler
            PreparedStatement pstmt = conn.prepareStatement(String.format(mapperData.getSql(), mapperData.getTableName(), parameter[0]));
            pstmt.execute();
            //ResultSetHandler
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
