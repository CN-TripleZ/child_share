package com.ling.child_share.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ling.child_share.db.ImageDao;

/**
 * Servlet implementation class ImageServlet
 */
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		ImageDao imageDao = new ImageDao();
		if ("add".equals(cmd)) {
			imageDao.addImage("haiyuchen");
		} else if ("delete".equals(cmd)) {
			
		} else if ("query".equals(cmd)) {
			String userId = request.getParameter("userId");
			PrintWriter writer = response.getWriter();
			if (userId == null || "".equals(userId)) {
				writer.write("user id error!");
				return;
			}
			ResultSet rs = imageDao.getImages(userId);
			try {
				String html = "getInfo(";
				while (rs.next()) {
					html += "{descript:'" + rs.getString("description") + "', path:'" + rs.getString("img_path") + "'}";
					if (!rs.last()) {
						html += ",";
					}
				}
				html += ");";
				writer.write(html);
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
