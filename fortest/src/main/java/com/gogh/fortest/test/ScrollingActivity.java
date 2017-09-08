package com.gogh.fortest.test;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.emokit.sdk.util.SDKAppInit;
import com.gogh.fortest.R;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ScrollingActivity extends AppCompatActivity {

    private static final String TAG = "ScrollingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SDKAppInit.createInstance(this);
        SDKAppInit.setDebugMode(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //            retrofit1Api();
            //            retrofit2Api();
            //            getIpInfo();
            //            getBlockList();
            getBlockList2();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void testRxJava() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.d(TAG, "Observable call.");
                subscriber.onNext("Your Toast!");
                //                subscriber.onCompleted();
                subscriber.onNext("Your Toast again!");
            }
        });

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "Subscriber onCompleted.");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Subscriber onError.");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Subscriber onNext.");
            }
        };

        observable.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void retrofit1Api() {
        String API = "https://api.github.com";
        String API2 = "https://api.douban.com/v2";
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API2)
                .build();

        RequestApi api = restAdapter.create(RequestApi.class);

        api.getBook("1003078", new Callback<BookEntity>() {
            @Override
            public void success(BookEntity gitmodel, Response response) {
                Log.d(TAG, "Github response :" + gitmodel.toString());
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private void retrofit2Api() {

        //创建Retrofit实例
       /* Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ip.taobao.com")
                .build();

        RequestApi apiStores = retrofit.create(RequestApi.class);
        Call<ResponseBody> call = apiStores.getIpIntro("220.160.193.209");*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestApi apiStores = retrofit.create(RequestApi.class);
        Call<ResponseBody> call = apiStores.getSearchBooks("小王子", "", 0, 3);

        //在主线程里，异步调用。
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "response=" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("onFailure", "onFailure=" + t.getMessage());
            }
        });
    }

    private void getIpInfo() {
        //创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                //当我们的@GET()里有url时，这个baseUrl无效。但是这个必须要填，不然会报错，神奇。
                .baseUrl("http://www.taobao.com.cn/")
                .build();

        BookApi apiStores = retrofit.create(BookApi.class);
        Call<ResponseBody> call = apiStores.getIpInfo("220.160.193.209");
        //在主线程里，异步调用。
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "response=" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // http://api.bc.global.domybox.com/api/open/vip/home/launcherHome/
    // 14:3D:F2:90:73:36-DUC3410E160700053-DB3046.json?mac=14:3D:F2:90:73:36
    // &sn=DUC3410E160700053&model=DB3046&romversion=DU.74.31.01&apkversion=2.0.02&duiversion=0.0
    private void getBlockList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.bc.global.domybox.com/")
                .build();
        RequestApi api = retrofit.create(RequestApi.class);
        Call<ResponseBody> call = api.getHomeList("14:3D:F2:90:73:36", "DUC3410E160700053", "DB3046",
                "14:3D:F2:90:73:36", "DUC3410E160700053", "DB3046", "DU.74.31.01", "2.0.02", "3.1.2");
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "getBlockList response = " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "getBlockList onFailure = " + t.getMessage());
            }
        });
    }

    private void getBlockList2() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://api.bc.global.domybox.com/")
                .build();

        RequestApi api = retrofit.create(RequestApi.class);
        Observable<ResponseEntity> observable = api.getBlockList("14:3D:F2:90:73:36", "DUC3410E160700053", "DB3046",
                "14:3D:F2:90:73:36", "DUC3410E160700053", "DB3046", "DU.74.31.01", "2.0.02", "3.1.2");
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "getBlockList2 onCompleted. ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getBlockList2 onError. " + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseEntity responseBody) {
                        try {
                            Log.d(TAG, "getBlockList2 response = " + responseBody.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 根据提供的action判断是否存在包含这个action的应用
     * @author gogh
     * @date 2016/12/14
     * ChangeLog:
     * <li> gogh on 2016/12/14 </li>
     */
    public static boolean isInstallByAction(Context context, String action) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(action);
        List<ResolveInfo> resolveInfo = packageManager
                .queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS);
        if (resolveInfo.size() == 0) {
            Log.d(TAG, "isInstallByAction false. ");
            // 说明没有任何应用使用该action
            return false;
        }

        // 说明有某个应用使用了该action
        Log.d(TAG, "isInstallByAction true. ");
        return true;
    }

    public static boolean isInstalled(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if(list.size() > 0){
            // 说明某个应用的activty使用了该action
            return true;
        }
        return false;
    }

    public static boolean isInstall(Context context, String packageName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName.trim()
                    , PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
            if (packageInfo != null) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "isInstallByAction onStart. " + isInstalled(this, "com.hiveview.cloudscreen.action.START_AD"));

        isInstallByAction(this, "com.hiveview.cloudscreen.action.START_AD");
    }
}
