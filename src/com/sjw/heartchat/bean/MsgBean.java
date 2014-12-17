package com.sjw.heartchat.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * ��Ϣ��
 * 
 * @author gg
 * 
 */
public class MsgBean extends BmobObject implements Serializable {

	private String msg;
	private UserBean userBean;
	private long msgTime;
	private Integer praiseCount;
	
	public Integer getPraiseCount() {
		if(praiseCount==null){
			return 0;
		}
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	private BmobGeoPoint msgGeoPoint;

	public BmobGeoPoint getMsgGeoPoint() {
		return msgGeoPoint;
	}

	public void setMsgGeoPoint(BmobGeoPoint msgGeoPoint) {
		this.msgGeoPoint = msgGeoPoint;
	}

	public long getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(long msgTime) {
		this.msgTime = msgTime;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
