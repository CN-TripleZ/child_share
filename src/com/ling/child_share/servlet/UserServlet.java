package com.ling.child_share.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ling.child_share.db.UserDao;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		UserDao userDao = new UserDao();
		if ("add".equals(cmd)) {
			userDao.addUser();
		} else if ("delete".equals(cmd)) {
			
		} else if ("query".equals(cmd)) {
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			PrintWriter writer = response.getWriter();
			if (id == null || "".equals(id) || password == null || "".equals(password)) {
				writer.write("user id and password error!");
				return;
			}
			ResultSet rs = userDao.getUser(id, password);
			try {
				if (rs.next()) {
					String name = rs.getString("name");
					writer.write("getUserInfo({id:'" + id + "',name:'" + name + "'});");
					rs.close();
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
