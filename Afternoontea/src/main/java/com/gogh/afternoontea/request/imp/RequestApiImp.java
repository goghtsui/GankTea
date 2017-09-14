package com.gogh.afternoontea.request.imp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gogh.afternoontea.constant.Constant;
import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.entity.gank.GankEntity;
import com.gogh.afternoontea.entity.gank.SearchEntity;
import com.gogh.afternoontea.entity.weather.WeatherEntity;
import com.gogh.afternoontea.http.RequestTask;
import com.gogh.afternoontea.listener.OnResponListener;
import com.gogh.afternoontea.location.LocationClient;
import com.gogh.afternoontea.location.listener.OnLocationListener;
import com.gogh.afternoontea.request.Property;
import com.gogh.afternoontea.request.Request;
import com.gogh.afternoontea.request.RequestApi;
import com.gogh.afternoontea.utils.Logger;
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

    private RequestApiImp() {
    }

    public static RequestApiImp newInstance() {
        return SingleHoder.REQUEST;
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
                    responListener.onError(new Throwable(errorCode + ", " + errorInfo));
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
        RequestTask task = RequestTask.getInstance();
        Retrofit retrofit = task.retrofit(getProperties(), Urls.Weather.BASE_URL, GsonConverterFactory.create());
        RequestApi api = retrofit.create(RequestApi.class);

        Observable<WeatherEntity> observable = api.getWeatherByCity(Urls.Weather.KEY, city, Urls.Weather.LANGUAGE, Urls.Weather.UNIT);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherEntity>() {
                    @Override
                    public void onNext(@NonNull WeatherEntity weatherEntity) {
                        Logger.d(TAG, "getWeatherByCity onNext weatherEntity =" + weatherEntity.toString());
                        if (null != weatherEntity && weatherEntity.getResults() != null
                                && weatherEntity.getResults().get(0) != null && weatherEntity.getResults().get(0).getNow() != null) {
                            WeatherEntity.ResultsBean.NowBean nowBean = weatherEntity.getResults().get(0).getNow();
                            if (null != nowBean) {
                                if(responListener != null){
                                    responListener.onResponse(nowBean.getText());
                                }
                            } else {
                                if(responListener != null){
                                    responListener.onResponse(null);
                                }
                            }
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
        RequestTask task = RequestTask.getInstance();
        Retrofit retrofit = task.retrofit(getProperties(), Urls.Meizi.BASE_URL, ScalarsConverterFactory.create());
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
        RequestApi requestApi = getRetrofit().create(RequestApi.class);
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
     * 搜索接口
     *
     * @param year
     * @param month
     * @param day
     * @param onResponListener
     * @author 高晓峰
     * @date 9/12/2017
     * @ChangeLog: <li> 高晓峰  on 9/12/2017 </li>
     */
    @Override
    public void getSearchList(String year, String month, String day, OnResponListener<SearchEntity> onResponListener) {
        RequestApi requestApi = getRetrofit().create(RequestApi.class);
        Observable<SearchEntity> observable = requestApi.getSearchList(year, month, day);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchEntity>() {
                    @Override
                    public void onCompleted() {
                        Logger.d(TAG, "getSearchList onCompleted.");
                        if (null != onResponListener) {
                            onResponListener.onComplete();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Logger.d(TAG, "getSearchList onError : " + e.getMessage());
                        if (null != onResponListener) {
                            onResponListener.onError(e);
                        }
                    }

                    @Override
                    public void onNext(SearchEntity response) {
                        Logger.d(TAG, "getSearchList onNext : " + response);
                        if (null != onResponListener) {
                            onResponListener.onResponse(response);
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
        RequestTask task = RequestTask.getInstance();
        Retrofit retrofit = task.retrofit(getProperties(), params[0], ScalarsConverterFactory.create());
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

    private List<Property> getProperties() {
        List<Property> propertyList = new ArrayList<>();
        propertyList.add(new Property(Constant.ACCEPT, Constant.ACCEPT_HTML_VALUE));
        propertyList.add(new Property(Constant.USER_AGENT, Constant.USER_AGENT_VALUE));
        return propertyList;
    }

    private Retrofit getRetrofit() {
        RequestTask task = RequestTask.getInstance();
        Retrofit retrofit = task.retrofit(getProperties(), Urls.GANK_URL.BASE_URL, GsonConverterFactory.create());
        return retrofit;
    }

}
