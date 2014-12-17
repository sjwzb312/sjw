package com.sjw.heartchat.bean;

import cn.bmob.v3.BmobObject;

public class EventBean extends BmobObject {
	private String eventId;
	private String  userId;
	private String eventContent;
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEventContent() {
		return eventContent;
	}
	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}

}
