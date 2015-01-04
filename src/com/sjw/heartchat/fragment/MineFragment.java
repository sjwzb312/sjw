package com.sjw.heartchat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.sjw.heartchat.R;
import com.sjw.heartchat.activity.ChatActivity;
import com.sjw.heartchat.adapter.ChatHistoryAdapter;
import com.sjw.heartchat.bean.MsgBean;

public class MineFragment extends BaseFragment {
	private View contentView;
	private ListView lv_chat;
	private ChatHistoryAdapter chatHistoryAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.fragment_mine_layout, null);
		initView();
		initData();
		initListener();
		return contentView;
	}

	@Override
	public void onResume() {
		super.onResume();
		chatHistoryAdapter.notifyDataSetChanged();
	}
	@Override
	public void initView() {
		lv_chat = (ListView) contentView.findViewById(R.id.lv_chat);
		super.initView();
	}

	@Override
	public void initData() {
		super.initData();
		chatHistoryAdapter = new ChatHistoryAdapter(context);
		lv_chat.setAdapter(chatHistoryAdapter);
	}

	@Override
	public void initListener() {
		super.initListener();
		lv_chat.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(context, ChatActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("msgBean",
						chatHistoryAdapter.getMsgBean(position));
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
	}
}
