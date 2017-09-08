package com.gogh.fortest.test;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/7/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/7/2016 do fisrt create. </li>
 */
public interface BookApi {

    @GET("http://ip.taobao.com/service/getIpInfo.php")
    Call<ResponseBody> getIpInfo(@Query("ip") String ip);

}
