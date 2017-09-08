package com.gogh.afternoontea.permission.imp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.utils.Logger;
import com.gogh.afternoontea.permission.RequestPermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/6/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/6/2017 do fisrt create. </li>
 */
public class RequestPermissionsImp implements RequestPermissions {


    private static final String TAG = "RequestPermissionsImp";

    /**
     * 授权码
     */
    private static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 需要进行检测的权限数组
     */
    @NonNull
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private Activity mActivity;

    private OnStartActivityListener onStartActivityListener;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;

    public RequestPermissionsImp(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void requestPermission() {
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
    }

    @Override
    public void checkPermissions(@NonNull String... permissions) {
        Logger.d(TAG, " checkPermissions.");
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            Logger.d(TAG, " checkPermissions.requestPermissions");
            ActivityCompat.requestPermissions(mActivity,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        } else {
            Logger.d(TAG, " checkPermissions.launch  home activity. ");
            if (onStartActivityListener != null) {
                onStartActivityListener.onStartActivity(true);
            }
        }
    }

    @NonNull
    @Override
    public List<String> findDeniedPermissions(@NonNull String[] permissions) {
        Logger.d(TAG, " findDeniedPermissions.");
        List<String> needRequestPermissonList = new ArrayList<>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(mActivity.getApplicationContext(),
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        Logger.d(TAG, "LauncherActivity  findDeniedPermissions.needRequestPermissonList.size : " + needRequestPermissonList.size());
        return needRequestPermissonList;
    }

    @Override
    public boolean verifyPermissions(@NonNull int[] grantResults) {
        Logger.d(TAG, " verifyPermissions.");
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                Logger.d(TAG, " verifyPermissions.false");
                return false;
            }
        }
        Logger.d(TAG, " verifyPermissions.true");
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] paramArrayOfInt) {
        Logger.d(TAG, " onRequestPermissionsResult.requestCode : " + requestCode);
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                Logger.d(TAG, " onRequestPermissionsResult.display a window for setting. ");
                showMissingPermissionDialog();
                isNeedCheck = false;
            } else {
                Logger.d(TAG, " onRequestPermissionsResult.launch home activity. ");
                if (onStartActivityListener != null) {
                    onStartActivityListener.onStartActivity(true);
                }
            }
        } else {
            Logger.d(TAG, "onRequestPermissionsResult.end. ");
        }
    }

    @Override
    public void showMissingPermissionDialog() {
        Logger.d(TAG, "showMissingPermissionDialog.start.");
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity.getApplicationContext());
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                (dialog, which) -> {
                    Logger.d(TAG, "showMissingPermissionDialog.cancel : ");
                    if (onStartActivityListener != null) {
                        onStartActivityListener.onFinish();
                    }
                });

        builder.setPositiveButton(R.string.setting,
                (dialog, which) -> {
                    Logger.d(TAG, "showMissingPermissionDialog.setting : ");
                    startAppSettings();
                });

        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + mActivity.getApplicationContext().getPackageName()));
        mActivity.getApplicationContext().startActivity(intent);
    }

    public void setOnStartActivityListener(OnStartActivityListener onStartActivityListener) {
        this.onStartActivityListener = onStartActivityListener;
    }

    public interface OnStartActivityListener {
        void onStartActivity(boolean isNeedFinish);

        void onFinish();
    }

}
