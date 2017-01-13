package com.gogh.afternoontea.request;

import android.content.Context;

import com.gogh.afternoontea.entity.gank.GankEntity;
import com.gogh.afternoontea.listener.OnResponListener;
import com.gogh.afternoontea.request.imp.RequestApiImp;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 接口请求代理类</p>
 * <p> Created by <b>高晓峰</b> on 12/28/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/28/2016 do fisrt create. </li>
 */
public class RequestProxy implements Request {

    private static RequestProxy INSTANCE;

    private RequestProxy() {
    }

    public static RequestProxy newInstance() {
        if (null == INSTANCE) {
            INSTANCE = SingleHoder.PROXY;
        }
        return INSTANCE;
    }

    /**
     * 获取当前位置
     *
     * @param responListener
     */
    @Override
    public void getLocation(Context context, OnResponListener<String> responListener) {
        RequestApiImp.newInstance().getLocation(context, responListener);
    }

    /**
     * 根据城市获取对应的天气信息
     *
     * @param city 城市名称
     * @return 天气
     */
    @Override
    public void getWeatherByCity(String city, OnResponListener<String> responListener) {
        RequestApiImp.newInstance().getWeatherByCity(city, responListener);
    }

    /**
     * 获取图片
     *
     * @param responListener
     * @return
     */
    @Override
    public void getMeiziPic(OnResponListener<String> responListener) {
        RequestApiImp.newInstance().getMeiziPic(responListener);
    }

    @Override
    public void getDataByCategory(String category, int num, int page, OnResponListener<GankEntity> responListener) {
        RequestApiImp.newInstance().getDataByCategory(category, num, page, responListener);
    }

    /**
     * 获取网页的html源码
     *
     * @param responListener
     */
    @Override
    public void getHtmlByUrl(String url, OnResponListener<String> responListener) {
        RequestApiImp.newInstance().getHtmlByUrl(url, responListener);
    }

    private static final class SingleHoder {
        private static final RequestProxy PROXY = new RequestProxy();
    }
}
