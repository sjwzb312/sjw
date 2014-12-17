package com.sjw.heartchat.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainVpAdapter extends FragmentPagerAdapter {
	private List<Fragment> fList;

	public MainVpAdapter(FragmentManager fm, List<Fragment> fList) {
		super(fm);
		this.fList = fList;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fList.get(arg0);
	}

	@Override
	public int getCount() {
		return fList.size();
	}

}
