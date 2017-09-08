/**
 * @Title WifiDrawable.java
 * @Package com.hiveview.cloudscreen.video.view.drawable
 * @author haozening
 * @date 2014年9月20日 下午12:40:28
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.view.drawable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;

import com.hiveview.cloudscreen.vipvideo.R;

/**
 * 0-4是网络信号、5是有wifi无外网
 * @ClassName WifiDrawable
 * @Description 
 * @author haozening
 * @date 2014年9月20日 下午12:40:28
 * 
 */
public class WifiDrawable extends LevelListDrawable {

	public static final int STATUS_INTERNET_DISCONNECT = 5;
	public WifiDrawable(Context context) {
		super();
		addLevel(0, 0, Drawable.createFromStream(context.getResources().openRawResource(R.drawable.wifi_0), "wifi_0"));
		addLevel(0, 1, Drawable.createFromStream(context.getResources().openRawResource(R.drawable.wifi_1), "wifi_1"));
		addLevel(1, 2, Drawable.createFromStream(context.getResources().openRawResource(R.drawable.wifi_2), "wifi_2"));
		addLevel(2, 3, Drawable.createFromStream(context.getResources().openRawResource(R.drawable.wifi_3), "wifi_3"));
		addLevel(3, 4, Drawable.createFromStream(context.getResources().openRawResource(R.drawable.wifi_4), "wifi_4"));
		addLevel(4, 5, Drawable.createFromStream(context.getResources().openRawResource(R.drawable.wifi_5), "wifi_5"));
	}
	
	
	
}
