package com.hiveview.cloudscreen.vipvideo.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;

import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.view.CloundMenuWindow;

import java.lang.ref.WeakReference;


public class BaseActivity extends Activity {

    private static final String TAG = "BaseActivity";
    protected CloundMenuWindow mWindow = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AppConstants.REMOTE_ACTION);
        registerReceiver(receiver, intentFilter);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private BroadcastReceiver receiver = new FinishBroadcastReceiver(this);

    private static class FinishBroadcastReceiver extends BroadcastReceiver {
        private WeakReference<BaseActivity> ref;

        public FinishBroadcastReceiver(BaseActivity activity) {
            ref = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(AppConstants.REMOTE_ACTION)) {
                BaseActivity activity = ref.get();
                if (null != activity) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 提交到本地的异步处理
     *
     * @param tag 处理某业务的标志位
     * @Title: BaseActivity
     * @author:陈丽晓
     * @Description: TODO
     */
    protected void submitAsyncTask(int tag) {
        new LocalAsyncTask().execute(tag);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
                if (null == mWindow) {
                    mWindow = new CloundMenuWindow(this, null);
                }
                mWindow.show();
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 根据tag值异步处理的方法，一般用来加载本地数据库中的数据
     *
     * @param tag 处理某业务的标志位
     * @Title: BaseActivity
     * @author:陈丽晓
     * @Description: TODO
     */
    protected void asyncLocalProcess(int tag) {

    }

    /**
     * 根据tag值执行在UI线程中完成的数据显示，一般用来处理从本地读取数据集合后对得到的数据集合进行处理
     *
     * @param tag
     * @Title: BaseActivity
     * @author:陈丽晓
     * @Description: TODO
     */
    protected void postExecute(int tag) {

    }

    /**
     * 用于本地异步处理的AsyncTask
     *
     * @ClassName: LocalAsyncTask
     * @Description: TODO
     * @author: 陈丽晓
     * @date 2014-8-14 下午10:33:30
     */
    class LocalAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {// 异步执行
            asyncLocalProcess(params[0]);
            return params[0];
        }

        @Override
        protected void onPostExecute(Integer result) {// UI线程中执行
            postExecute(result);
        }

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        unregisterReceiver(receiver);
        if (getClass().equals(VipVideoDetailActivity.class) || getClass().equals(MainActivity.class)) {
            if (null != CloudScreenApplication.getInstance().blurBitmap) {
                if (!CloudScreenApplication.getInstance().blurBitmap.isRecycled()) {
                    CloudScreenApplication.getInstance().blurBitmap.recycle();
                }
                CloudScreenApplication.getInstance().blurBitmap = null;
            }
        }
        super.onDestroy();
    }
}
