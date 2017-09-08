package com.hiveview.cloudscreen.vipvideo.service;

import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;

/**
 * Created by Administrator on 2015/10/12.
 */
public interface OnRequestResultListener<T> {
    /**
     * @param t
     * @Title: OnRequestResultListener
     * @author:yupengtong
     * @Description: 请求成功
     */
    public void onSucess(T t);

    /**
     * @param e
     * @Title: OnRequestResultListener
     * @author:yupengtong
     * @Description: 请求失败
     */
    public void onFail(Exception e);

    /**
     * @param e
     * @Title: OnRequestResultListener
     * @author:yupengtong
     * @Description: 解析出错
     */
    public void onParseFail(HiveviewException e);
}
