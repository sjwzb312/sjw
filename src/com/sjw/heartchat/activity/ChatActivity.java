package com.sjw.heartchat.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.TextMessageBody;
import com.sjw.heartchat.R;
import com.sjw.heartchat.adapter.LvChatAdapter;
import com.sjw.heartchat.bean.MsgBean;
import com.sjw.heartchat.utils.LogUtil;

public class ChatActivity extends BaseActivity implements OnClickListener {
	private EditText et_chat;
	private Button btn_chat;
	private MsgBean msgBean;
	private ListView lv_chat;
	private LvChatAdapter lvChatAdapter;
	private EMConversation conversation;
	private String toChatUsername;
	private TextView tv_title;

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
		tv_title = (TextView) findViewById(R.id.tv_title);
		et_chat = (EditText) getViewById(R.id.et_chat);
		btn_chat = (Button) getViewById(R.id.btn_chat_send);
		lv_chat = (ListView) findViewById(R.id.lv_chat);
		initRec();

	}

	@Override
	public void initData() {
		super.initData();
		msgBean = (MsgBean) getIntent().getSerializableExtra("msgBean");
		toChatUsername = msgBean.getUserName();
		//toChatUsername="123";
		conversation = EMChatManager.getInstance().getConversation(
				toChatUsername);
		  // 把此会话的未读数置为0
        conversation.resetUnreadMsgCount();
		tv_title.setText(getSpUserName() + " 对  " + toChatUsername);
		lvChatAdapter = new LvChatAdapter(this,toChatUsername);
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

	private NewMessageBroadcastReceiver receiver;
	private void initRec() {
		// 注册接收消息广播
		 receiver= new NewMessageBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(EMChatManager
				.getInstance().getNewMessageBroadcastAction());
		// 设置广播的优先级别大于Mainacitivity,这样如果消息来的时候正好在chat页面，直接显示消息，而不是提示消息未读
		intentFilter.setPriority(5);
		registerReceiver(receiver, intentFilter);

		// 注册一个ack回执消息的BroadcastReceiver
		IntentFilter ackMessageIntentFilter = new IntentFilter(EMChatManager
				.getInstance().getAckMessageBroadcastAction());
		ackMessageIntentFilter.setPriority(5);
		registerReceiver(ackMessageReceiver, ackMessageIntentFilter);

		// 注册一个消息送达的BroadcastReceiver
		IntentFilter deliveryAckMessageIntentFilter = new IntentFilter(
				EMChatManager.getInstance()
						.getDeliveryAckMessageBroadcastAction());
		deliveryAckMessageIntentFilter.setPriority(5);
		registerReceiver(deliveryAckMessageReceiver,
				deliveryAckMessageIntentFilter);
		EMChat.getInstance().setAppInited();

	}

	private void sendText(String content) {

		if (content.length() > 0) {
			EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
			// 如果是群聊，设置chattype,默认是单聊

			TextMessageBody txtBody = new TextMessageBody(content);
			// 设置消息body
			message.addBody(txtBody);
			// 设置要发给谁,用户username或者群聊groupid
			message.setReceipt(toChatUsername);
			LogUtil.d("toUserName", toChatUsername);
			// 把messgage加到conversation中
			conversation.addMessage(message);
			// 通知adapter有消息变动，adapter会根据加入的这条message显示消息和调用sdk的发送方法
			lvChatAdapter.refresh();
			lv_chat.setSelection(lv_chat.getCount() - 1);
			et_chat.setText("");

		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	        // 注销广播
	        try {
	            unregisterReceiver(receiver);
	            receiver = null;
	        } catch (Exception e) {
	        }
	        try {
	            unregisterReceiver(ackMessageReceiver);
	            ackMessageReceiver = null;
	            unregisterReceiver(deliveryAckMessageReceiver);
	            deliveryAckMessageReceiver = null;
	        } catch (Exception e) {
	        }
	}

	/**
	 * 消息广播接收者
	 * 
	 */
	private class NewMessageBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 记得把广播给终结掉
			abortBroadcast();

			String username = intent.getStringExtra("from");
			String msgid = intent.getStringExtra("msgid");
			// 收到这个广播的时候，message已经在db和内存里了，可以通过id获取mesage对象
			EMMessage message = EMChatManager.getInstance().getMessage(msgid);
			// 如果是群聊消息，获取到group id
			if (message.getChatType() == ChatType.GroupChat) {
				username = message.getTo();
			}
			if (!username.equals(toChatUsername)) {
				// 消息不是发给当前会话，return
				// notifyNewMessage(message);
				return;
			}
			// conversation =
			// EMChatManager.getInstance().getConversation(toChatUsername);
			// 通知adapter有新消息，更新ui
			lvChatAdapter.refresh();
			lv_chat.setSelection(lv_chat.getCount() - 1);

		}
	}

	/**
	 * 消息回执BroadcastReceiver
	 */
	private BroadcastReceiver ackMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			abortBroadcast();

			String msgid = intent.getStringExtra("msgid");
			String from = intent.getStringExtra("from");
			EMConversation conversation = EMChatManager.getInstance()
					.getConversation(from);
			if (conversation != null) {
				// 把message设为已读
				EMMessage msg = conversation.getMessage(msgid);
				if (msg != null) {
					msg.isAcked = true;
				}
			}
			lvChatAdapter.refresh();

		}
	};

	/**
	 * 消息送达BroadcastReceiver
	 */
	private BroadcastReceiver deliveryAckMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			abortBroadcast();

			String msgid = intent.getStringExtra("msgid");
			String from = intent.getStringExtra("from");
			EMConversation conversation = EMChatManager.getInstance()
					.  getConversation(from);
			if (conversation != null) {
				// 把message设为已读
				EMMessage msg = conversation.getMessage(msgid);
				if (msg != null) {
					msg.isDelivered = true;
				}
			}

			lvChatAdapter.refresh();
		}
	};
}
