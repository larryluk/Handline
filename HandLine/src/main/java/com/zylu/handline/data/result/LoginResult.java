package com.zylu.handline.data.result;

import com.zylu.handline.bean.User;

public class LoginResult extends CommonResult{
	private User user;
	private String mKey;
	private String token;	
	
	public LoginResult() {
		super();
	}
	
	public LoginResult(String errMsg) {
		super(errMsg);
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getmKey() {
		return mKey;
	}
	public void setmKey(String mKey) {
		this.mKey = mKey;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	

}
