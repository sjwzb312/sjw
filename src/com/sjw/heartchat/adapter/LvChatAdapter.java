package com.sjw.heartchat.adapter;

import com.sjw.heartchat.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobMsg;

/**
 * 聊天adapter
 * 
 * @author gg
 * 
 */
public class LvChatAdapter extends BaseLvAdapter<BmobMsg> {
	public static final int TYPE_LEFT = 0;
	public static final int TYPE_RIGHT = 1;
	public static final int TYPE_SIZE = 2;
	private String currentUserId;

	public LvChatAdapter(Context context) {
		this.context = context;
		currentUserId = BmobUserManager.getInstance(context)
				.getCurrentUserObjectId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		BmobMsg bmobMsg = itemList.get(position);
		if (convertView == null) {
			holder = new Holder();
			switch (getItemViewType(position)) {
			case TYPE_LEFT:
				convertView = LayoutInflater.from(context).inflate(
						R.layout.adapter_chat_left_itme, null);
				holder.tv_left = (TextView) convertView
						.findViewById(R.id.tv_chat_left);
				convertView.setTag(holder);
				break;

			case TYPE_RIGHT:
				convertView = LayoutInflater.from(context).inflate(
						R.layout.adapter_chat_right_itme, null);
				holder.tv_left = (TextView) convertView
						.findViewById(R.id.tv_chat_right);
				convertView.setTag(holder);
				break;
			default:
				break;

			}
		} else {
			holder = (Holder) convertView.getTag();
		}
		switch (getItemViewType(position)) {
		case TYPE_LEFT:
			holder.tv_left.setText(bmobMsg.getContent());
			break;

		case TYPE_RIGHT:
			holder.tv_right.setText(bmobMsg.getContent());
			break;
		default:
			break;
		}

		return convertView;
	}

	private class Holder {
		TextView tv_left;
		TextView tv_right;
	}

	@Override
	public int getItemViewType(int position) {
		BmobMsg bmobMsg = itemList.get(position);
//		if (bmobMsg.getBelongId().equals(currentUserId)) {
//			return TYPE_RIGHT;
//		}
		return TYPE_LEFT;
	}

	@Override
	public int getViewTypeCount() {

		return TYPE_SIZE;
	}

}
