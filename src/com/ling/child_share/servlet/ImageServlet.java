package com.ling.child_share.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ling.child_share.constants.Constants;
import com.ling.child_share.db.ImageDao;
import com.ling.child_share.model.Image;
import com.ling.child_share.model.User;

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
			List photos = uploadPhoto(request);
			for (Object obj : photos) {
				Image image = (Image) obj;
				imageDao.addImage(image);
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

	private List uploadPhoto(HttpServletRequest request) {
		List result = new ArrayList();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
		    List items = upload.parseRequest(request);
		    Iterator itr = items.iterator();
		    while (itr.hasNext()) {
		        FileItem item = (FileItem) itr.next();
		        if (item.isFormField()) {
//		        	String fieldName = item.getFieldName();
//		        	String fieldValue = item.getString();
		        } else {
		            if (item.getName() != null && !item.getName().equals("")) {
		            	String userName = "jeffreyzhang";
		            	String filePath = Constants.PHOTO_PATH + Constants.FILE_SEPARATOR + userName + Constants.FILE_SEPARATOR;
		            	File f = new File(filePath);
						if (!f.exists()) f.mkdirs();
		                File file = new File(filePath + item.getName() + ".jpg");
		                item.write(file);
		                
		                Image image = new Image();
			        	image.setT_user(new User("123"));
			        	image.setUpload_time(new Date());
			        	image.setDescription("");
			        	image.setImg_path(file.getPath());
			        	result.add(image);
		            }
		        }
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return result;
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
