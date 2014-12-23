package com.sjw.heartchat.widget;

import com.sjw.heartchat.utils.LogUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class LoadMoreListView extends ListView implements OnScrollListener {

	private LinearLayout ll_foot;
	private LoadMoreFooterView footerView;
	private Context context;
	private int visibleItemCount;
	private int visibleLastIndex;
	private int totalItemCount;
	private OnLoadMorListener loadMorListener;

	public OnLoadMorListener getLoadMorListener() {
		return loadMorListener;
	}

	public void setLoadMorListener(OnLoadMorListener loadMorListener) {
		this.loadMorListener = loadMorListener;
	}

	public LoadMoreListView(Context context) {
		super(context);

	}

	public LoadMoreListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	private void initView() {
		setOnScrollListener(this);
		addFoot();
	}

	private void addFoot() {
		footerView = new LoadMoreFooterView(context);
		addFooterView(footerView);
	}

	public void hideFoot() {
		//footerView.setLayoutParams(new LayoutParams(0, 0));
		removeFooterView(footerView);
	}

	public void showFoot() {
		footerView.setLayoutParams(new LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		this.totalItemCount = totalItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
		// LogUtil.d("onScroll", "firstVisibleItem= " + firstVisibleItem
		// + "visibleItemCount: " + visibleItemCount + "totalItemCount= "
		// + totalItemCount);
	}

	private boolean isCompleteLoad = true;

	public void completeLoad() {
		isCompleteLoad = true;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = totalItemCount; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		LogUtil.d("onScroll", "itemsLastIndex: " + itemsLastIndex
				+ "lastIndex :" + lastIndex);

		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			// 判断是否滚动到底部
			if (view.getLastVisiblePosition() == view.getCount() - 1) {
				// 加载更多功能的代码
				LogUtil.d("onScroll", "滑动到底部");
				if (isCompleteLoad) {
					if (getLoadMorListener() != null) {
						isCompleteLoad=false;
						getLoadMorListener().onLoadMore();
					}
				}
				// hideFoot();
			}
		}

	}

	public interface OnLoadMorListener {
		public void onLoadMore();
	}

}
