package com.sjw.heartchat;

import android.app.Application;
import android.content.Context;
import cn.bmob.v3.Bmob;

import com.baidu.location.LocationClient;
import com.easemob.EMConnectionListener;
import com.easemob.chat.EMChat;

public class HeartChatApplication extends Application {
	public static Context applicationContext;
	private static HeartChatApplication instance;
	// login user name
	public final String PREF_USERNAME = "username";
	
	/**
	 * 当前用户nickname,为了苹果推送不是userid而是昵称
	 */
	public static String currentUserNick = "";
	public LocationClient mLocationClient;
	public static String APPID = "3dc1bca6f5a8b1e4bbce9edb6818f932";
	private boolean sdkInited;
	private boolean isDebug=false;
	private String TAG = "HeartChatApplication";
	/**
	 * MyConnectionListener
	 */
	protected EMConnectionListener connectionListener = null;
	public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();

	@Override
	public void onCreate() {
		super.onCreate();
		Bmob.initialize(this, APPID);
		EMChat.getInstance().init(getApplicationContext());
		 applicationContext = this;
	        instance = this;
	        hxSDKHelper.onInit(applicationContext);
		//init();
		// 使用推送服务时的初始化操作

		// initLocation();
	}

	/**
	 * 初始化百度地图定位
	 */
	private void initLocation() {
		mLocationClient = new LocationClient(this);

	}

	

	public static HeartChatApplication getInstance() {
		return instance;
	}
 
	
	
	


	
	
}
