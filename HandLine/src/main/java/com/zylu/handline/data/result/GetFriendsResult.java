package com.zylu.handline.data.result;

import java.util.List;

import com.zylu.handline.bean.User;

public class GetFriendsResult extends CommonResult {

	private List<User> friends;

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public GetFriendsResult(List<User> friends) {
		super();
		this.friends = friends;
	}

	public GetFriendsResult() {
		super();
	}

	public GetFriendsResult(String errMsg) {
		super(errMsg);
	}
	
	
	
	
}
