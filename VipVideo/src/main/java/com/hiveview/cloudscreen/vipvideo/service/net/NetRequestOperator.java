package com.hiveview.cloudscreen.vipvideo.service.net;

import android.content.Context;

import com.hiveview.cloudscreen.vipvideo.common.CloudScreenConstants;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.service.net.volley.VolleyRequestGetByteArray;
import com.hiveview.cloudscreen.vipvideo.service.net.volley.VolleyRequestPostByteArray;
import com.hiveview.cloudscreen.vipvideo.service.request.BaseGetRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.BasePostRequest;
import com.hiveview.cloudscreen.vipvideo.util.Logger;

import java.io.UnsupportedEncodingException;

/**
 * 这里的网络请求采用了命令模式的设计思想，本类是执行器
 * 所有的命令全部继承自{@link ImpNetRequest}类
 * 并通过实现submit()方法来执行
 * 执行的具体命令就是volley请求
 * By Spr_ypt 2015/09/08 14:13
 */
public class NetRequestOperator {
    private static final String TAG = NetRequestOperator.class.getSimpleName();

    public static void operateGetRequest(BaseGetRequest request, ImpNetRequest.OnResponseListener<byte[]> responseListener, ImpNetRequest.OnErrorListener errorListener,
                                         Context context) {
        String url = "";
        try {
            url = request.getRequestUrl();
            url = ResourceProvider.getInstance().addUrlExtra(context, url);
        } catch (UnsupportedEncodingException e) {
            errorListener.onError(e);
            e.printStackTrace();
        }
        if (!"".equals(url)) {
            Logger.d(TAG, "url==" + url);
            switch (CloudScreenConstants.REQUEST_TOOL_TYPE) {
                case VOLLEY:
                    VolleyRequestGetByteArray volleyRequest = new VolleyRequestGetByteArray(url, responseListener, errorListener, context);
                    volleyRequest.submit();
                    break;

                default:
                    break;
            }
        }
    }

    public static void operatePostRequest(BasePostRequest request, ImpNetRequest.OnResponseListener<byte[]> responseListener, ImpNetRequest.OnErrorListener errorListener,
                                          Context context) {
        String url = "";
        try {
            url = request.getRequestUrl();
            url = ResourceProvider.getInstance().addUrlExtra(context, url);
        } catch (UnsupportedEncodingException e) {
            errorListener.onError(e);
            e.printStackTrace();
        }
        if (!"".equals(url)) {
            Logger.d(TAG, "url==" + url);
            switch (CloudScreenConstants.REQUEST_TOOL_TYPE) {
                case VOLLEY:
                    VolleyRequestPostByteArray volleyRequest = new VolleyRequestPostByteArray(url, responseListener, errorListener, context);
                    volleyRequest.setParams(request.getParams()).submit();
                    break;
                default:
                    break;
            }
        }

    }

}
