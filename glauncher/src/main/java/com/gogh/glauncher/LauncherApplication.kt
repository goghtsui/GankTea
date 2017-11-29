package com.gogh.glauncher

import android.app.Application
import com.gogh.glauncher.request.Path
import com.gogh.okrxretrofit.HttpClient
import com.gogh.okrxretrofit.component.DaggerHttpComponent
import com.gogh.okrxretrofit.conf.TimeOut
import com.gogh.okrxretrofit.http.HttpModule
import com.gogh.okrxretrofit.util.Logger
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 11/27/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 11/27/2017 do fisrt create. </li>
 */
class LauncherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    fun init(){
        /* 初始化*/
        val httpModule = HttpModule.Builder()
                .baseUrl(Path.DOMAIN)
                .factory(GsonConverterFactory.create())
                .timeOut(TimeOut())
                .build()
        DaggerHttpComponent.builder().httpModule(httpModule).build().inject(HttpClient.newInstance())
        Logger.setDebugMode(true)
        Logger.setHttpDebugModel(true)
    }
}