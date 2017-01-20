package com.gogh.afternoontea.permission;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/6/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/6/2017 do fisrt create. </li>
 */
public interface RequestPermissions {

    void checkPermissions(String... permissions);

    @NonNull
    List<String> findDeniedPermissions(String[] permissions);

    boolean verifyPermissions(int[] grantResults);

    void onRequestPermissionsResult(int requestCode,
                                    String[] permissions, int[] paramArrayOfInt);

    void showMissingPermissionDialog();

    void startAppSettings();

}
