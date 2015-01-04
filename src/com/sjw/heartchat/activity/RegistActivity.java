package com.sjw.heartchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

import com.easemob.EMError;
import com.easemob.exceptions.EaseMobException;
import com.sjw.heartchat.R;
import com.sjw.heartchat.bean.UserBean;
import com.sjw.heartchat.http.RegistRequest;
import com.sjw.heartchat.http.RegistRequest.RegistListener;

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
		hideRight();
		setTvTitle("注册");
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
				logD(TAG,
						"注册成功:" + userBean.getUsername() + "-"
								+ userBean.getObjectId() + "-"
								+ userBean.getCreatedAt() + "-"
								+ userBean.getSessionToken());
				// saveUser(userBean);
				// registChatUser(userBean.getUsername(),
				// userBean.getPassword());
				registChat(userBean);

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				pd.dismiss();
				Toast("注册失败" + arg1);
			}
		});
	}

	private void registChat(final UserBean userBean) {
		RegistRequest registRequest = new RegistRequest(userBean.getUsername(),
				userBean.getPassword(), new RegistListener() {

					@Override
					public void onSuccess() {
						saveUser(userBean);
						Toast("注册成功");
						startActivity(new Intent(context, LoginActivity.class));
						finish();

					}

					@Override
					public void onError(EaseMobException exception) {
						pd.dismiss();
						int errorCode = exception.getErrorCode();
						if (errorCode == EMError.NONETWORK_ERROR) {
							Toast.makeText(context, "网络异常，请检查网络！",
									Toast.LENGTH_SHORT).show();
						} else if (errorCode == EMError.USER_ALREADY_EXISTS) {
							Toast.makeText(context, "用户已存在！",
									Toast.LENGTH_SHORT).show();
						} else if (errorCode == EMError.UNAUTHORIZED) {
							Toast.makeText(context, "注册失败，无权限！",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(context,
									"注册失败: " + exception.getMessage(),
									Toast.LENGTH_SHORT).show();
						}

					}
				});
		registRequest.request();   
		
		  
	}

}
