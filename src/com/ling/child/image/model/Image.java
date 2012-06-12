package com.ling.child.image.model;

import java.util.Date;

import com.ling.child.user.model.User;

public class Image implements java.io.Serializable {
	private static final long serialVersionUID = -5880627123137506330L;

	private String id;
	private String path;
	private Date upload_time;
	private String description;
	private User user;

	public Image() {
	}

	public Image(String id, String path) {
		this.id = id;
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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
}