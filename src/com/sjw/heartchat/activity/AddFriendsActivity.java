package com.sjw.heartchat.activity;

import com.sjw.heartchat.R;
import com.sjw.heartchat.http.SearchFriendRequest;
import com.sjw.heartchat.http.SearchFriendRequest.ISearchListener;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AddFriendsActivity extends BaseActivity {
	private EditText et_name;
	private Button btn_find;
	private ListView lv_find;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friends);
		initView();
		initData();
		initListener();
	}

	@Override
	public void initView() {
		super.initView();
		et_name = (EditText) findViewById(R.id.et_find_name);
		btn_find = (Button) findViewById(R.id.btn_find);
		lv_find = (ListView) findViewById(R.id.lv_find_friend);
		hideRight();
	}

	@Override
	public void initData() {
		super.initData();
	}

	@Override
	public void initListener() {
		super.initListener();
		btn_find.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = et_name.getText().toString();
				if (TextUtils.isEmpty(name)) {
					Toast("名字不能为空");
				} else {
					searchFriend(name);
				}

			}
		});
	}

	private void searchFriend(String name) {
		SearchFriendRequest.searchFriend(name, new ISearchListener() {

			@Override
			public void doSuccess() {
				Toast("请求发送成功！");

			}

			@Override
			public void doFail() {
				Toast("请求发送失败！");
			}
		});
	}

}
