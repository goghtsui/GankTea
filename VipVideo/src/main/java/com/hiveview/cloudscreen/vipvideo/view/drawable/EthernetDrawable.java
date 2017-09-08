/**
 * @Title EthernetDrawable.java
 * @Package com.hiveview.cloudscreen.video.view.drawable
 * @author haozening
 * @date 2014年9月20日 下午4:34:28
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.view.drawable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;

import com.hiveview.cloudscreen.vipvideo.R;

/**
 * 0无连接、1有连接无外网、2有线连接
 * @ClassName EthernetDrawable
 * @Description 
 * @author haozening
 * @date 2014年9月20日 下午4:34:28
 * 
 */
public class EthernetDrawable extends LevelListDrawable {

	/**
	 * 没插网线
	 */
	public static final int STATUS_DISCONNECT = 0;
	/**
	 * 没连外网
	 */
	public static final int STATUS_INTERNET_DISCONNECT = 1;
	/**
	 * 连接正常
	 */
	public static final int STATUS_CONNECTED = 2;
	
	public EthernetDrawable(Context context) {
		super();
		addLevel(0, 0, Drawable.createFromStream(context.getResources().openRawResource(R.drawable.eth_0), "eth_0"));
		addLevel(0, 1, Drawable.createFromStream(context.getResources().openRawResource(R.drawable.eth_1), "eth_1"));
		addLevel(1, 2, Drawable.createFromStream(context.getResources().openRawResource(R.drawable.eth_2), "eth_2"));
	}

	
}
