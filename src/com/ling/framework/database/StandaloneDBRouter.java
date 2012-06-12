package com.ling.framework.database;


/**
 * 单机版数据库路由
 */
public class StandaloneDBRouter implements IDBRouter {
	// 表前缀
	private String prefix;

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void createTable(String moudle) {

	}

	public String getTableName(String moudle) {
		return prefix + moudle;
	}
}
