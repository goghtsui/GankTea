package com.hiveview.cloudscreen.vipvideo.service.parser;

import android.os.AsyncTask;

import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.error.HiveViewErrorCode;

/**
 * Created by Spr-ypt on 2015/10/12.
 */
public abstract class BaseParser<R, T> {

    private static final String TAG = BaseParser.class.getSimpleName();
    protected final String ENCODE = "UTF-8";
    private OnParseCompleteListener listener;

    public void parseRequest(final T result, final OnParseCompleteListener<R> listener) {
        this.listener = listener;
        if (null == result) {// 网络返回的数据为空
            this.listener.onFail(new HiveviewException(HiveViewErrorCode.E0000600));
        } else {
            task.execute(result);
        }

    }

    public abstract R parseJSON(T result) throws Exception;

    public interface OnParseCompleteListener<R> {
        void onSuccess(R result);

        void onFail(HiveviewException e);
    }


    /**
     * @Author Spr_ypt
     * @Date 2016/6/23
     * @Description 异步解析任务
     */
    AsyncTask task = new AsyncTask<T, String, R>() {


        @Override
        protected R doInBackground(T... params) {
            try {
                R parseResult = parseJSON(params[0]);
                return parseResult;

            } catch (Exception e) {// 解析JSON数据异常
                Logger.e(TAG, e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(R r) {
            super.onPostExecute(r);
            if (null != listener) {
                if (null != r) {
                    listener.onSuccess(r);
                } else {
                    listener.onFail(new HiveviewException(HiveViewErrorCode.E0000598));
                }
            }
        }
    };
}
