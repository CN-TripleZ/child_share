package com.ling.child_share.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author chenhaiyu e-mail:haiyupeter@163.com
 * @Version 2012-4-30 下午11:47:21
 */
public class ImageDao {
	public ResultSet getImages(String userId) {
		String sql = "select * from t_image where t_user_id='" + userId + "'";
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
	
	public boolean addImage(String userId) {
		String sql = "insert into t_image (t_user_id, description, upload_time, img_path) values('" + userId + "', '你的成长将是妈妈最大的快乐', '2012-02-02', 'http://www.iteye.com/upload/logo/user/664205/9e2aff5c-bb85-3662-a5ef-0fd9338d097a.png?1334153356')";
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
	
	public static void main(String[] args) {
		ImageDao imageDao = new ImageDao();
		imageDao.addImage("haiyuchen");
	}
}
