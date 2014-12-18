package com.sjw.heartchat.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import cn.bmob.v3.listener.PushListener;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.chat.EMMessage.ChatType;
import com.sjw.heartchat.R;
import com.sjw.heartchat.adapter.LvChatAdapter;
import com.sjw.heartchat.bean.MsgBean;
import com.sjw.heartchat.utils.LogUtil;
import com.sjw.heartchat.utils.SharePreUtil;

public class ChatActivity extends BaseActivity implements OnClickListener {
	private EditText et_chat;
	private Button btn_chat;
	private MsgBean msgBean;
	private ListView lv_chat;
	private LvChatAdapter lvChatAdapter;
	private EMConversation conversation;

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
		conversation = EMChatManager.getInstance().getConversation(
				getSpUserName());
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
			String content = et_chat.getText().toString();
			sendText(content);
			break;

		default:
			break;
		}

	}

	private void sendText(String content) {

		if (content.length() > 0) {
			EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
			// 如果是群聊，设置chattype,默认是单聊

			TextMessageBody txtBody = new TextMessageBody(content);
			// 设置消息body
			message.addBody(txtBody);
			// 设置要发给谁,用户username或者群聊groupid
			message.setReceipt("123456123");
			// 把messgage加到conversation中
			conversation.addMessage(message);
			// 通知adapter有消息变动，adapter会根据加入的这条message显示消息和调用sdk的发送方法
			lvChatAdapter.refresh();
			lv_chat.setSelection(lv_chat.getCount() - 1);
			et_chat.setText("");

		}
	}
}
