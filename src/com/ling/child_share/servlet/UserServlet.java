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
import com.ling.child_share.model.User;

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
		PrintWriter writer = response.getWriter();
		if ("add".equals(cmd)) {
			User user = new User();
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			user.setId(id);
			user.setName(name);
			user.setPassword(password);
			user.setEmail(email);
			userDao.addUser(user);
			writer.write("addUserInfo({id:'" + id + "',name:'" + name + "'});");
		} else if ("delete".equals(cmd)) {
			
		} else if ("query".equals(cmd)) {
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			response.setCharacterEncoding("UTF-8");
			if (id == null || "".equals(id) || password == null || "".equals(password)) {
				writer.write("user id and password error!");
				return;
			}
			ResultSet rs = userDao.getUser(id, password);
			try {
				if (rs.next()) {
					String name = rs.getString("name");
					writer.write("getUserInfo({ret:0, msg:'login success', data:{id:'" + id + "',name:'" + name + "'}});");
					rs.close();
					return;
				} else {
					writer.write("getUserInfo({ret:-9999, msg:'login fail'});");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				writer.write("getUserInfo({ret:-1, msg:'server error'});");
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
