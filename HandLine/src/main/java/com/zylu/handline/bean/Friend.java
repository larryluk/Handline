package com.zylu.handline.bean;

public class Friend {
	
	private String friId;

	private String[] permissions;

	
	public Friend() { }

	public Friend(String friId, String[] permissions) {
		this.friId = friId;
		this.permissions = permissions;
	}

	public String getFriId() {
		return friId;
	}

	public void setFriId(String friId) {
		this.friId = friId;
	}

	public String[] getPermissions() {
		return permissions;
	}

	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}

}
