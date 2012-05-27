package com.ling.framework.database;

/**
 * 数据库路由器
 */
public interface IDBRouter {
	
	/**
	 * 得到一个用户某模块的表名
	 * @param userid 用户id
	 * @param moudle 模块名
	 * @return
	 */
	public String getTableName(String moudle);
	
	/**
	 * 生成一个用户某模块的数据表
	 * @param userid 用户id
	 * @param moudle 模块名
	 */
	public void createTable( String moudle);
}
