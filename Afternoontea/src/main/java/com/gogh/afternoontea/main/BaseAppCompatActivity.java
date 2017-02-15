package com.gogh.afternoontea.main;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.gogh.afternoontea.R;
import com.gogh.afternoontea.preference.imp.Configuration;
import com.gogh.afternoontea.theme.ThemeManager;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/30/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/30/2016 do fisrt create. </li>
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity implements ThemeManager.OnUpdateThemeListener,
        ColorChooserDialog.ColorCallback {

    protected abstract void updateThemeByChoice(int resId);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ATApplication.THEME);
        ThemeManager.newInstance().registerUpdateThemeListener(this);
        new Configuration(getApplicationContext(), Configuration.FLAG_CUSTOM).setTheme(ATApplication.THEME);
        setupNoTitle();
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
        updateTheme(selectedColor);
    }

    /**
     *  设置主题，并通知显示的activity更改主题色
     * @param selectedColor
     */
    private void updateTheme(@ColorInt int selectedColor) {
        ThemeManager.newInstance().updateThemeByColor(BaseAppCompatActivity.this, selectedColor);
        new Configuration(getApplicationContext(), Configuration.FLAG_CUSTOM).setTheme(ATApplication.THEME);
    }

    @Override
    public void onUpdateTheme(int themeColor) {
        ThemeManager.newInstance().updateThemeByWeather(BaseAppCompatActivity.this, themeColor);
        updateThemeByChoice(themeColor);
        // 动态设置标题栏的颜色
        getWindow().setStatusBarColor(ContextCompat.getColor(BaseAppCompatActivity.this, themeColor));
        // 动态设置导航栏的颜色
        getWindow().setNavigationBarColor(ContextCompat.getColor(BaseAppCompatActivity.this, themeColor));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThemeManager.newInstance().unRegisterUpdateThemeListener(this);
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
