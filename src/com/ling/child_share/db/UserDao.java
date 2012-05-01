package com.ling.child_share.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ling.child_share.model.User;


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
	
	public boolean addUser(User user) {
		String id = user.getId();
		String password = user.getPassword();
		String name = user.getName();
		String email = user.getEmail();
		String sql = "insert into t_user values('" + id + "', '" + password + "', '" + name + "', '" + email + "')";
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
