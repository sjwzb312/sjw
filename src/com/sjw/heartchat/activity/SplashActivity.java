package com.sjw.heartchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sjw.heartchat.R;

public class SplashActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_layout);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (isLogin()) {
					startActivity(new Intent(SplashActivity.this,
							LoginActivity.class));
					finish();
				} else {
					startActivity(new Intent(SplashActivity.this,
							MainActivity.class));
					finish();
				}

			}
		}, 1500);
	}

	// private void startActivity() {
	// if (Utils.isLogin(this)) {
	// stat
	// }
	// }
}
