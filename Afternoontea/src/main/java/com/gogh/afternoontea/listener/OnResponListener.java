package com.gogh.afternoontea.listener;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 网络请求回调</p>
 * <p> Created by <b>高晓峰</b> on 12/22/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/22/2016 do fisrt create. </li>
 */
public interface OnResponListener<T> {

    /**
     * 接口请求正常结束
     */
    void onComplete();

    /**
     *  接口响应失败
     * @param e 异常信息
     */
    void onError(Throwable e);

    /**
     * 接口请求成功，并且获取到数据
     * @param response 接口返回的数据
     */
    void onResponse(T response);

}
