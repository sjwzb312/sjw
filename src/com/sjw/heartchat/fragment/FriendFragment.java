package com.sjw.heartchat.fragment;

import java.util.List;

import com.sjw.heartchat.R;
import com.sjw.heartchat.adapter.FriendLvAdapter;
import com.sjw.heartchat.bean.UserBean;
import com.sjw.heartchat.http.GetAllFriendsRequest;
import com.sjw.heartchat.http.GetAllFriendsRequest.IGetAllFriendsListener;
import com.sjw.heartchat.utils.ToastUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class FriendFragment extends BaseFragment {
	private ListView lv_friend;
	private View contentView;
	private FriendLvAdapter friendLvAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.fragment_friend_layout, null);
		initView();
		initData();
		initListener();
		return contentView;
	}

	@Override
	public void initView() {
		super.initView();
		lv_friend = (ListView) contentView.findViewById(R.id.lv_friend);
	}

	@Override
	public void initData() {
		super.initData();
		friendLvAdapter = new FriendLvAdapter(context);
		lv_friend.setAdapter(friendLvAdapter);
		getAllFriends();

	}

	@Override
	public void initListener() {
		super.initListener();
	}

	private void getAllFriends() {
		GetAllFriendsRequest friendsRequest = new GetAllFriendsRequest(
				new IGetAllFriendsListener() {

					@Override
					public void doFinish(List<UserBean> list) {
						if (list.size() == 0) {
							ToastUtil.toast(context, "没有联系人");
						} else {
							friendLvAdapter.addMore(list);
						}

					}

					@Override
					public void doFail() {
						ToastUtil.toast(context, "获取联系人失败");

					}
				});
		friendsRequest.request();
	}

}
