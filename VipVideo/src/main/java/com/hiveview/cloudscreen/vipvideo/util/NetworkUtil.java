/**
 * @Title NetworkUtil.java
 * @Package com.hiveview.cloudscreen.video.utils
 * @author haozening
 * @date 2014年9月20日 上午10:06:16
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * 网络连接状态工具类
 * 
 * @ClassName NetworkUtil
 * @Description
 * @author haozening
 * @date 2014年9月20日 上午10:06:16
 * 
 */
public class NetworkUtil {

	public static final int WIFI_MAX_LEVEL = 5;
	public static final int CHECK_NETWORK_STATE_DELAY = 5000;
	private ConnectivityManager connectivityManager;
	private WifiManager wifiManager;
	private NetworkInfo networkInfo;
	private WifiInfo wifiInfo;

	public NetworkUtil(Context context) {
		connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		networkInfo = connectivityManager.getActiveNetworkInfo();
		wifiInfo = wifiManager.getConnectionInfo();
	}

	/**
	 * 判断网络是否连接
	 * 
	 * @Title isConnected
	 * @author haozening
	 * @Description
	 * @return
	 */
	public boolean isConnected() {
		if (null != networkInfo && networkInfo.isAvailable() && networkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否是本地连接
	 * 
	 * @Title isEthernet
	 * @author haozening
	 * @Description
	 * @return
	 */
	@SuppressLint("InlinedApi")
	public boolean isEthernet() {
		if (null == networkInfo) {
			return false;
		}
		return ConnectivityManager.TYPE_ETHERNET == networkInfo.getType();
	}

	/**
	 * 判断是否是WIFI连接
	 * 
	 * @Title isWifi
	 * @author haozening
	 * @Description
	 * @return
	 */
	public boolean isWifi() {
		if (null == networkInfo) {
			return false;
		}
		return ConnectivityManager.TYPE_WIFI == networkInfo.getType();
	}

	/**
	 * 获取WIFI的当前强度
	 * 
	 * @Title getWifiLevel
	 * @author haozening
	 * @Description
	 * @return
	 */
	public int getWifiLevel() {
		if (null == wifiInfo) {
			return 0;
		} else {
			return WifiManager.calculateSignalLevel(wifiInfo.getRssi(), WIFI_MAX_LEVEL);
		}
	}

}
