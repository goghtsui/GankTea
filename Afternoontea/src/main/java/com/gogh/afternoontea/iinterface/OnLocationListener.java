package com.gogh.afternoontea.iinterface;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 定位服务的数据回调</p>
 * <p> Created by <b>高晓峰</b> on 12/23/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/23/2016 do fisrt create. </li>
 */
public interface OnLocationListener {
    /**
     * 定位成功
     * @param city 城市名称（或IP、经纬度）
     */
    void onLocationed(String city);

    /**
     * 定位失败
     * @param errorCode 错误码
     * @param errorInfo 错误信息
     */
    void onError(int errorCode, String errorInfo);
}
