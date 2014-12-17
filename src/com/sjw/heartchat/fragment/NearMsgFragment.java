package com.sjw.heartchat.fragment;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.FindListener;

import com.sjw.heartchat.R;
import com.sjw.heartchat.activity.MessageActivity;
import com.sjw.heartchat.adapter.PublicMsgAdapter;
import com.sjw.heartchat.bean.MsgBean;
import com.sjw.heartchat.utils.Contans.BroadCastAction;
import com.sjw.heartchat.utils.LocationUtils.LoactionListener;
import com.sjw.heartchat.utils.LocationUtils.LocationBean;
import com.sjw.heartchat.utils.LocationUtils;
import com.sjw.heartchat.utils.ToastUtil;
import com.sjw.heartchat.widget.LoadMoreListView;
import com.sjw.heartchat.widget.LoadMoreListView.OnLoadMorListener;

public class NearMsgFragment extends BaseFragment implements OnRefreshListener,
		OnLoadMorListener {
	private LoadMoreListView lv_pub_msg;
	private View contentView;
	private PublicMsgAdapter msgAdapter;
	private SwipeRefreshLayout sRefreshLayout;
	private int index = 10;
	private int curPage = 0;
	private LocationUtils locationUtils;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.fragment_pub_msg_layout, null);
		locationUtils = new LocationUtils(getActivity());
		initView();
		initData();
		initListener();
		return contentView;
	}

	@Override
	public void initView() {
		super.initView();
		lv_pub_msg = (LoadMoreListView) getContentView(R.id.lv_pub_msg);
		lv_pub_msg.setLoadMorListener(this);
		sRefreshLayout = (SwipeRefreshLayout) getContentView(R.id.id_swipe_ly);
		sRefreshLayout.setColorScheme(android.R.color.holo_blue_light,
				android.R.color.holo_red_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light);
	}

	@Override
	public void initData() {
		super.initData();
		msgAdapter = new PublicMsgAdapter(getActivity());
		lv_pub_msg.setAdapter(msgAdapter);
		if (geoPoint != null) {
			queryMsg(false);
		} else {
			getLocationAndQureyMsg(false);
		}

	}

	@Override
	public void initListener() {
		super.initListener();
		initBrocast();
		lv_pub_msg.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
		sRefreshLayout.setRefreshing(true);
		sRefreshLayout.setOnRefreshListener(this);

	}

	private View getContentView(int id) {
		return contentView.findViewById(id);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(addMsgBroCastReceiver);
	}

	/**
	 * 查询所有的信息
	 */
	private void queryMsg(final boolean isRe) {
		BmobQuery<MsgBean> msgQuery = new BmobQuery<MsgBean>();
		msgQuery.addWhereNear("gpsAdd", geoPoint);
		if (isRe) {
			index = 5;
		} else {
			index = 10;
		}
		msgQuery.setLimit(index);
		msgQuery.order("-msgTime");
		if (!isRe) {
			msgQuery.setSkip(curPage * index);
		}

		msgQuery.findObjects(getActivity(), new FindListener<MsgBean>() {

			@Override
			public void onError(int arg0, String arg1) {
				// pdDialog.dismiss();
				sRefreshLayout.setRefreshing(false);
				if (!isRe) {
					lv_pub_msg.completeLoad();
				}
			}

			@Override
			public void onSuccess(List<MsgBean> arg0) {
				// pdDialog.dismiss();
				// index += arg0.size();
				if (isRe) {
					msgAdapter.refeList(arg0);
				} else {
					msgAdapter.loadItems(arg0);
					curPage++;
				}
				if (!isRe) {
					lv_pub_msg.completeLoad();
				}
				sRefreshLayout.setRefreshing(false);

			}
		});
	}

	@Override
	public void onRefresh() {
		if (geoPoint != null) {
			queryMsg(true);
		} else {
			getLocationAndQureyMsg(true);
		}

	}

	@Override
	public void onLoadMore() {
		if (geoPoint != null) {
			queryMsg(false);
		} else {
			getLocationAndQureyMsg(false);
		}

	}

	private void initBrocast() {
		IntentFilter filter = new IntentFilter(BroadCastAction.ADD_MSG_BROCAST);
		getActivity().registerReceiver(addMsgBroCastReceiver, filter);
	}

	private BroadcastReceiver addMsgBroCastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			ToastUtil.toast(context, "保存信息");
			sRefreshLayout.setRefreshing(true);
			queryMsg(true);

		}

	};

	private BmobGeoPoint geoPoint;

	private void getLocationAndQureyMsg(final boolean isRe) {
		locationUtils.startLoaction(new LoactionListener() {

			@Override
			public void onCompleteLocation(LocationBean locationBean) {
				geoPoint = new BmobGeoPoint();
				geoPoint.setLatitude(locationBean.lat);
				geoPoint.setLongitude(locationBean.lon);
				queryMsg(isRe);
			}
		});
	}
}
