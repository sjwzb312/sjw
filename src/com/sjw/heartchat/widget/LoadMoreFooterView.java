package com.sjw.heartchat.widget;

import com.sjw.heartchat.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class LoadMoreFooterView extends LinearLayout {

	private View v;
	private Context context;

	public LoadMoreFooterView(Context context) {
		super(context);
		this.context = context;
		initView();
	}

	private void initView() {
		v = LayoutInflater.from(context).inflate(R.layout.lv_load_more_foot,
				this);
	}
}
