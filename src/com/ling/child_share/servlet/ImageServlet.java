package com.ling.child_share.servlet;

import java.io.DataInputStream;
import java.io.FileOutputStream;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		ImageDao imageDao = new ImageDao();
		if ("add".equals(cmd)) {
			int MAX_SIZE = 102400 * 102400;

			DataInputStream in = null;
			FileOutputStream fileOut = null;

			String contentType = request.getContentType();
			try {
				if (contentType.indexOf("multipart/form-data") >= 0) {
					in = new DataInputStream(request.getInputStream());
					int formDataLength = request.getContentLength();
					if (formDataLength > MAX_SIZE) {
						return;
					}
					byte dataBytes[] = new byte[formDataLength];
					int byteRead = 0;
					int totalBytesRead = 0;
					while (totalBytesRead < formDataLength) {
						byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
						totalBytesRead += byteRead;
					}
					String file = new String(dataBytes);
					String saveFile = file.substring(file.indexOf("filename=\"") + 10);
					saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
					saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1, saveFile.indexOf("\""));
					int lastIndex = contentType.lastIndexOf("=");
					String boundary = contentType.substring(lastIndex + 1,contentType.length());

					int pos;
					pos = file.indexOf("filename=\"");
					pos = file.indexOf("\n", pos) + 1;
					pos = file.indexOf("\n", pos) + 1;
					pos = file.indexOf("\n", pos) + 1;
					int boundaryLocation = file.indexOf(boundary, pos) - 4;
					// 取得文件数据的开始的位置
					int startPos = ((file.substring(0, pos)).getBytes()).length;
					// 取得文件数据的结束的位置
					int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;

					// 创建文件的写出类
					fileOut = new FileOutputStream("C://aa.jpg");
					// 保存文件的数据
					fileOut.write(dataBytes, startPos, (endPos - startPos));
					fileOut.close();
				} 
			} catch (Exception ex) {
				throw new ServletException(ex.getMessage());
			}

		} else if ("delete".equals(cmd)) {

		} else if ("query".equals(cmd)) {
			String userId = request.getParameter("userId");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			if (userId == null || "".equals(userId)) {
				writer.write("user id error!");
				return;
			}
			ResultSet rs = imageDao.getImages(userId);
			try {
				String html = "getInfo([";
				while (rs.next()) {
					html += "{description:'" + rs.getString("description")
							+ "', path:'" + rs.getString("img_path")
							+ "', upload_time:'" + rs.getDate("upload_time")
							+ "'}";
					if (!rs.isLast()) {
						html += ",";
					}
				}
				html += "]);";
				writer.write(html);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
