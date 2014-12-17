package com.sjw.heartchat.bean;

import cn.bmob.v3.BmobUser;

public class UserBean extends BmobUser {
	private String userId;
	private String nickName;
	private String userSex;

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

}
