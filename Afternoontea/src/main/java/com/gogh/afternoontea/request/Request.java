package com.gogh.afternoontea.request;

import android.content.Context;

import com.gogh.afternoontea.entity.gank.GankEntity;
import com.gogh.afternoontea.listener.OnResponListener;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/27/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/27/2016 do fisrt create. </li>
 */

public interface Request {

    /**
     * 获取当前位置
     * @param responListener
     */
    void getLocation(Context context, OnResponListener<String> responListener);

    /**
     * 根据城市获取对应的天气信息
     *
     * @param city           城市名称
     * @return 天气
     */
    void getWeatherByCity(String city, OnResponListener<String> responListener);

    /**
     * 获取图片
     *
     * @param responListener
     * @return
     */
    void getMeiziPic(OnResponListener<String> responListener);

    /**
     *  根据分类信息，获取在线数据
     * @param category 分类名称
     * @param num 每页的数量
     * @param page 当前页的索引
     * @param responListener
     */
    void getDataByCategory(String category, int num, int page, OnResponListener<GankEntity> responListener);

    /**
     *  获取网页的html源码
     */
    void getHtmlByUrl(String url, OnResponListener<String> responListener);

}
