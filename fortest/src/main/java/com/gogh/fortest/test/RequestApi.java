package com.gogh.fortest.test;

import okhttp3.ResponseBody;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit2.Call;
import retrofit2.http.Query;
import rx.Observable;

/**
 * //这里url为请求地址

 //多参数，用map，注解用@QueryMap
 @GET("url")
 Call<ResponseBody> getInfo(@QueryMap Map<String,String> params);

 //post的请求参数是放在请求体中的，就是body内(详见http请求),这是以json格式传递参数的
 @POST("url")
 @FormUrlEncoded
 Call<ResponseBody> doLogin(@Body User user);

 //post表单传递，map，就是我们一般用到的
 @POST("url")
 @FormUrlEncoded
 Call<ResponseBody> doLogin(@FieldMap Map<String,String> params);

 //也是post表单传递，是以单个进行传递
 @FormUrlEncoded
 @POST("url")
 Call<ResponseBody> doLogin(@Field("username") String name, @Field("password") String password);

 //请求头更改
 @FormUrlEncoded
 @Headers({"Accept: application/vnd.github.v3.full+json",
 "User-Agent: Retrofit-Sample-App"})
 Call<ResponseBody> getUserInfo();

 //动态改变请求头
 @GET("/user")
 Call<User> getUser(@Header("Authorization")   String authorization);
 */
public interface RequestApi {

    @GET("/users/{user}")
    void getFeed(@Path("user") String user, Callback<GitResponse> response);

    @retrofit2.http.GET("/book/{id}")
    void getBook(@Path("id") String user, Callback<BookEntity> response);

    @retrofit2.http.GET("/service/getIpInfo.php")
    Call<ResponseBody> getIpIntro(@Query("ip") String ip);

    @retrofit2.http.GET("/book/search")
    Call<ResponseBody> getSearchBooks(@Query("q") String name,
                                            @Query("tag") String tag, @Query("start") int start,
                                            @Query("count") int count);

    // http://api.bc.global.domybox.com/api/open/vip/home/launcherHome/
    // 14:3D:F2:90:73:36-DUC3410E160700053-DB3046.json?mac=14:3D:F2:90:73:36
    // &sn=DUC3410E160700053&model=DB3046&romversion=DU.74.31.01&apkversion=2.0.02&duiversion=0.0
    @retrofit2.http.GET("api/open/vip/home/launcherHome/{param1}-{param2}-{param3}.json")
    Call<ResponseBody> getHomeList(@retrofit2.http.Path("param1") String param1, @retrofit2.http.Path("param2") String param2,
                                   @retrofit2.http.Path("param3") String param3, @Query("mac") String mac, @Query("sn") String sn,
                                   @Query("model") String model, @Query("romversion") String romversion,
                                   @Query("apkversion") String apkversion, @Query("duiversion") String duiversion);

    @retrofit2.http.GET("api/open/vip/home/launcherHome/{param1}-{param2}-{param3}.json")
    Observable<ResponseEntity> getBlockList(@retrofit2.http.Path("param1") String param1, @retrofit2.http.Path("param2") String param2,
                                          @retrofit2.http.Path("param3") String param3, @Query("mac") String mac, @Query("sn") String sn,
                                          @Query("model") String model, @Query("romversion") String romversion,
                                          @Query("apkversion") String apkversion, @Query("duiversion") String duiversion);

}