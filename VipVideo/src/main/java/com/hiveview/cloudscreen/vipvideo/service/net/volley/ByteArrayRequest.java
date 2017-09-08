/**
 * @Title ByteArrayRequest.java
 * @Package com.hiveview.domyphonemate.service.net.volley
 * @author haozening
 * @date 2014年5月28日 下午4:20:18
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.service.net.volley;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * 网络请求返回字节数组
 * @ClassName ByteArrayRequest
 * @Description
 * @author haozening
 * @date 2014年5月28日 下午4:20:18
 *
 */
public class ByteArrayRequest extends Request<byte[]> {

    private Listener<byte[]> listener;

    /**
     * @param @param method
     * @param @param url
     * @param @param listener
     */
    public ByteArrayRequest(int method, String url, Listener<byte[]> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(byte[] response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }


}
