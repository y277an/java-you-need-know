package com.xiaohui.minimybatis.binding;

/**
 * @Author: xiaohui
 * @Description: 保存mapper的数据
 */
public class MappedStatement {

    /**
     * SQL语句
     */
    private String sql;

    /**
     * 返回值类型
     */
    private Class type;

    /**
     * 表名
     */
    private String tableName;

    public MappedStatement(String sql, Class type, String tableName) {
        this.sql = sql;
        this.type = type;
        this.tableName = tableName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
