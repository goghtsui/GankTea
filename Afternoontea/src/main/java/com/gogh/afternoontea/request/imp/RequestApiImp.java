package com.gogh.afternoontea.request.imp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gogh.afternoontea.constant.Constant;
import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.convert.CustomGsonConverterFactory;
import com.gogh.afternoontea.entity.gank.GankEntity;
import com.gogh.afternoontea.entity.weather.NowBean;
import com.gogh.afternoontea.entity.weather.WeatherEntity;
import com.gogh.afternoontea.http.RequestTask;
import com.gogh.afternoontea.listener.OnResponListener;
import com.gogh.afternoontea.location.LocationClient;
import com.gogh.afternoontea.location.listener.OnLocationListener;
import com.gogh.afternoontea.log.Logger;
import com.gogh.afternoontea.request.Property;
import com.gogh.afternoontea.request.Request;
import com.gogh.afternoontea.request.RequestApi;
import com.gogh.afternoontea.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/27/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/27/2016 do fisrt create. </li>
 */
public class RequestApiImp implements Request {

    private static final String TAG = "RequestApiImp";

    private static RequestApiImp INSTANCE;

    private RequestApiImp() {
    }

    public static RequestApiImp newInstance() {
        if (null == INSTANCE) {
            INSTANCE = SingleHoder.REQUEST;
        }
        return INSTANCE;
    }

    /**
     * 获取当前位置
     *
     * @param responListener
     */
    @Override
    public void getLocation(@NonNull Context context, @Nullable OnResponListener<String> responListener) {
        LocationClient.newInstance().setOnLocationListener(new OnLocationListener() {
            @Override
            public void onLocationed(String cityIp) {
                Logger.d(TAG, "getLocation onLocationed cityIp =" +cityIp);
                if (null != responListener) {
                    responListener.onResponse(cityIp);
                }
            }

            @Override
            public void onError(int errorCode, String errorInfo) {
                Logger.d(TAG, "getLocation onError errorcode : " + errorCode + ", errorInfo : " +errorInfo);
                if (null != responListener) {
                    responListener.onError(new Throwable(errorInfo + ", " + errorInfo));
                }
            }
        });
        LocationClient.newInstance().build(context.getApplicationContext()).startLocation();
    }

