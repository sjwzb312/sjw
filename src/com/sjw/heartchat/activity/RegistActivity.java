package com.sjw.heartchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.listener.SaveListener;

import com.sjw.heartchat.R;
import com.sjw.heartchat.bean.UserBean;

public class RegistActivity extends BaseActivity {
	private EditText et_name;
	private EditText et_pwd;
	private Button btn_regist;
	private String TAG = "RegistActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist_layout);
		Log.d("RegistActivity", "oncre  ");
		initView();
		initListener();
		initData();
	}

	@Override
	public void initView() {

		Log.d("RegistActivity", "initView regist ");
		btn_regist = (Button) findViewById(R.id.btn_regist);
		et_name = (EditText) findViewById(R.id.et_name);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		super.initView();
	}

	@Override
	public void initData() {
		super.initData();
	}

	@Override
	public void initListener() {

		btn_regist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = et_name.getText().toString();
				String pwd = et_pwd.getText().toString();
				if (name == null || pwd == null) {
					return;
				}
				regist(name, pwd);

			}
		});
		super.initListener();
	}

	/**
	 * 注册方法
	 * 
	 * @param name
	 * @param pwd
	 */
	private void regist(String name, String pwd) {
		pd.show();
		final UserBean userBean = new UserBean();
		userBean.setUsername(name);
		userBean.setPassword(pwd);
		userBean.signUp(this, new SaveListener() {

			@Override
			public void onSuccess() {
				Toast("注册成成功");
				logD(TAG,
						"注册成功:" + userBean.getUsername() + "-"
								+ userBean.getObjectId() + "-"
								+ userBean.getCreatedAt() + "-"
								+ userBean.getSessionToken());
				// saveUser(userBean);
				// registChatUser(userBean.getUsername(),
				// userBean.getPassword());
				startActivity(new Intent(context, MainActivity.class));
				finish();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				pd.dismiss();
				Toast("注册失败" + arg1);
			}
		});
	}

	// /**
	// * 注册聊天的的用户
	// *
	// * @param chatName
	// * @param chatPwd
	// */
	// private void registChatUser(String chatName, String chatPwd) {
	// final BmobChatUser chatUser = new BmobChatUser();
	// chatUser.setUsername(chatName);
	// chatUser.setPassword(chatPwd);
	// chatUser.signUp(context, new SaveListener() {
	//
	// @Override
	// public void onSuccess() {
	// UserBean userBean = new UserBean();
	// userBean.setUsername(chatUser.getUsername());
	// userBean.setPassword(chatUser.getPassword());
	// userBean.setUserChatId(chatUser.getObjectId());
	// saveUser(userBean);
	// Toast("注册成成功....");
	// pd.dismiss();
	//
	// }
	//
	// @Override
	// public void onFailure(int arg0, String arg1) {
	// Toast("注册失败...");
	// }
	// });
	//
	// }

}
