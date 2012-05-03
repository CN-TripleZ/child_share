package com.ling.child_share.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.ling.child_share.model.Image;

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
	
	public ResultSet getHotImages() {
		String sql = "select *from t_image order by upload_time desc";
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
	
	public boolean addImage(Image image) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into t_image (t_user_id, description, upload_time, img_path) ");
		sql.append(" values (");
		sql.append("'").append(image.getUser().getId()).append("',");
		sql.append("'").append(image.getDescription()).append("',");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		sql.append("'").append(df.format(image.getUpload_time())).append("',");
		sql.append("'").append(image.getImg_path()).append("'");
		sql.append(")");
		DbOperator dbOperator = new DbOperator();
		PreparedStatement ps = dbOperator.getPreparedStatement(sql.toString());
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

	}
}
