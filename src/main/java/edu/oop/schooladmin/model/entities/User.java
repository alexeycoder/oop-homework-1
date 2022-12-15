package edu.oop.schooladmin.model.entities;

public class User {
	private Integer userId;
	private String loginName;
	private String password;

	public User() {
	}

	public User(Integer userId, String loginName, String password) {
		this.userId = userId;
		this.loginName = loginName;
		this.password = password;
	}

	public User(User other) {
		this(other.userId, other.loginName, other.password);
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
