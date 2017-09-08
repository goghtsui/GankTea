/**
 * @Title VolleyGet.java
 * @Package com.hiveview.domyphonemate.service.net.volley
 * @author haozening
 * @date 2014年5月27日 下午2:10:24
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.service.net.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.hiveview.cloudscreen.vipvideo.service.net.ImpRequestPost;
import com.hiveview.cloudscreen.vipvideo.util.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Volley的获取byte[]的请求，需要设置请求地址、数据请求监听(OnResponseListener)、数据错误监听(OnErrorListener
 * )和上线文对象<br>
 * 通过OnResponseListener 获取请求结果<br>
 * 通过OnErrorListener 获取异常信息
 * 
 * @ClassName VolleyGet
 * @Description
 * @author haozening
 * @date 2014年5月27日 下午2:10:24
 * 
 */
public class VolleyRequestPostByteArray implements ImpRequestPost<byte[]> {

	private static final String TAG = VolleyRequestGetByteArray.class.getSimpleName();
	private String url;
	private OnResponseListener<byte[]> onResponseListener;
	private OnErrorListener onErrorListener;
	private Map<String, String> params = new HashMap<String, String>();
	private Context context;

	private ByteArrayRequest request;
	private VolleyRequestQueue queue;

	/**
	 * @param @param url
	 * @param @param responseListener
	 * @param @param errorListener
	 */
	public VolleyRequestPostByteArray(String url, OnResponseListener<byte[]> responseListener, OnErrorListener errorListener, Context context) {
		this.url = url;
		Logger.d(TAG, "url====" + url);
		this.onResponseListener = responseListener;
		this.onErrorListener = errorListener;
		this.context = context;
		queue = VolleyRequestQueue.getInstance(context);
	}

	@Override
	public void submit() {
		request = createRequest();
		queue.addRequest(request);
	}

	@Override
	public void cancel() {
		request.cancel();
	}

	/**
	 * 创建获取byte[]的请求
	 * 
	 * @Title createRequest
	 * @author haozening
	 * @Description
	 * @return
	 */
	private ByteArrayRequest createRequest() {
		// if (url.contains("https")) {
		// HttpsTrustManager.allowAllSSL();
		// }
		ByteArrayRequest request = new ByteArrayRequest(Method.POST, url, new Listener<byte[]>() {

			@Override
			public void onResponse(byte[] arg0) {
				onResponseListener.onResponse(arg0);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				onErrorListener.onError(arg0);
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}
		};
		return request;
	}

	@Override
	public ImpRequestPost<byte[]> setParam(String key, String value) {
		if (params == null) {
			params = new HashMap<String, String>();
		}
		params.put(key, value);
		return this;
	}

	@Override
	public ImpRequestPost<byte[]> setParams(Map<String, String> params) {
		this.params = params;
		return this;
	}

}
