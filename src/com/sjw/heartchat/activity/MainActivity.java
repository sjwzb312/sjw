package com.sjw.heartchat.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactListener;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMMessage;
import com.sjw.heartchat.R;
import com.sjw.heartchat.adapter.MainVpAdapter;
import com.sjw.heartchat.fragment.AfterMealFragment;
import com.sjw.heartchat.fragment.FriendFragment;
import com.sjw.heartchat.fragment.MineFragment;
import com.sjw.heartchat.utils.LogUtil;
import com.sjw.heartchat.widget.MyViewPager;

public class MainActivity extends BaseActivity {
	private MyViewPager vp_main;
	private List<Fragment> fList;
	private Button btn_add_msg;
	private RadioGroup rg_tab;
	private NewMessageBroadcastReceiver msgReceiver;
	private MineFragment mineFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_layout);
		initView();
		initData();
		initListener();
	}

	@Override
	public void initView() {
		super.initView();
		vp_main = (MyViewPager) findViewById(R.id.vp_main);
		btn_add_msg = (Button) findViewById(R.id.btn_add);
		rg_tab = (RadioGroup) findViewById(R.id.rg_tab);
		hideRight();
	}

	@Override
	public void initData() {
		super.initData();
		mineFragment=new MineFragment();
		fList = new ArrayList<Fragment>();
		fList.add(new AfterMealFragment());
		fList.add(new FriendFragment());
		fList.add(mineFragment);
		// fList.add(new SetFragment());
		// fList.add(new NearMsgFragment());
		vp_main.setOffscreenPageLimit(fList.size());
		vp_main.setAdapter(new MainVpAdapter(getSupportFragmentManager(), fList));

	}
	
	private void initBroad(){
		// 注册一个接收消息的BroadcastReceiver
        msgReceiver = new NewMessageBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(EMChatManager
                .getInstance().getNewMessageBroadcastAction());
        intentFilter.setPriority(3);
        registerReceiver(msgReceiver, intentFilter);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(msgReceiver);
	}

	@Override
	public void initListener() {
		super.initListener();
		initBroad();
		EMContactManager.getInstance().setContactListener(
                new MyContactListener());//注册个关于好友的监听
		btn_add_msg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(context, AddMsgActivity.class));
			}
		});
		rg_tab.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_after:
					vp_main.setCurrentItem(0);
					hideRight();
					break;
				case R.id.rb_friend:
					vp_main.setCurrentItem(1);
					// hideRight();
					showRight();
					rightView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(context,
									AddFriendsActivity.class);
							startActivity(intent);

						}
					});
					break;
				case R.id.rb_mine:
					vp_main.setCurrentItem(2);
					showRight();
					rightView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(context,
									AddMsgActivity.class);
							startActivity(intent);

						}
					});
					break;
				// case R.id.rb_set:
				// vp_main.setCurrentItem(3);
				// break;
				default:
					break;
				}

			}
		});
		vp_main.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				((RadioButton) rg_tab.getChildAt(arg0)).setChecked(true);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		findViewById(R.id.btn_test).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(context, TestActivity.class));

			}
		});
	}

	private class MyContactListener implements EMContactListener {

		@Override
		public void onContactAdded(List<String> arg0) {
			// 添加

		}

		@Override
		public void onContactAgreed(String arg0) {
			// 同意
		}

		@Override
		public void onContactDeleted(List<String> arg0) {
			// 删除

		}

		@Override
		public void onContactInvited(String arg0, String arg1) {
			// 好友请求
			LogUtil.d("MainActivity", "用户名："+arg0+"  "+arg1);

		}

		@Override
		public void onContactRefused(String arg0) {
			// 拒绝

		}

	}

	
	/**
    * 新消息广播接收者
    * 
    * 
    */
   private class NewMessageBroadcastReceiver extends BroadcastReceiver {
       @Override
       public void onReceive(Context context, Intent intent) {
           // 主页面收到消息后，主要为了提示未读，实际消息内容需要到chat页面查看
           Toast.makeText(context, "接到新消息了。。", 0).show();
           String from = intent.getStringExtra("from");
           // 消息id
           String msgId = intent.getStringExtra("msgid");
           EMMessage message = EMChatManager.getInstance().getMessage(msgId);
           // 2014-10-22 修复在某些机器上，在聊天页面对方发消息过来时不立即显示内容的bug
//           if (ChatActivity.activityInstance != null) {
//               if (message.getChatType() == ChatType.GroupChat) {
//                   if (message.getTo().equals(
//                           ChatActivity.activityInstance.getToChatUsername()))
//                       return;
//               } else {
//                   if (from.equals(ChatActivity.activityInstance
//                           .getToChatUsername()))
//                       return;
//               }
//           }

           // 注销广播接收者，否则在ChatActivity中会收到这个广播
           abortBroadcast();

          // notifyNewMessage(message);

           // 刷新bottom bar消息未读数
//           updateUnreadLabel();
//           if (currentTabIndex == 0) {
//               // 当前页面如果为聊天历史页面，刷新此页面
//               if (chatHistoryFragment != null) {
//                   chatHistoryFragment.refresh();
//               }
//           }

       }
   }
}
