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

    @Override
    public void onUpdateTheme(int themeStyleId) {
        updateThemeByChoice(themeStyleId);
        // 动态设置标题栏的颜色
        getWindow().setStatusBarColor(ContextCompat.getColor(BaseAppCompatActivity.this, themeStyleId));
        // 动态设置导航栏的颜色
        getWindow().setNavigationBarColor(ContextCompat.getColor(BaseAppCompatActivity.this, themeStyleId));
    }

    private void updateTheme(@ColorInt int selectedColor) {
        if (selectedColor == ContextCompat.getColor(BaseAppCompatActivity.this, R.color.colorBluePrimary)) {
            ATApplication.THEME = R.style.BlueTheme;
            ThemeManager.newInstance().setTheme(R.color.colorBluePrimary);
        } else if (selectedColor == ContextCompat.getColor(BaseAppCompatActivity.this, R.color.colorRedPrimary)) {
            ATApplication.THEME = R.style.RedTheme;
            ThemeManager.newInstance().setTheme(R.color.colorRedPrimary);
        } else if (selectedColor == ContextCompat.getColor(BaseAppCompatActivity.this, R.color.colorBrownPrimary)) {
            ATApplication.THEME = R.style.BrownTheme;
            ThemeManager.newInstance().setTheme(R.color.colorBrownPrimary);
        } else if (selectedColor == ContextCompat.getColor(BaseAppCompatActivity.this, R.color.colorGreenPrimary)) {
            ATApplication.THEME = R.style.GreenTheme;
            ThemeManager.newInstance().setTheme(R.color.colorGreenPrimary);
        } else if (selectedColor == ContextCompat.getColor(BaseAppCompatActivity.this, R.color.colorPurplePrimary)) {
            ATApplication.THEME = R.style.PurpleTheme;
            ThemeManager.newInstance().setTheme(R.color.colorPurplePrimary);
        } else if (selectedColor == ContextCompat.getColor(BaseAppCompatActivity.this, R.color.colorTealPrimary)) {
            ATApplication.THEME = R.style.TealTheme;
            ThemeManager.newInstance().setTheme(R.color.colorTealPrimary);
        } else if (selectedColor == ContextCompat.getColor(BaseAppCompatActivity.this, R.color.colorPinkPrimary)) {
            ATApplication.THEME = R.style.PinkTheme;
            ThemeManager.newInstance().setTheme(R.color.colorPinkPrimary);
        } else if (selectedColor == ContextCompat.getColor(BaseAppCompatActivity.this, R.color.colorDeepPurplePrimary)) {
            ATApplication.THEME = R.style.DeepPurpleTheme;
            ThemeManager.newInstance().setTheme(R.color.colorDeepPurplePrimary);
        } else if (selectedColor == ContextCompat.getColor(BaseAppCompatActivity.this, R.color.colorOrangePrimary)) {
            ATApplication.THEME = R.style.OrangeTheme;
            ThemeManager.newInstance().setTheme(R.color.colorOrangePrimary);
        } else if (selectedColor == ContextCompat.getColor(BaseAppCompatActivity.this, R.color.colorIndigoPrimary)) {
            ATApplication.THEME = R.style.IndigoTheme;
            ThemeManager.newInstance().setTheme(R.color.colorIndigoPrimary);
        } else if (selectedColor == ContextCompat.getColor(BaseAppCompatActivity.this, R.color.colorLightGreenPrimary)) {
            ATApplication.THEME = R.style.LightGreenTheme;
            ThemeManager.newInstance().setTheme(R.color.colorLightGreenPrimary);
        } else if (selectedColor == ContextCompat.getColor(BaseAppCompatActivity.this, R.color.colorDeepOrangePrimary)) {
            ATApplication.THEME = R.style.DeepOrangeTheme;
            ThemeManager.newInstance().setTheme(R.color.colorDeepOrangePrimary);
        } else if (selectedColor == ContextCompat.getColor(BaseAppCompatActivity.this, R.color.colorLimePrimary)) {
            ATApplication.THEME = R.style.LimeTheme;
            ThemeManager.newInstance().setTheme(R.color.colorLimePrimary);
        } else if (selectedColor == ContextCompat.getColor(BaseAppCompatActivity.this, R.color.colorBlueGreyPrimary)) {
            ATApplication.THEME = R.style.BlueGreyTheme;
            ThemeManager.newInstance().setTheme(R.color.colorBlueGreyPrimary);
        } else if (selectedColor == ContextCompat.getColor(BaseAppCompatActivity.this, R.color.colorCyanPrimary)) {
            ATApplication.THEME = R.style.CyanTheme;
            ThemeManager.newInstance().setTheme(R.color.colorCyanPrimary);
        }
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
