package com.sjw.heartchat.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseLvAdapter<E> extends BaseAdapter {

	public List<E> itemList = new ArrayList<E>();
	public Context context;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void addOne(E e) {
		itemList.add(e);
		notifyDataSetChanged();
	}

	public void refreshOne(E e) {
		itemList.add(0, e);
	}

	public void addMore(List<E> list) {
		itemList.addAll(list);
		notifyDataSetChanged();
	}

	public void refreshMore(List<E> list) {
		itemList.addAll(0, list);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return super.getViewTypeCount();
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}

}
