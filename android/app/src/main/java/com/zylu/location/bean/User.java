package com.zylu.location.bean;

import java.util.List;

public class User{
	
	private String id;
	
	private String userName;

	private String password;

	private String realName;
	
	private String email;
	
	private String role;
	
	private List<Friend> friends;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Friend> getFriends() {
		return friends;
	}

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User() {
	}

	public User(String userName, String password, String realName, String email) {
		this.userName = userName;
		this.password = password;
		this.realName = realName;
		this.email = email;
		this.role = "1";
	}
}

