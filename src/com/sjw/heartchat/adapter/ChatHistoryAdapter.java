package com.sjw.heartchat.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.sjw.heartchat.R;
import com.sjw.heartchat.bean.MsgBean;

public class ChatHistoryAdapter extends BaseLvAdapter<EMConversation> {

	public ChatHistoryAdapter(Context context) {
		super(context);
		itemList.addAll(loadConversationsWithRecentChat());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.adapter_chat_history_item,
					null);
			holder.tv_chat_msg = (TextView) convertView
					.findViewById(R.id.tv_chat_msg);
			holder.tv_chat_name = (TextView) convertView
					.findViewById(R.id.tv_chat_name);
			holder.iv_chat_face = (ImageView) convertView
					.findViewById(R.id.iv_chat_face);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		// 获取与此用户/群组的会话
		EMConversation conversation = itemList.get(position);
		// 获取用户username或者群组groupid
		String username = conversation.getUserName();
		EMMessage message = conversation.getLastMessage();
		TextMessageBody txtBody = (TextMessageBody) message.getBody();
		String content = txtBody.getMessage();
		holder.tv_chat_name.setText(username);
		holder.tv_chat_msg.setText(content);
		return convertView;
	}

	class Holder {
		TextView tv_chat_name;
		TextView tv_chat_msg;
		ImageView iv_chat_face;
	}

	/**
	 * 获取所有会话
	 * 
	 * @param context
	 * @return
	 */
	private List<EMConversation> loadConversationsWithRecentChat() {
		// 获取所有会话，包括陌生人
		Hashtable<String, EMConversation> conversations = EMChatManager
				.getInstance().getAllConversations();
		List<EMConversation> list = new ArrayList<EMConversation>();
		// 过滤掉messages seize为0的conversation
		for (EMConversation conversation : conversations.values()) {
			if (conversation.getAllMessages().size() != 0)
				list.add(conversation);
		}
		// 排序
		sortConversationByLastChatTime(list);
		return list;
	}

	/**
	 * 根据最后一条消息的时间排序
	 * 
	 * @param usernames
	 */
	private void sortConversationByLastChatTime(
			List<EMConversation> conversationList) {
		Collections.sort(conversationList, new Comparator<EMConversation>() {
			@Override
			public int compare(final EMConversation con1,
					final EMConversation con2) {

				EMMessage con2LastMessage = con2.getLastMessage();
				EMMessage con1LastMessage = con1.getLastMessage();
				if (con2LastMessage.getMsgTime() == con1LastMessage
						.getMsgTime()) {
					return 0;
				} else if (con2LastMessage.getMsgTime() > con1LastMessage
						.getMsgTime()) {
					return 1;
				} else {
					return -1;
				}
			}

		});
	}

	public MsgBean getMsgBean(int position) {
		MsgBean msgBean = new MsgBean();
		TextMessageBody textMessageBody = (TextMessageBody) itemList
				.get(position).getLastMessage().getBody();
		msgBean.setMsg(textMessageBody.getMessage());
		msgBean.setUserName(itemList.get(position).getUserName());
		return msgBean;

	}
}
