package com.gogh.afternoontea.main;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.gogh.afternoontea.R;
import com.gogh.afternoontea.preference.PreferenceManager;
import com.gogh.afternoontea.preference.imp.Configuration;
import com.gogh.afternoontea.theme.ThemeManager;
import com.gogh.afternoontea.utils.DataUtil;
import com.gogh.afternoontea.utils.Logger;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/30/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/30/2016 do fisrt create. </li>
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity implements ThemeManager.OnUpdateThemeListener,
        ColorChooserDialog.ColorCallback {

    private static final String TAG = "BaseAppCompatActivity";

    protected abstract void updateThemeByChoice(int resId);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ATApplication.THEME);
        ThemeManager.newInstance().registerUpdateThemeListener(this);
        setupNoTitle();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 设置状态栏、导航栏颜色
     *
     * @author 高晓峰
     * @date 2016/12/20
     * ChangeLog:
     * <li> 高晓峰 on 2016/12/20 </li>
     */
    private void setupNoTitle() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        ThemeManager.newInstance().updateThemeByColor(BaseAppCompatActivity.this, selectedColor);
        Configuration.newInstance().setTheme(ATApplication.THEME);
        onUpdateByChangeTheme(selectedColor);
        PreferenceManager.newInstance().notifyCardModeChanged();
    }

    @Override
    public void onUpdateTheme(int themeColor) {
        Logger.d(TAG, "base onUpdateTheme");
        ThemeManager.newInstance().updateThemeByWeather(BaseAppCompatActivity.this, themeColor);
        onUpdateByChangeTheme(themeColor);
    }

    /**
     * 更新标题栏及其它外漏主题色
     *
     * @param themeColor
     * @ChangeLog: <li> 高晓峰 on 9/6/2017 </li>
     * @author 高晓峰
     * @date 9/6/2017
     */
    private void onUpdateByChangeTheme(int themeColor) {
        updateThemeByChoice(themeColor);
        // 动态设置标题栏的颜色
        getWindow().setStatusBarColor(/*ContextCompat.getColor(BaseAppCompatActivity.this, themeColor)*/themeColor);
        // 动态设置导航栏的颜色
        getWindow().setNavigationBarColor(/*ContextCompat.getColor(BaseAppCompatActivity.this, themeColor)*/themeColor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThemeManager.newInstance().clear();
        PreferenceManager.newInstance().clear();
        DataUtil.cleanApplicationData(this);
    }

    protected void chooseTheme() {
        new ColorChooserDialog.Builder(BaseAppCompatActivity.this, R.string.theme)
                .customColors(R.array.colors, null)
                .doneButton(R.string.done)
                .cancelButton(R.string.cancel)
                .allowUserColorInput(false)
                .allowUserColorInputAlpha(false)
                .show();
    }

}
