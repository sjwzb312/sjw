package com.sjw.heartchat.http;

import com.sjw.heartchat.utils.LogUtil;
import com.sjw.heartchat.utils.ToastUtil;

import android.content.Context;

public class BaseRequest {

	public void Toast(Context context, String msg) {
		ToastUtil.toast(context, msg);
	}

	public void log(String msg) {
		LogUtil.d(this.getClass().getName(), msg);
	}
}
