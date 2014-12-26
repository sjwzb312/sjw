package com.sjw.heartchat.fragment;

import java.util.ArrayList;
import java.util.List;

import com.sjw.heartchat.HeartChatApplication;
import com.sjw.heartchat.R;
import com.viewpagerindicator.TabPageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AfterMealFragment extends BaseFragment {
	private View contentView;
	private ViewPager vp;
	private TabPageIndicator tab_page;
	private String[] DAYS = { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };
	private VpAdapter vpAdapter;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.fragment_after_meal_layout,
				null);
		initView();
		initData();
		initListener();
		return contentView;
	}

	@Override
	public void initView() {
		super.initView();
		tab_page = (TabPageIndicator) contentView.findViewById(R.id.tab_page);
		vp = (ViewPager) contentView.findViewById(R.id.vp_after);
		vpAdapter = new VpAdapter(getFragmentManager());
		vp.setAdapter(vpAdapter);
		tab_page.setViewPager(vp);

	}

	@Override
	public void initData() {
		super.initData();
		for (int i = 0; i < HeartChatApplication.day; i++) {
			fragmentList.add(new PublicMsgFragment());
		}
		vpAdapter.notifyDataSetChanged();
	}

	@Override
	public void initListener() {
		super.initListener();
	}

	private class VpAdapter extends FragmentPagerAdapter {

		public VpAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {

			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {

			return HeartChatApplication.day;
		}

		@Override
		public CharSequence getPageTitle(int position) {

			return getTitle(position);
		}
	}

	private String getTitle(int position) {

		if (position == 0) {
			return "今日";
		} else {
			return DAYS[HeartChatApplication.day - position - 1];
		}

	}

}
