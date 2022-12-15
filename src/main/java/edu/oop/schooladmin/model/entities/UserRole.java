package edu.oop.schooladmin.model.entities;

public class UserRole {
	private Integer roleId;
	private Integer userId;
	private String roleName;

	public UserRole() {
	}

	public UserRole(Integer roleId, Integer userId, String roleName) {
		this.roleId = roleId;
		this.userId = userId;
		this.roleName = roleName;
	}

	public UserRole(UserRole other) {
		this(other.roleId, other.userId, other.roleName);
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
