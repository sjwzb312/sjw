package com.sjw.heartchat.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import cn.bmob.v3.listener.UpdateListener;

import com.sjw.heartchat.R;
import com.sjw.heartchat.activity.ChatActivity;
import com.sjw.heartchat.activity.MessageActivity;
import com.sjw.heartchat.bean.MsgBean;
import com.sjw.heartchat.utils.LogUtil;
import com.sjw.heartchat.utils.ToastUtil;

public class PublicMsgAdapter extends BaseAdapter {
	private List<MsgBean> beanList;
	private Context context;
	public static int bgColors[] = { android.R.color.holo_blue_light,
			android.R.color.holo_orange_light, android.R.color.holo_green_light };
	public List<String> msgIds = new ArrayList<String>();

	public PublicMsgAdapter(Context context) {
		this.context = context;
		beanList = new ArrayList<MsgBean>();
	}

	@Override
	public int getCount() {
		return beanList.size();
	}

	public PublicMsgAdapter() {
		super();
	}

	@Override
	public MsgBean getItem(int position) {
		return beanList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHoder hoder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.public_msg_item_layout, null);
			hoder = new ViewHoder();
			hoder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_msg_contnet);
			hoder.btn_chart = (Button) getView(convertView, R.id.btn_chat);
			hoder.btn_praise = (Button) getView(convertView, R.id.btn_praise);

			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
		MsgBean msgBean = beanList.get(position);
		hoder.btn_chart.setOnClickListener(myClickListener);
		hoder.btn_praise.setOnClickListener(myClickListener);
		hoder.btn_chart.setTag(msgBean);
		hoder.tv_content.setText(msgBean.getMsg());
		hoder.btn_praise.setText("赞(" + msgBean.getPraiseCount() + ")");
		hoder.btn_praise.setTag(msgBean.getPraiseCount());
		LogUtil.d("userName", "userName "+msgBean.getUserName()+" id "+msgBean.getUserBean().getObjectId());
//		convertView.setBackgroundResource(bgColors[new Random()
//				.nextInt(bgColors.length)]);
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MsgBean bean = getItem(position);
				Intent intent = new Intent(context, MessageActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("msgBean", bean);
				intent.putExtras(bundle);
				context.startActivity(intent);

			}
		});
		/**
		 * 赞一个
		 */
		hoder.btn_praise.setOnClickListener(new PraisClickListener(msgBean,
				hoder.btn_praise));

		return convertView;
	}

	private class PraisClickListener implements OnClickListener {

		private MsgBean msgBean;
		private Button button;

		public PraisClickListener(MsgBean msgBean, Button button) {
			super();
			this.button = button;
			this.msgBean = msgBean;
		}

		@Override
		public void onClick(View v) {
			msgBean.increment("praiseCount");
			msgBean.update(context, new UpdateListener() {

				@Override
				public void onSuccess() {

					int count = (Integer) button.getTag();
					ToastUtil.toast(context, "" + (count + 1));
					button.setText("赞" + "(" + (count + 1) + ")");
					button.setTag(count + 1);

				}

				@Override
				public void onFailure(int arg0, String arg1) {
					ToastUtil.toast(context, "" + msgBean.getPraiseCount() + 1);

				}
			});

		}
	}

	private class ViewHoder {
		TextView tv_content;
		Button btn_chart;
		Button btn_praise;

	}

	private void adapterDataChange() {
		this.notifyDataSetChanged();

	}

	private View getView(View v, int viewId) {
		return v.findViewById(viewId);
	}

	/**
	 * 添加item
	 * 
	 * @param lsit
	 */
	public void loadItems(List<MsgBean> list) {
		if (list != null) {
			for (MsgBean msgBean : list) {
				beanList.add(msgBean);
				msgIds.add(msgBean.getObjectId());
			}
			notifyDataSetChanged();

		}
	}

	public void refeList(List<MsgBean> list) {
		if (list != null) {
			for (MsgBean msgBean : list) { //d751e86db0  a49ebf632b 
				if (!msgIds.contains(msgBean.getObjectId())) { // 判断是否已经加载过了
					beanList.add(0, msgBean);
					msgIds.add(msgBean.getObjectId());
					notifyDataSetChanged();
				}

			}

		}

	}

	private OnClickListener myClickListener = new OnClickListener() {
  
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_chat: // 聊天
				MsgBean msgBean = (MsgBean) v.getTag();
				Intent intent = new Intent(context, ChatActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("msgBean", msgBean);
				intent.putExtras(bundle);
				context.startActivity(intent);
				break;

			default:
				break;
			}

		}
	};

}
