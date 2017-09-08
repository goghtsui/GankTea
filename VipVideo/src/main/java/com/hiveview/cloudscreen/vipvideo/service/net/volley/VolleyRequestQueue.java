/**
 * @Title VolleyRequestQueue.java
 * @Package com.hiveview.domyphonemate.service.net
 * @author haozening
 * @date 2014年5月27日 下午1:40:40
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.service.net.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 
 * @ClassName: VolleyRequestQueue
 * @Description: 单例化Volley请求队列
 * @author: yupengtong
 * @date 2015年4月9日 下午3:13:58
 * 
 */
public class VolleyRequestQueue {

	private static VolleyRequestQueue volleyRequestQueue;
	private static RequestQueue queue;

	private VolleyRequestQueue(Context context) {
		synchronized (this) {
			queue = Volley.newRequestQueue(context);
		}
	}

	public static synchronized VolleyRequestQueue getInstance(Context context) {
		if (volleyRequestQueue == null) {
			volleyRequestQueue = new VolleyRequestQueue(context);
		}
		return volleyRequestQueue;
	}

	public RequestQueue getRequestQueue() {
		return queue;
	}

	public <T> void addRequest(Request<T> request) {
		queue.add(request);
	}
}
