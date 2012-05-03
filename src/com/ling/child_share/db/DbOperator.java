package com.ling.child_share.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author chenhaiyu e-mail:haiyupeter@163.com
 * @Version 2012-4-30 下午11:44:45
 */
public class DbOperator {


    Connection conn = null;

    String url = "jdbc:mysql://localhost:3306/timeshare?useUnicode=true&characterEncoding=UTF8";
    String driver = "com.mysql.jdbc.Driver";
    String user = "root";
    String password = "root";

    public DbOperator() {
        Connect();
    }

    public void Connect() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statement getStatement(String sql) {
        try {
            return conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PreparedStatement getPreparedStatement(String sql) {
        try {
            return conn.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CallableStatement getCallableStatement(String sql) {
        try {
            return conn.prepareCall(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean close() {
    	try {
    		conn.close();
    		return true;
    	} catch (SQLException e) {
    		System.out.println(e);
    	}
    	return false;
    }
    
}
