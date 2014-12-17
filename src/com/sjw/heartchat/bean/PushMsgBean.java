package com.sjw.heartchat.bean;

import android.content.Context;
import cn.bmob.v3.BmobInstallation;

public class PushMsgBean extends BmobInstallation{

	private int age;
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public PushMsgBean(Context arg0) {
		super(arg0);
		
	}

}
