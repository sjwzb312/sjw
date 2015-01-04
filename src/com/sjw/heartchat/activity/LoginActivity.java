package com.sjw.heartchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.listener.SaveListener;

import com.sjw.heartchat.R;
import com.sjw.heartchat.bean.UserBean;
import com.sjw.heartchat.http.LoginRequest;
import com.sjw.heartchat.http.LoginRequest.LoginListener;

public class LoginActivity extends BaseActivity {
	private EditText et_name;
	private EditText et_pwd;
	private Button btn_login, btn_regist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_layout);
		initView();
		initListener();
	}

	@Override
	public void initView() {
		super.initView();
		setTvTitle("登陆");
		hideRight();
		et_name = (EditText) findViewById(R.id.et_name);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_regist = (Button) findViewById(R.id.btn_go_regist);
	}

	@Override
	public void initListener() {
		super.initListener();
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = et_name.getText().toString();
				String pwd = et_pwd.getText().toString();
				login(name, pwd);
			}
		});
		btn_regist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(LoginActivity.this,
						RegistActivity.class));
				finish();

			}
		});
	}

	/**
	 * 登陆
	 * 
	 * @param name
	 * @param pwd
	 */
	private void login(final String name, final String pwd) {
		if (name == null || pwd == null) {
			return;
		}
		pd.show();
		final UserBean userBean = new UserBean();
		userBean.setUsername(name);
		userBean.setPassword(pwd);

		userBean.login(context, new SaveListener() {

			@Override
			public void onSuccess() {

				loginChat(userBean);

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				pd.dismiss();
				Toast("登陆失败" + arg1);
			}
		});
	}

	private void loginChat(final UserBean userBean) {
		LoginRequest loginRequest = new LoginRequest(userBean.getUsername(),
				userBean.getPassword(), new LoginListener() {

					@Override
					public void onSuccess() {
						pd.dismiss();
						saveUser(userBean);
						Toast("登陆成功");
						startActivity(new Intent(LoginActivity.this,
								MainActivity.class));

					}

					@Override
					public void onError(int code, String msg) {

					}
				});
		loginRequest.request();
	}

}
