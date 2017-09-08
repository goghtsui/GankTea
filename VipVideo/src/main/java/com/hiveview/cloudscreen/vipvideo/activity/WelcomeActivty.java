package com.hiveview.cloudscreen.vipvideo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.manager.KeyCodeManager;

import java.io.File;
import java.lang.ref.WeakReference;


/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/3/21
 * @Description 去取广告图之前现显示默认图，防止黑屏
 * 默认图显示2秒后在显示广告图2秒随后进入首页
 * 特：显示广告图时，”大麦影视尊享包“隐藏不显示
 */
public class WelcomeActivty extends BaseActivity {

    private static final String TAG = WelcomeActivty.class.getSimpleName();
    private LinearLayout bgLayout;
    private LinearLayout bgLayout_path;
    private ViewPropertyAnimator animator;
    private ImageView upView;
    private ImageView downView;
    private View viewShape;
    //3.2新需求所用
    private ImageView logo;
    private WelcomeHandler welcomeHandler = new WelcomeHandler(this);
    private ImageView viewPath;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "WelcomeActivity.onCreate");
        setContentView(R.layout.activity_welcome);
        viewPath = (ImageView) findViewById(R.id.image_upview_path);
        viewShape = findViewById(R.id.view_shape);
        logo = (ImageView) findViewById(R.id.iv_welcome_logo);
        logo.setImageResource(ResourceProvider.getInstance().getWelcomeTxt());
        //获取图片路径，显示启动也图片
        getWelcomeImage();
    }


    //获取图片路径，显示启动也图片
    private void getWelcomeImage() {
        path = DeviceInfoUtil.getInstance().getDeviceInfo(this).filePath;
        Log.d(TAG, "path==" + path);
        if (null != path) {
            File mFile = new File(path);
            //若该文件存在
            if (mFile.exists()) {
//                Bitmap bitmap = BitmapFactory.decodeFile(path);
                DisplayImageUtil.getInstance().setDuration(2000).setEmptyUriImage(R.drawable.bg_welcome)
                        .setErrorImage(R.drawable.bg_welcome).setLoadingImage(R.drawable.bg_welcome).displayImage("file://" + path, viewPath);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
//      animator = bgLayout.animate().translationY(0).setDuration(3000).setInterpolator(new LinearInterpolator()).setListener(new WelcomeAnimListenerAdapter(this));
        if (null != path) {
            welcomeHandler.sendEmptyMessageDelayed(WelcomeHandler.FINISH_WELCOME, WelcomeHandler.FINISH_WELCOME_DELAY);
            welcomeHandler.sendEmptyMessageDelayed(WelcomeHandler.VISIBILITY_WELCOME, WelcomeHandler.VISIBILITY_WELCOME_DELAY);
        } else {
            Log.i(TAG, "path==null");
            welcomeHandler.sendEmptyMessageDelayed(WelcomeHandler.FINISH_WELCOME, WelcomeHandler.FINISH_WELCOME_DELAY);
        }
    }

    private static class WelcomeAnimListenerAdapter extends AnimatorListenerAdapter {
        private WeakReference<WelcomeActivty> reference;

        public WelcomeAnimListenerAdapter(WelcomeActivty activty) {
            reference = new WeakReference<WelcomeActivty>(activty);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            Logger.d(TAG, "onAnimationEnd");
            WelcomeActivty activty = reference.get();
            if (null != activty) {
                activty.startActivity(new Intent().setClass(activty, MainActivity.class));
                activty.finish();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d(TAG, "onStop");
//        if (null != animator) {
//            animator.setListener(null).cancel();
//            animator = null;
//        }
        if (null != welcomeHandler) {
            welcomeHandler.removeMessages(welcomeHandler.FINISH_WELCOME);
            welcomeHandler.removeMessages(welcomeHandler.VISIBILITY_WELCOME);
        }
    }

    @Override
    protected void onDestroy() {
        Logger.d(TAG, "onDestroy");
//        if (null != animator) {
//            animator.setListener(null).cancel();
//            animator = null;
//        }
        if (null != welcomeHandler) {
            welcomeHandler.removeMessages(welcomeHandler.FINISH_WELCOME);
            welcomeHandler.removeMessages(welcomeHandler.VISIBILITY_WELCOME);
        }
        super.onDestroy();
    }

    private class WelcomeHandler extends android.os.Handler {
        private WeakReference<WelcomeActivty> reference;

        private static final int FINISH_WELCOME = 0x0002;

        private static final int FINISH_WELCOME_DELAY = 3000;

        private static final int VISIBILITY_WELCOME = 0x0003;

        private static final int VISIBILITY_WELCOME_DELAY = 1500;

        public WelcomeHandler(WelcomeActivty activty) {
            reference = new WeakReference<WelcomeActivty>(activty);
        }

        @Override
        public void handleMessage(Message msg) {
            WelcomeActivty activty = reference.get();
            switch (msg.what) {
                case FINISH_WELCOME:
                    if (null != activty) {
                        activty.startActivity(new Intent().setClass(activty, MainActivity.class));
                        activty.finish();
                    }
                    break;
                case VISIBILITY_WELCOME:
                    logo.setVisibility(View.GONE);
                    viewShape.setVisibility(View.GONE);
                    break;
            }
            super.handleMessage(msg);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
                return true;
            }
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if (null != animator) {
                    animator.setListener(null).cancel();
                }
            }
            int code = event.getKeyCode();
            if (code == KeyCodeManager.KEYCODE_TV_APP || code == KeyCodeManager.KEYCODE_TV_HDVIDEO || code == KeyCodeManager.KEYCODE_TV_GAME || code == KeyEvent.KEYCODE_SEARCH) {
                finish();
            }
        }
        return super.dispatchKeyEvent(event);
    }

}
