package com.sjw.heartchat.bean;

import cn.bmob.v3.BmobObject;

public class CommentBean extends BmobObject {
	private String comment;
	private String msgBeanId;
	
	
	public String getMsgBeanId() {
		return msgBeanId;
	}

	public void setMsgBeanId(String msgBeanId) {
		this.msgBeanId =msgBeanId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
