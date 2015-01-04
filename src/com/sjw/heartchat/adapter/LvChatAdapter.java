package com.sjw.heartchat.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.sjw.heartchat.R;
import com.sjw.heartchat.utils.LogUtil;

/**
 * 聊天adapter
 * 
 * @author gg
 * 
 */
public class LvChatAdapter extends BaseLvAdapter<EMMessage> {
	

	private static final int MESSAGE_TYPE_RECV_TXT = 0;
	private static final int MESSAGE_TYPE_SENT_TXT = 1;
	private static final int MESSAGE_TYPE_SENT_IMAGE = 2;
	private static final int MESSAGE_TYPE_SENT_LOCATION = 3;
	private static final int MESSAGE_TYPE_RECV_LOCATION = 4;
	private static final int MESSAGE_TYPE_RECV_IMAGE = 5;
	private static final int MESSAGE_TYPE_SENT_VOICE = 6;
	private static final int MESSAGE_TYPE_RECV_VOICE = 7;
	private static final int MESSAGE_TYPE_SENT_VIDEO = 8;
	private static final int MESSAGE_TYPE_RECV_VIDEO = 9;
	private static final int MESSAGE_TYPE_SENT_FILE = 10;
	private static final int MESSAGE_TYPE_RECV_FILE = 11;
	private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 12;
	private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 13;
	public static final int TYPE_SIZE = 2;

	private EMConversation emConversation;

	

	public LvChatAdapter(Context context, String toUserName) {
		super(context);
		this.context = context;
		emConversation = EMChatManager.getInstance()
				.getConversation(toUserName);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return emConversation.getMsgCount();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		EMMessage emMessage = emConversation.getMessage(position);
		if (convertView == null) {
			holder = new Holder();
			switch (getItemViewType(position)) {
			case MESSAGE_TYPE_RECV_TXT:
				convertView = LayoutInflater.from(context).inflate(
						R.layout.adapter_chat_left_item, null);
				holder.tv_left = (TextView) convertView
						.findViewById(R.id.tv_chat_left);

				convertView.setTag(holder);
				break;

			case MESSAGE_TYPE_SENT_TXT:
				convertView = LayoutInflater.from(context).inflate(
						R.layout.adapter_chat_right_item, null);
				holder.tv_right = (TextView) convertView
						.findViewById(R.id.tv_chat_right);
				holder.tv_right_state = (TextView) convertView
						.findViewById(R.id.tv_right_state);
				holder.pb_right = (ProgressBar) convertView
						.findViewById(R.id.pb_right_chat);
				convertView.setTag(holder);
				break;
			default:
				break;

			}
		} else {
			holder = (Holder) convertView.getTag();
		}
		switch (getItemViewType(position)) {
		case MESSAGE_TYPE_RECV_TXT:
			TextMessageBody txtBody = (TextMessageBody) emMessage.getBody();

			// 设置内容
			holder.tv_left.setText(txtBody.getMessage());
			// handleTextMessage(emMessage, holder, position);
			break;

		case MESSAGE_TYPE_SENT_TXT:
			TextMessageBody rightBody = (TextMessageBody) emMessage.getBody();

			// 设置内容
			holder.tv_right.setText(rightBody.getMessage());
			handleTextMessage(emMessage, holder, position);
			break;
		default:
			break;
		}

		return convertView;
	}

	private class Holder {
		TextView tv_left;
		TextView tv_right;
		ProgressBar pb_right;
		TextView tv_right_state;
	}

	@Override
	public int getItemViewType(int position) {
		EMMessage message = emConversation.getMessage(position);
		if (message.getType() == EMMessage.Type.TXT) {
			return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_TXT
					: MESSAGE_TYPE_SENT_TXT;
		}
		if (message.getType() == EMMessage.Type.IMAGE) {
			return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_IMAGE
					: MESSAGE_TYPE_SENT_IMAGE;

		}
		if (message.getType() == EMMessage.Type.LOCATION) {
			return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_LOCATION
					: MESSAGE_TYPE_SENT_LOCATION;
		}
		if (message.getType() == EMMessage.Type.VOICE) {
			return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE
					: MESSAGE_TYPE_SENT_VOICE;
		}
		if (message.getType() == EMMessage.Type.VIDEO) {
			return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO
					: MESSAGE_TYPE_SENT_VIDEO;
		}
		if (message.getType() == EMMessage.Type.FILE) {
			return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_FILE
					: MESSAGE_TYPE_SENT_FILE;
		}

		return -1;// invalid
	}

	@Override
	public int getViewTypeCount() {

		return TYPE_SIZE;
	}

	/**
	 * 文本消息
	 * 
	 * @param message
	 * @param holder
	 * @param position
	 */
	private void handleTextMessage(EMMessage message, Holder holder,
			final int position) {

		if (message.direct == EMMessage.Direct.SEND) {
			switch (message.status) {
			case SUCCESS: // 发送成功
				holder.pb_right.setVisibility(View.GONE);
				holder.tv_right_state.setVisibility(View.GONE);
				break;
			case FAIL: // 发送失败
				holder.pb_right.setVisibility(View.GONE);
				holder.tv_right_state.setVisibility(View.VISIBLE);
				break;
			case INPROGRESS: // 发送中
				holder.pb_right.setVisibility(View.VISIBLE);
				holder.tv_right_state.setVisibility(View.GONE);
				break;
			default:
				// 发送消息
				sendMsgInBackground(message, holder);
			}
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param message
	 * @param holder
	 * @param position
	 */
	public void sendMsgInBackground(final EMMessage message, final Holder holder) {
		holder.tv_right_state.setVisibility(View.GONE);
		holder.pb_right.setVisibility(View.VISIBLE);

		final long start = System.currentTimeMillis();
		// 调用sdk发送异步发送方法
		EMChatManager.getInstance().sendMessage(message, new EMCallBack() {

			@Override
			public void onSuccess() {

				updateSendedView(message, holder, null);
			}

			@Override
			public void onError(int code, String error) {

				updateSendedView(message, holder, error);
			}

			@Override
			public void onProgress(int progress, String status) {
			}

		});

	}

	/**
	 * 更新ui上消息发送状态
	 * 
	 * @param message
	 * @param holder
	 */
	private void updateSendedView(final EMMessage message, final Holder holder,
			final String error) {
		((Activity) context).runOnUiThread(new Runnable() {
			@Override
			public void run() {

				if (message.status == EMMessage.Status.SUCCESS) {
					// if (message.getType() == EMMessage.Type.FILE) {
					// holder.pb.setVisibility(View.INVISIBLE);
					// holder.staus_iv.setVisibility(View.INVISIBLE);
					// } else {
					// holder.pb.setVisibility(View.GONE);
					// holder.staus_iv.setVisibility(View.GONE);
					// }
					Toast.makeText(context, "发送成功", 0).show();

				} else if (message.status == EMMessage.Status.FAIL) {
					// if (message.getType() == EMMessage.Type.FILE) {
					// holder.pb.setVisibility(View.INVISIBLE);
					// } else {
					// holder.pb.setVisibility(View.GONE);
					// }
					// holder.staus_iv.setVisibility(View.VISIBLE);
					LogUtil.d("sendError", error);
					Toast.makeText(context, "发送失败" + error, 0).show();
				}

				notifyDataSetChanged();
			}
		});
	}
}
