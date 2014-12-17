package com.sjw.heartchat.utils;

public class Contans {

	public static int colors[] = { android.R.color.holo_blue_light,
			android.R.color.holo_red_light, android.R.color.holo_orange_light,
			android.R.color.holo_green_light };

	/**
	 * 广播动作
	 * 
	 * @author gg
	 * 
	 */
	public class BroadCastAction {

		public static final String LOGIN_BROCAST = "com.heartchat.action.login"; // 登陆
		public static final String LOGIN_OUT_BROCAST = "com.heartchat.action.loginout";// 退出
		public static final String ADD_MSG_BROCAST = "com.heartchat.action.addmsg";
	}

}
