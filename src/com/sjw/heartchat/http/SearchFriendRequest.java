package com.sjw.heartchat.http;

import android.os.Handler;

import com.easemob.chat.EMContactManager;
import com.easemob.exceptions.EaseMobException;

public class SearchFriendRequest extends BaseRequest {

	public static void searchFriend(final String name, final ISearchListener listener) {
		final Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 200:
					if(listener!=null){
						listener.doSuccess();
					}
					break;
				case 1:
					if(listener!=null){
						listener.doFail();
					}
					break;
				default:
					break;
				}
			};
		};
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					EMContactManager.getInstance().addContact(name, "加个好友呗");
					handler.sendEmptyMessage(200);
				} catch (EaseMobException e) {
					e.printStackTrace();
					handler.sendEmptyMessage(1);
				}
			}
		}).start();

	}

	public interface ISearchListener {
		void doSuccess();

		void doFail();
	}

}
