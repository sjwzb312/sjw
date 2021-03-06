package com.sjw.heartchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.SaveListener;

import com.sjw.heartchat.R;
import com.sjw.heartchat.bean.MsgBean;
import com.sjw.heartchat.bean.UserBean;
import com.sjw.heartchat.utils.Contans.BroadCastAction;
import com.sjw.heartchat.utils.LocationUtils;
import com.sjw.heartchat.utils.LogUtil;
import com.sjw.heartchat.utils.LocationUtils.LoactionListener;
import com.sjw.heartchat.utils.LocationUtils.LocationBean;

public class AddMsgActivity extends BaseActivity {
	private EditText et_msg;
	private Button btn_send;
	private Button btn_voice;

	// private LocationUtils locationUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_msg_layout);
		// locationUtils = new LocationUtils(this);
		initView();
		initListener();
	}

	@Override
	public void initView() {
		super.initView();
		et_msg = (EditText) findViewById(R.id.et_msg);
		btn_send = (Button) findViewById(R.id.btn_send_msg);
		btn_voice = (Button) findViewById(R.id.btn_voice);
		hideRight();
		setLeftViewBg(R.drawable.general_button_back);
		setTvTitle("吐槽");
	}

	@Override
	public void initListener() {
		super.initListener();
		btn_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String msg = et_msg.getText().toString();
				if (!TextUtils.isEmpty(msg)) {
					MsgBean msgBean = new MsgBean();
					msgBean.setMsg(msg);
					UserBean userBean = new UserBean();
					userBean.setObjectId(getSpUserID());
					msgBean.setUserName(getSpUserName());
					LogUtil.d("userName", getSpUserName());
					userBean.setUsername(getSpUserName());
					msgBean.setUserBean(userBean);
					sendMsg(msgBean);
				}

			}
		});
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				onBackPressed();
			}
		});
	}

	@Override
	public void initData() {
		super.initData();
	}

	/**
	 * 发送消息
	 * 
	 * @param msgBean
	 */
	private void sendMsg(MsgBean msgBean) {
		pd.show();
		msgBean.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				pd.dismiss();
				Toast("保存成功");
				sendAddMsgBroCast();
				finish();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast("保存失败");
			}
		});
	}

	/**
	 * 发送添加消息的广播
	 */
	private void sendAddMsgBroCast() {
		Intent intent = new Intent();
		intent.setAction(BroadCastAction.ADD_MSG_BROCAST);
		sendBroadcast(intent);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

}
