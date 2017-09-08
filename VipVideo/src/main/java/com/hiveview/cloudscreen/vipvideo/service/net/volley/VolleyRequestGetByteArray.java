/**
 * @Title VolleyRequestGetByteArray.java
 * @Package com.hiveview.domyphonemate.service.net.volley
 * @author haozening
 * @date 2014年5月28日 下午4:26:45
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.service.net.volley;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.hiveview.cloudscreen.vipvideo.service.net.ImpRequestGet;
import com.hiveview.cloudscreen.vipvideo.util.Logger;


/**
 * @author haozening
 * @ClassName VolleyRequestGetByteArray
 * @Description
 * @date 2014年5月28日 下午4:26:45
 */
public class VolleyRequestGetByteArray implements ImpRequestGet<byte[]> {

    private static final String TAG = VolleyRequestGetByteArray.class.getSimpleName();
    private String url;
    private OnResponseListener<byte[]> onResponseListener;
    private OnErrorListener onErrorListener;
    private Context context;

    private ByteArrayRequest request;
    private VolleyRequestQueue queue;

    /**
     * @param @param url
     * @param @param responseListener
     * @param @param errorListener
     */
    public VolleyRequestGetByteArray(String url, OnResponseListener<byte[]> responseListener, OnErrorListener errorListener, Context context) {
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
     * @return
     * @Title createRequest
     * @author haozening
     * @Description
     */
    private ByteArrayRequest createRequest() {
        // if (url.contains("https")) {
        // HttpsTrustManager.allowAllSSL();
        // }
        ByteArrayRequest request = new ByteArrayRequest(Method.GET, url, new Listener<byte[]>() {

            @Override
            public void onResponse(byte[] arg0) {
                onResponseListener.onResponse(arg0);
            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                onErrorListener.onError(arg0);
            }
        });
        return request;
    }
}
