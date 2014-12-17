package com.sjw.heartchat.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sjw.heartchat.R;
import com.sjw.heartchat.bean.CommentBean;

public class MsgCommentAdapter extends BaseAdapter {
	private Context context;
	private List<CommentBean> list;

	public MsgCommentAdapter(Context context) {
		super();
		this.context = context;
		this.list = new ArrayList<CommentBean>();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_comment_item, null);
			holder = new Holder();
			holder.tv_comment_item = (TextView) convertView
					.findViewById(R.id.tv_comment_itme);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		CommentBean commentBean = list.get(position);
		holder.tv_comment_item.setText(commentBean.getComment());
		return convertView;
	}

	private class Holder {
		TextView tv_comment_item;
	}

	/**
	 * 加载更多
	 * 
	 * @param msgList
	 */
	public void addMore(List<CommentBean> commentBeans) {
		if (commentBeans != null) {
			list.addAll(commentBeans);
			notifyDataSetChanged();
		}
	}

	/**
	 * 刷新
	 * 
	 * @param msgList
	 */
	public void refeList(List<CommentBean> commList) {
		if (commList != null) {
			list.addAll(0, commList);
			notifyDataSetChanged();
		}
	}

	/**
	 * 添加一个
	 * 
	 * @param msgBean
	 */
	public void addMoreItem(CommentBean commentBean) {
		if (commentBean != null) {
			list.add(commentBean);
			notifyDataSetChanged();
		}
	}
}
