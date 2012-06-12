package com.ling.framework.db;

import com.ling.framework.database.NotDBField;

public class User {
	private Integer user_id;

	private String username;

	private String password;

	@NotDBField
	public String getDate() {

		return "aabc";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
