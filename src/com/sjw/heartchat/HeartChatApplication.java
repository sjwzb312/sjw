package com.sjw.heartchat;

import android.app.Application;
import cn.bmob.v3.Bmob;

import com.baidu.location.LocationClient;
import com.easemob.chat.EMChat;

public class HeartChatApplication extends Application {
	public LocationClient mLocationClient;
	public static String APPID = "3dc1bca6f5a8b1e4bbce9edb6818f932";
	@Override
	public void onCreate() {
		super.onCreate();
		Bmob.initialize(this, APPID);
		EMChat.getInstance().init(getApplicationContext());
		 // 使用推送服务时的初始化操作
	    
		initLocation();
	}

	/**
	 * 初始化百度地图定位
	 */
	private void initLocation() {
		mLocationClient = new LocationClient(this);

	}

}
