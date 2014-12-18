package com.sjw.heartchat.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.PushListener;
import cn.bmob.v3.listener.SaveListener;

import com.sjw.heartchat.R;

public class TestActivity extends BaseActivity {
	BmobPushManager<BmobInstallation> bmobPush;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_layout);
		Button button = (Button) findViewById(R.id.btn_test);
		bmobPush = new BmobPushManager<BmobInstallation>(this);
		BmobInstallation.getCurrentInstallation(this).save();
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// pushMsg();
				pushChannelMessage("nihao  .......", "dd");
			}
		});
		findViewById(R.id.btn_add).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				BmobInstallation installation = BmobInstallation
						.getCurrentInstallation(TestActivity.this);
				installation.subscribe("dd");
				installation.save(TestActivity.this, new SaveListener() {

					@Override
					public void onSuccess() {
						Toast("订阅成功");

					}

					@Override
					public void onFailure(int arg0, String arg1) {
						Toast("订阅失败" + arg1);

					}
				});

			}
		});

		findViewById(R.id.btn_cancle_push).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						BmobInstallation installation2 = BmobInstallation
								.getCurrentInstallation(TestActivity.this);
						installation2.unsubscribe("dd");
						installation2.save();
					}
				});

	}

	private void pushMsg() {

		BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
		List<String> list = new ArrayList<String>();
		list.add("dd");
		query.addWhereEqualTo("channels", list);
		bmobPush.setQuery(query);

		bmobPush.pushMessage("推送消息群聊..............", new PushListener() {

			@Override
			public void onSuccess() {
				Toast("推送成功");

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast("推送失败..." + arg1);

			}
		});
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	private void pushChannelMessage(String message, String channel) {
		BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
		List<String> channels = new ArrayList<String>();
		channels.add(channel);
		query.addWhereContainedIn("channels", channels);
		bmobPush.setQuery(query);
		bmobPush.pushMessage(message);
	}
}
