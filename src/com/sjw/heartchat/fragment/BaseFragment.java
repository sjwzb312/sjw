package com.sjw.heartchat.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.sjw.heartchat.inte.ViewInitface;

public class BaseFragment extends Fragment implements ViewInitface{
	public ProgressDialog pdDialog;
	public Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initView() {
		pdDialog=new ProgressDialog(getActivity());
		context=getActivity();
		
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		
	}
	
	

}
