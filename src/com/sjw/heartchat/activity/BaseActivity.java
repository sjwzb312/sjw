package com.sjw.heartchat.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sjw.heartchat.R;
import com.sjw.heartchat.bean.UserBean;
import com.sjw.heartchat.inte.ViewInitface;
import com.sjw.heartchat.utils.LogUtil;
import com.sjw.heartchat.utils.SharePreUtil;
import com.sjw.heartchat.utils.ToastUtil;

public class BaseActivity extends FragmentActivity implements ViewInitface {

	public static String TAG = "bmob";
	//

	protected ListView mListview;
	protected BaseAdapter mAdapter;
	public ProgressDialog pd;
	public String LOGTAG;
	public Context context;
	public View leftView;
	public View rightView;
	public View tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;

	}

	@Override
	public void initView() {
		LOGTAG = getClass().getName();
		context = this;
		pd = new ProgressDialog(this);
		leftView=findViewById(R.id.title_left);
		rightView=findViewById(R.id.title_right);
		tv_title=findViewById(R.id.tv_title);

	}

	@Override
	public void initData() {
		

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	public void Toast(String msg) {
		ToastUtil.toast(this, msg);
	}

	public void logD(String tag, String msg) {
		LogUtil.d(tag, msg);

	}

	/**
	 * ��½�ɹ��󱣴�user���
	 * 
	 * @param userBean
	 */
	public void saveUser(UserBean userBean) {
		SharePreUtil.putStrToSp(this, SharePreUtil.SP_USER.USER_ID,
				userBean.getObjectId());
		SharePreUtil.putStrToSp(this, SharePreUtil.SP_USER.USER_NAME,
				userBean.getUsername());
		SharePreUtil.putStrToSp(this, SharePreUtil.SP_USER.USER_PWD,
				userBean.getPassword());

	}
	
	public String getSpUserName(){
		return SharePreUtil.getStrFroSp(context, SharePreUtil.SP_USER.USER_NAME, "-1");
	}
	
	public String getSpUserID(){
		return SharePreUtil.getStrFroSp(context, SharePreUtil.SP_USER.USER_ID, "-1");
	}

	/**
	 * ���view
	 * 
	 * @param viewId
	 * @return
	 */
	public View getViewById(int viewId) {
		return findViewById(viewId);
	}

	public boolean isLogin() {
		return TextUtils.isEmpty(SharePreUtil.getStrFroSp(context,
				SharePreUtil.SP_USER.USER_NAME, ""));
	}
	
	private void hideLeft(){
		if(leftView!=null){
			leftView.setVisibility(View.GONE);
		}
	}

	public void hideRight(){
		if(rightView!=null){
			rightView.setVisibility(View.GONE);
		}
	}
	
	public void showRight(){
		if(rightView!=null){
			rightView.setVisibility(View.VISIBLE);
		}
	}
	
	public void setLeftViewBg(int id){
		if(leftView!=null){
			((ImageView)leftView).setImageResource(id);
		}
	}
	
	public void setTvTitle(String title){
		if(tv_title!=null){
			((TextView)tv_title).setText(title);
		}
	}
}
