package com.sjw.heartchat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePreUtil {
	public static final String SP_NAME = "SP_WEI_LIAO";

	public static class SP_USER {
		public static final String USER_NAME = "USER_NAME";
		public static final String USER_PWD = "USER_PWD";
		public static final String USER_ID = "USER_ID";
		public static final String USER_CHAT_ID = "USER_CHAT_ID";
	}
	

	/**
	 * 定位信息
	 * 
	 * @author gg
	 * 
	 */
	public static class SP_LOACTION_INFO {
		public static final String LOCATION_LAT = "LOCATION_LAT";
		public static final String LOCATION_LON = "LOCATION_LON";
		public static final String LOCATION_ADDR = "LOCATION_ADDR";
		public static final String LOCATION_CITY="LOCATION_CITY";
	}

	public static SharedPreferences getShPreferences(Context context) {

		return context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);

	}

	/**
	 * 存放string
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putStrToSp(Context context, String key, String value) {
		getShPreferences(context).edit().putString(key, value).commit();
	}

	/**
	 * 获得sp 中的string 值
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getStrFroSp(Context context, String key,
			String defValue) {
		return getShPreferences(context).getString(key, defValue);
	}

}
