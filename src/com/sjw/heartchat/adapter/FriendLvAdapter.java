package com.sjw.heartchat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjw.heartchat.R;
import com.sjw.heartchat.bean.UserBean;

public class FriendLvAdapter extends BaseLvAdapter<UserBean> {

	public FriendLvAdapter(Context context) {
		super(context);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.adapter_friend_item, null);
			holder.iv_friend_face = (ImageView) convertView
					.findViewById(R.id.iv_friend_face);
			holder.tv_chat_msg = (TextView) convertView
					.findViewById(R.id.tv_chat_msg);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_friend_name);
			convertView.setTag(holder);
		} else {
			holder=(Holder) convertView.getTag();
		}
		UserBean userBean = itemList.get(position);
		holder.tv_name.setText(userBean.getUsername());
		return convertView;
	}

	class Holder {
		ImageView iv_friend_face;
		TextView tv_name, tv_chat_msg;
	}

}
