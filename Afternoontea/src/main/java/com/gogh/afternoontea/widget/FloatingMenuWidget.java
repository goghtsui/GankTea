package com.gogh.afternoontea.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import com.gogh.afternoontea.app.Initializer;
import com.gogh.afternoontea.entity.FloatMenu;
import com.gogh.afternoontea.utils.AndroidUtil;
import com.gogh.afternoontea.utils.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/10/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/10/2017 do fisrt create. </li>
 */
public class FloatingMenuWidget implements Initializer{

    /**
     * duration of animations, in milliseconds
     */
    protected static final int DURATION = 800;
    private Context context;
    private List<View> mTargetViews;
    private RelativeLayout floatMenuContainer;
    @NonNull
    private List<FloatMenu> mFloatMenus = new ArrayList<>();

    @NonNull
    private ActionType actionType = ActionType.CLOSING;

    public FloatingMenuWidget(Context context, RelativeLayout floatMenuContainer, @NonNull List<View> mTargetViews) {
        this.context = context;
        this.mTargetViews = mTargetViews;
        this.floatMenuContainer = floatMenuContainer;
        initView();
    }

    @Override
    public void init() {
    }

    @Override
    public void initView() {
        floatMenuContainer.setOnClickListener(v -> closeMenu());
        for (int i = 0; i < mTargetViews.size(); i++) {
            FloatMenu floatMenu = new FloatMenu(mTargetViews.get(i));
            floatMenu.setPointer(MathUtil.computePoint((int) (AndroidUtil.getScreenSize(context)[0] / 2), i, mTargetViews.size() - 1));
            mFloatMenus.add(floatMenu);
        }
    }

    public void openMenu() {
        floatMenuContainer.setVisibility(View.VISIBLE);
        for (int index = 0; index < mFloatMenus.size(); index++) {
            PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -mFloatMenus.get(index).getPointer().getX());
            PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -mFloatMenus.get(index).getPointer().getY());
            PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat(View.ROTATION, 360);
            PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.8f);
            PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.8f);
            PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 1);

            ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(mTargetViews.get(index), translationX, translationY, rotation, scaleX, scaleY, alpha);
            animation.setDuration(DURATION);
            animation.setInterpolator(new OvershootInterpolator(0.9f));
            int finalIndex = index;
            animation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (finalIndex == mFloatMenus.size() - 1) {
                        actionType = ActionType.OPENING;
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            animation.start();
        }
    }

    public void closeMenu() {
        for (int index = 0; index < mFloatMenus.size(); index++) {
            PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0);
            PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0);
            PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat(View.ROTATION, -360);
            PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0);
            PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0);
            PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0);

            ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(mTargetViews.get(index), translationX, translationY, rotation, scaleX, scaleY, alpha);
            animation.setDuration(DURATION);
            animation.setInterpolator(new OvershootInterpolator(0.9f));
            int finalIndex = index;
            animation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (finalIndex == mFloatMenus.size() - 1) {
                        floatMenuContainer.setVisibility(View.GONE);
                        actionType = ActionType.CLOSING;
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            animation.start();
        }
    }

    public void autoBootMenu() {
        if (actionType == ActionType.OPENING) {
            closeMenu();
        } else if (actionType == ActionType.CLOSING) {
            openMenu();
        }
    }

    public boolean isShowing() {
        return actionType == ActionType.OPENING;
    }

    protected enum ActionType {OPENING, CLOSING}

}
