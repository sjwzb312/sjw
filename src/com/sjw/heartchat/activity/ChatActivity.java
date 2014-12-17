package com.sjw.heartchat.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.inteface.EventListener;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.listener.PushListener;

import com.sjw.heartchat.R;
import com.sjw.heartchat.adapter.LvChatAdapter;
import com.sjw.heartchat.bean.MsgBean;
import com.sjw.heartchat.inte.DefaultEventListener;
import com.sjw.heartchat.receiver.HeartChatMsgReceiver;
import com.sjw.heartchat.utils.LogUtil;
import com.sjw.heartchat.utils.ToastUtil;

public class ChatActivity extends BaseActivity implements OnClickListener {
	private EditText et_chat;
	private Button btn_chat;
	private MsgBean msgBean;
	private ListView lv_chat;
	private LvChatAdapter lvChatAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_layout);
		initView();
		initData();
		initListener();
	}

	@Override
	public void initView() {
		super.initView();
		et_chat = (EditText) getViewById(R.id.et_chat);
		btn_chat = (Button) getViewById(R.id.btn_chat_send);
		lv_chat = (ListView) findViewById(R.id.lv_chat);

	}

	@Override
	public void initData() {
		super.initData();
		// targChatUser=(BmobChatUser)
		// getIntent().getSerializableExtra("targUser");
		msgBean = (MsgBean) getIntent().getSerializableExtra("msgBean");
		lvChatAdapter = new LvChatAdapter(this);
		lv_chat.setAdapter(lvChatAdapter);
	}

	@Override
	public void initListener() {
		super.initListener();
		btn_chat.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_chat_send:
			sendChat();
			break;

		default:
			break;
		}

	}

	/**
	 * 发送消息
	 */
	private void sendChat() {
		// targChatUser = new BmobChatUser();
		// targChatUser.setUsername("1234564");
		// targChatUser.setObjectId(msgBean);
		String msg = et_chat.getText().toString();
		LogUtil.d("targetId", msgBean.getUserBean().getObjectId() + " "
				+ msgBean.getUserBean().getUsername());
		// a34058d691
		msgBean.getUserBean().setUsername("1234567");
		BmobMsg message = BmobMsg.createTextSendMsg(context, msgBean
				.getUserBean().getObjectId(), msg);
		chatManager.sendTextMessage(msgBean.getUserBean(), message,
				new PushListener() {

					@Override
					public void onSuccess() {
						Toast("发送成功....");

					}

					@Override
					public void onFailure(int arg0, String arg1) {
						Toast("发送失败...." + arg1);

					}
				});

	}

}
