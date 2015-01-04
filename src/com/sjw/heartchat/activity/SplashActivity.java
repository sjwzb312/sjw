package com.sjw.heartchat.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.bmob.v3.listener.GetServerTimeListener;

import com.sjw.heartchat.HeartChatApplication;
import com.sjw.heartchat.R;
import com.sjw.heartchat.http.GetServerTimeRequest;
import com.sjw.heartchat.utils.LogUtil;
import com.sjw.heartchat.utils.ToastUtil;

public class SplashActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_layout);
		getServerTime();
		// new Handler().postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// if (isLogin()) {
		// startActivity(new Intent(SplashActivity.this,
		// LoginActivity.class));
		// finish();
		// } else {
		// startActivity(new Intent(SplashActivity.this,
		// MainActivity.class));
		// finish();
		// }
		//
		// }
		// }, 1500);
	}

	private void getServerTime() {
		GetServerTimeRequest.request(context, new GetServerTimeListener() {

			@Override
			public void onSuccess(long arg0) {
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				String times = formatter.format(new Date(arg0 * 1000L));
				Date date=new Date(arg0*1000L);
				HeartChatApplication.day =date.getDay();
				if(HeartChatApplication.day==0){
					HeartChatApplication.day=7;
				}
				//HeartChatApplication.day=3;
				//ToastUtil.toast(context, times);
				LogUtil.d(TAG, times+"  day  "+HeartChatApplication.day);
				startActivity();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				ToastUtil.toast(context, "获取服务器时间失败" + arg1);
			}
		});
	}

	private void startActivity() {
		if (isLogin()) {
			startActivity(new Intent(SplashActivity.this, LoginActivity.class));
			finish();
		} else {
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
			finish();
		}
	}

	// private void startActivity() {
	// if (Utils.isLogin(this)) {
	// stat
	// }
	// }

}
