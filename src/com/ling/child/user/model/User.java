package com.ling.child.user.model;

/**
 * @author jeffreyzhang
 *
 */
public class User implements java.io.Serializable {
	private static final long serialVersionUID = 813919795237689427L;
	
	private String id;
	private String password;
	private String name;
	private String email;
	
	public User() {}
	
	public User(String id) {
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
