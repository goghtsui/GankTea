package com.hiveview.cloudscreen.vipvideo.service;

import android.content.Context;

import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.service.net.ImpNetRequest;
import com.hiveview.cloudscreen.vipvideo.service.net.NetRequestOperator;
import com.hiveview.cloudscreen.vipvideo.service.parser.BaseParser;
import com.hiveview.cloudscreen.vipvideo.service.request.BaseGetRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.BasePostRequest;

/**
 * Created by Spr-ypt on 2015/10/12.
 */
public class BaseService {
    private Context context;

    public BaseService(Context context) {
        super();
        this.context = context;
    }

    protected <R, T> void get(BaseGetRequest request, final BaseParser<R, byte[]> parser, final OnRequestResultListener<R> listener) {
        NetRequestOperator.operateGetRequest(request, new ImpNetRequest.OnResponseListener<byte[]>() {

            @Override
            public void onResponse(byte[] result) {
                parser.parseRequest(result, new BaseParser.OnParseCompleteListener<R>() {
                    @Override
                    public void onSuccess(R result) {
                        listener.onSucess(result);
                    }

                    @Override
                    public void onFail(HiveviewException e) {
                        listener.onFail(e);

                    }
                });
            }
        }, new ImpNetRequest.OnErrorListener() {

            @Override
            public void onError(Exception e) {
                listener.onFail(e);

            }
        }, context);

    }

    protected <R, T> void post(BasePostRequest request, final BaseParser<R, byte[]> parser, final OnRequestResultListener<R> listener) {
        NetRequestOperator.operatePostRequest(request, new ImpNetRequest.OnResponseListener<byte[]>() {

            @Override
            public void onResponse(byte[] result) {
                parser.parseRequest(result, new BaseParser.OnParseCompleteListener<R>() {
                    @Override
                    public void onSuccess(R result) {
                        listener.onSucess(result);
                    }

                    @Override
                    public void onFail(HiveviewException e) {
                        listener.onFail(e);

                    }
                });

            }
        }, new ImpNetRequest.OnErrorListener() {

            @Override
            public void onError(Exception e) {
                listener.onFail(e);
            }
        }, context);
    }
}
