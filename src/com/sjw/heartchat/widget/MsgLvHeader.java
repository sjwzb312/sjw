package com.sjw.heartchat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sjw.heartchat.R;
import com.sjw.heartchat.inte.ViewInitface;

public class MsgLvHeader extends LinearLayout implements ViewInitface {

	private Context context;
	private View contentView;
	private TextView tv_content;

	public MsgLvHeader(Context context) {
		super(context);
		this.context = context;
	}

	public MsgLvHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		initView();
	}

	@Override
	public void initView() {
		
		tv_content = (TextView) findViewById(R.id.msg_header_tv);
	}

	public void setTvContent(String msg) {
		if (tv_content != null) {
			tv_content.setText(msg);
		}
	}

	@Override
	public void initData() {

	}

	@Override
	public void initListener() {

	}

}
