package com.sjw.heartchat.utils;

import android.app.Activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.sjw.heartchat.HeartChatApplication;
import com.sjw.heartchat.utils.SharePreUtil.SP_LOACTION_INFO;

/**
 * 定位工具类
 * 
 * @author gg
 * 
 */
public class LocationUtils {
	private LocationClient mLocationClient;
	private Activity context;

	public LocationUtils(Activity context) {
		super();
		this.context = context;
	}

	public void startLoaction(final LoactionListener listener) {
		mLocationClient = ((HeartChatApplication) (context.getApplication())).mLocationClient;
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("gcj02");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation arg0) {
				stopLocation();
				if (arg0 != null) {
					LocationBean locationBean = new LocationBean();
					locationBean.addr = arg0.getAddrStr();
					locationBean.city = arg0.getCity();
					locationBean.lat = arg0.getLatitude();
					locationBean.lon = arg0.getLongitude();
					LogUtil.d(
							"locationInfo",
							"addr " + "  " + arg0.getAddrStr() + " lat: "
									+ arg0.getLatitude() + " lon "
									+ arg0.getLongitude() + " code "
									+ arg0.getLocType());
					if (listener != null) {
						listener.onCompleteLocation(locationBean);
					}
					saveLocationToSp(locationBean);

				} else {
					ToastUtil.toast(context, "定位失败");
				}

			}
		});
		mLocationClient.start();
	}

	/**
	 * 停止定位
	 */
	private void stopLocation() {
		if (mLocationClient != null) {
			mLocationClient.stop();
		}
	}

	public class LocationBean {
		public double lat;
		public double lon;
		public String addr;
		public String city;
	}

	/**
	 * 定位完成的回调
	 * 
	 * @author gg
	 * 
	 */
	public interface LoactionListener {
		void onCompleteLocation(LocationBean locationBean);
	}

	/**
	 * 保存定位信息
	 * 
	 * @param locationBean
	 */
	private void saveLocationToSp(LocationBean locationBean) {
		SharePreUtil.putStrToSp(context, SP_LOACTION_INFO.LOCATION_ADDR,
				locationBean.addr);
		SharePreUtil.putStrToSp(context, SP_LOACTION_INFO.LOCATION_LAT,
				String.valueOf(locationBean.lat));
		SharePreUtil.putStrToSp(context, SP_LOACTION_INFO.LOCATION_LON,
				String.valueOf(locationBean.lon));
		SharePreUtil.putStrToSp(context, SP_LOACTION_INFO.LOCATION_CITY,
				locationBean.city);
	}
}
