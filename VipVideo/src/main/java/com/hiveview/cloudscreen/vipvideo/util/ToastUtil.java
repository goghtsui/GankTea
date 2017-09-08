package com.hiveview.cloudscreen.vipvideo.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;

import static android.view.Gravity.FILL;

/**
 * @ClassName: ToastUtil
 * @Description: Toast 工具类，Toast弹窗单例化，避免多次按键造成的Toast反复触发
 * @author: yupengtong
 * @date 2014年11月20日 上午9:50:55
 */
public class ToastUtil {
    private static Toast mToast;

    public static void showToast(Context context, String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(CloudScreenApplication.getInstance().getApplicationContext(), text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void showBecomeVipSuccess(Context context, int toastLength) {
        LayoutInflater inflater = LayoutInflater.from(CloudScreenApplication.getInstance().getApplicationContext());
        View layout = inflater.inflate(R.layout.view_become_vip_success, null);
        layout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);//该属性如果不设置在3.0盒子上会有上下无法铺满屏幕的部分
        Toast toast = new Toast(CloudScreenApplication.getInstance().getApplicationContext());
        toast.setGravity(FILL, 0, 0);
        toast.setDuration(toastLength);
        toast.setView(layout);
        toast.show();
    }
}
