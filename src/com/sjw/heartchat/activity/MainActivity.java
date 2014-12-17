package com.sjw.heartchat.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.sjw.heartchat.R;
import com.sjw.heartchat.activity.AddMsgActivity;
import com.sjw.heartchat.activity.BaseActivity;
import com.sjw.heartchat.adapter.MainVpAdapter;
import com.sjw.heartchat.fragment.NearMsgFragment;
import com.sjw.heartchat.fragment.PublicMsgFragment;

public class MainActivity extends BaseActivity {
	private ViewPager vp_main;
	private List<Fragment> fList;
	private Button btn_add_msg;
	private RadioGroup rg_tab;

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
		vp_main = (ViewPager) findViewById(R.id.vp_main);
		btn_add_msg = (Button) findViewById(R.id.btn_add);
		rg_tab = (RadioGroup) findViewById(R.id.rg_tab);
	}

	@Override
	public void initData() {
		super.initData();
		fList = new ArrayList<Fragment>();
		fList.add(new PublicMsgFragment());
		fList.add(new NearMsgFragment());
		vp_main.setAdapter(new MainVpAdapter(getSupportFragmentManager(), fList));

	}

	@Override
	public void initListener() {
		super.initListener();
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
				case R.id.rb_pub_msg:
					vp_main.setCurrentItem(0);
					break;
				case R.id.rb_near_msg:
					vp_main.setCurrentItem(1);
					break;
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
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		findViewById(R.id.btn_test).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(context, TestActivity.class));

			}
		});
	}

}
