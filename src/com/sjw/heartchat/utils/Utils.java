package com.sjw.heartchat.utils;

import android.content.Context;
import android.text.TextUtils;

public class Utils {
	/**
	 * �ж��û��Ƿ��½
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isLogin(Context context) {
		String userId = SharePreUtil.getStrFroSp(context,
				SharePreUtil.SP_USER.USER_ID, "");
		return !TextUtils.isEmpty(userId);
	}

}
