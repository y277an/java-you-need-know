package com.xiaohui.minimybatis.session;

import java.sql.Connection;

/**
 * @Author: xiaohui
 * @Description: 事务隔离级别，是一个枚举型
 */
public enum TransactionIsolationLevel {
	//包括JDBC支持的5个级别
  NONE(Connection.TRANSACTION_NONE),
  READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
  READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
  REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
  SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

  private final int level;

  private TransactionIsolationLevel(int level) {
    this.level = level;
  }

  public int getLevel() {
    return level;
  }
}
