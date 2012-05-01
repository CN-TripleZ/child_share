package com.ling.child_share.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author chenhaiyu e-mail:haiyupeter@163.com
 * @Version 2012-4-30 下午11:47:30
 */
public class UserDao {
	
	public ResultSet getUser(String id, String password) {
		String sql = "select * from t_user where id='" + id + "' and password='" + password +"'";
		DbOperator dbOperator = new DbOperator();
		PreparedStatement ps = dbOperator.getPreparedStatement(sql);
		try {
			ps.execute();
			return ps.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean addUser() {
		String sql = "insert into t_user values('haiyuchen', '123456', '海宇', '21haiyu21@163.com')";
		DbOperator dbOperator = new DbOperator();
		PreparedStatement ps = dbOperator.getPreparedStatement(sql);
		try {
			ps.execute();
		} catch (SQLException e) {
			System.out.println("执行失败：" + e);
			return false;
		}
		dbOperator.close();
		return true;
	}
}
