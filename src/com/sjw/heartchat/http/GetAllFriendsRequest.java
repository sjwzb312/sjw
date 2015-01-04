package com.sjw.heartchat.http;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.easemob.chat.EMContactManager;
import com.easemob.exceptions.EaseMobException;
import com.sjw.heartchat.bean.UserBean;

public class GetAllFriendsRequest extends BaseRequest {

	public IGetAllFriendsListener friendsListener;

	public GetAllFriendsRequest(IGetAllFriendsListener friendsListener) {
		this.friendsListener = friendsListener;
	}

	public void request() {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					List<String> usernames = EMContactManager.getInstance()
							.getContactUserNames();
					List<UserBean> userBeans = new ArrayList<UserBean>();
					for (String userName : usernames) {
						if (!TextUtils.isEmpty(userName)) {
							UserBean userBean = new UserBean();
							userBean.setUsername(userName);
							userBeans.add(userBean);
						}
					}
					Message message = new Message();
					message.what = 0;
					message.obj = userBeans;
					handler.sendMessage(message);
				} catch (EaseMobException e) {
					Message message = new Message();
					message.what = 1;
					e.printStackTrace();
				}

			}
		}).start();

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				List<UserBean> list = (List<UserBean>) msg.obj;
				if (friendsListener != null) {
					friendsListener.doFinish(list);
				}
				break;

			case 1:
				if (friendsListener != null) {
					friendsListener.doFail();
				}
				break;
			default:
				break;
			}
		};
	};

	public interface IGetAllFriendsListener {
		void doFinish(List<UserBean> list);

		void doFail();
	}
}
