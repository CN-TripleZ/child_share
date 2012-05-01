package com.ling.child_share.model;

import java.util.Date;

public class Image implements java.io.Serializable {
	
	private String id;
	private Date upload_time;
	private User user;
	private String description;
	private String img_path;

	public Image() {}
	
	public Image(String id) {
		this();
		this.id = id;
	}
	
	//setter and getter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getUpload_time() {
		return upload_time;
	}
	public void setUpload_time(Date uploadTime) {
		upload_time = uploadTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String imgPath) {
		img_path = imgPath;
	}
	  
}