    /**
     * 根据城市获取对应的天气信息
     *
     * @param city 城市名称
     * @return 天气
     */
    @Override
    public void getWeatherByCity(String city, @Nullable OnResponListener<String> responListener) {
        List<Property> propertyList = new ArrayList<>();
        propertyList.add(new Property(Constant.ACCEPT, Constant.ACCEPT_GSON_VALUE));
        propertyList.add(new Property(Constant.USER_AGENT, Constant.USER_AGENT_VALUE));
        propertyList.add(new Property(Constant.APIKEY, Constant.APIKE_VALUE));

        RequestTask task = RequestTask.getInstance();
        Retrofit retrofit = task.retrofit(propertyList, Urls.Weather.BASE_URL, CustomGsonConverterFactory.create());
        RequestApi api = retrofit.create(RequestApi.class);

        Observable<WeatherEntity> observable = api.getWeatherByCity(city);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherEntity>() {
                    @Override
                    public void onNext(@NonNull WeatherEntity weatherEntity) {
                        Logger.d(TAG, "getWeatherByCity onNext weatherEntity =" + weatherEntity.toString());
                        String weather = null;
                        if (null != weatherEntity) {
                            NowBean nowBean = weatherEntity.getNow();
                            if (null != nowBean) {
                                NowBean.CondBean cond = nowBean.getCond();
                                if (null != cond) {
                                    Logger.d(TAG, "getWeatherByCity onNext normal weather : " + cond.getTxt());
                                    weather = cond.getTxt();
                                }
                            }
                        }
                        if(responListener != null){
                            responListener.onResponse(weather);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        Logger.d(TAG, "getWeatherByCity onCompleted.");
                        if(responListener != null){
                            responListener.onComplete();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Logger.d(TAG, "getWeatherByCity onError : " + e.getMessage());
                        if(responListener != null){
                            responListener.onError(e);
                        }
                    }
                });
    }

    /**
     * 获取图片
     *
     * @param responListener
     * @return
     */
    @Override
    public void getMeiziPic(@Nullable OnResponListener<String> responListener) {
        List<Property> propertyList = new ArrayList<>();
        propertyList.add(new Property(Constant.ACCEPT, Constant.ACCEPT_HTML_VALUE));
        propertyList.add(new Property(Constant.USER_AGENT, Constant.USER_AGENT_VALUE));

        RequestTask task = RequestTask.getInstance();
        Retrofit retrofit = task.retrofit(propertyList, Urls.Meizi.BASE_URL, ScalarsConverterFactory.create());
        RequestApi api = retrofit.create(RequestApi.class);

        Observable<String> observable = api.getMeiziPic();
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Logger.d(TAG, "getMeiziPic onCompleted.");
                        if (null != responListener) {
                            responListener.onComplete();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Logger.d(TAG, "getMeiziPic onError : " + e.getMessage());
                        if (null != responListener) {
                            responListener.onError(e);
                        }
                    }

                    @Override
                    public void onNext(String response) {
                        Logger.d(TAG, "getMeiziPic onNext : " + response);
                        if (null != responListener) {
                            responListener.onResponse(response);
                        }
                    }
                });
    }

    /**
     * 根据分类信息，获取在线数据
     *
     * @param category       分类名称
     * @param num            每页的数量
     * @param page           当前页的索引
     * @param responListener
     */
    @Override
    public void getDataByCategory(String category, int num, int page, @Nullable OnResponListener<GankEntity> responListener) {
        List<Property> propertyList = new ArrayList<>();
        propertyList.add(new Property(Constant.ACCEPT, Constant.ACCEPT_GSON_VALUE));
        propertyList.add(new Property(Constant.USER_AGENT, Constant.USER_AGENT_VALUE));

        RequestTask task = RequestTask.getInstance();
        Retrofit retrofit = task.retrofit(propertyList, Urls.GANK_URL.BASE_URL, GsonConverterFactory.create());
        RequestApi requestApi = retrofit.create(RequestApi.class);
        Observable<GankEntity> observable = requestApi.getDataByCategory(category, num, page);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankEntity>() {
                    @Override
                    public void onCompleted() {
                        Logger.d(TAG, "getDataByCategory onCompleted.");
                        if (null != responListener) {
                            responListener.onComplete();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Logger.d(TAG, "getDataByCategory onError : " + e.getMessage());
                        if (null != responListener) {
                            responListener.onError(e);
                        }
                    }

                    @Override
                    public void onNext(@NonNull GankEntity response) {
                        Logger.d(TAG, "getDataByCategory onNext : " + response.toString());
                        if (null != responListener) {
                            responListener.onResponse(response);
                        }
                    }
                });
    }

    /**
     * 获取网页的html源码
     *
     * @param responListener
     */
    @Override
    public void getHtmlByUrl(@NonNull String url, @Nullable OnResponListener<String> responListener) {
        String[] params = StringUtil.formatUrl(url);
        Logger.d(TAG,  "getHtmlByUrl: " + params[0] + ", " + params[1]);
        List<Property> propertyList = new ArrayList<>();
        propertyList.add(new Property(Constant.ACCEPT, Constant.ACCEPT_HTML_VALUE));
        propertyList.add(new Property(Constant.USER_AGENT, Constant.USER_AGENT_VALUE));

        RequestTask task = RequestTask.getInstance();
        Retrofit retrofit = task.retrofit(propertyList, params[0], ScalarsConverterFactory.create());
        RequestApi api = retrofit.create(RequestApi.class);

        Observable<String> observable = api.getHtmlByUrl(params[1]);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Logger.d(TAG, "getHtmlByUrl onCompleted.");
                        if (null != responListener) {
                            responListener.onComplete();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Logger.d(TAG, "getHtmlByUrl onError : " + e.getMessage());
                        if (null != responListener) {
                            responListener.onError(e);
                        }
                    }

                    @Override
                    public void onNext(String response) {
                        Logger.d(TAG, "getHtmlByUrl onNext : " + response);
                        if (null != responListener) {
                            responListener.onResponse(response);
                        }
                    }
                });
    }

    private static final class SingleHoder {
        private static final RequestApiImp REQUEST = new RequestApiImp();
    }

}
