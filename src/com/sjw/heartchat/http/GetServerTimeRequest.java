package com.sjw.heartchat.http;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sjw.heartchat.utils.LogUtil;
import com.sjw.heartchat.utils.ToastUtil;

import android.content.Context;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.GetServerTimeListener;

public class GetServerTimeRequest extends BaseRequest {

	private static final String TAG = "GetServerTimeRequest";

	public static void request(final Context context,GetServerTimeListener listener) {
		Bmob.getServerTime(context, listener);
	}

}
