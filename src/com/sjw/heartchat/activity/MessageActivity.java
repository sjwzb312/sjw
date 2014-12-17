package com.sjw.heartchat.activity;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.sjw.heartchat.R;
import com.sjw.heartchat.adapter.MsgCommentAdapter;
import com.sjw.heartchat.bean.CommentBean;
import com.sjw.heartchat.bean.MsgBean;
import com.sjw.heartchat.net.NetDataUtils;
import com.sjw.heartchat.widget.MsgLvHeader;

public class MessageActivity extends BaseActivity {
	private MsgLvHeader lvHeader;
	private ListView lv_msg;
	private String msgId;
	private EditText et_comment;
	private Button btn_send;
	private MsgBean msgBean;
	private MsgCommentAdapter commentAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msg_activity);
		initView();
		initListener();
		initData();

	}

	@Override
	public void initView() {
		super.initView();
		lv_msg = (ListView) findViewById(R.id.lv_msg);
		et_comment = (EditText) findViewById(R.id.et_comment);
		btn_send = (Button) findViewById(R.id.btn_send_comment);
		lvHeader=(MsgLvHeader) LayoutInflater.from(this).inflate(R.layout.msg_lv_header, null);
		lv_msg.addHeaderView(lvHeader);

	}

	@Override
	public void initListener() {
		super.initListener();
		btn_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String comment = et_comment.getText().toString();
				if (et_comment != null) {
					CommentBean commentBean = new CommentBean();
					commentBean.setMsgBeanId(msgId);
					commentBean.setComment(comment);
					sendComment(commentBean);
				}
			}
		});
	}

	@Override
	public void initData() {
		super.initData();
		msgBean = (MsgBean) getIntent().getSerializableExtra("msgBean");
		msgId = msgBean.getObjectId();
		logD(LOGTAG, msgBean.getObjectId() + "  " + msgBean.getMsg());
		lvHeader.setTvContent(msgBean.getMsg());
		commentAdapter = new MsgCommentAdapter(this);
		lv_msg.setAdapter(commentAdapter);
		getComments();// 获取评论
	}

	/**
	 * 发送评论
	 * 
	 * @param commentBean
	 */
	private void sendComment(final CommentBean commentBean) {
		pd.show();
		NetDataUtils.sendComment(context, commentBean, new SaveListener() {

			@Override
			public void onSuccess() {
				pd.dismiss();
				Toast("评论发送成功！");
				commentAdapter.addMoreItem(commentBean);
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				pd.dismiss();
				Toast("评论发送失败，请稍后再试！");
			}
		});
	}

	private void getComments() {
		pd.show();
		NetDataUtils.getAllCommentByMsgId(context, msgId,
				new FindListener<CommentBean>() {

					@Override
					public void onSuccess(List<CommentBean> arg0) {
						pd.dismiss();
						commentAdapter.addMore(arg0);

					}

					@Override
					public void onError(int arg0, String arg1) {
						Toast("评论获取失败");
						pd.dismiss();

					}
				});
	}

}
