package com.gogh.fortest.card.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import com.gogh.fortest.R;
import com.gogh.fortest.card.adpter.BaseCardAdapter;

import static android.R.attr.focusable;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 2/23/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 2/23/2017 do fisrt create. </li>
 */
public class LaminatedImageView extends RelativeLayout {

    private BaseCardAdapter mAdapter;

    private static final Interpolator sInterpolator = new Interpolator() {

        private float mTension = 1.6f;

        @Override
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * ((mTension + 1) * t + mTension) + 1.0f;
        }
    };

    /**
     * 默认旋转角度
     */
    private static final int DEFAULT_DEGREE = 15;

    /**
     * 旋转角度
     */
    private int mRotationDegree = DEFAULT_DEGREE;

    /**
     * 缩放比例
     */
    private float mScaleOffset = 0f;

    /**
     * 状态值
     */
    private int mCardStatus = STATUS_CLOSE;
    private static final int STATUS_DISPLAY = 0x111213;
    private static final int STATUS_CLOSE = 0x111214;
    private boolean isAnimating = false;

    private OnClickListener mOnClickListener;

    public LaminatedImageView(Context context) {
        this(context, null);
    }

    public LaminatedImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LaminatedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LaminatedImageView);
        mRotationDegree = a.getInt(R.styleable.LaminatedImageView_rotationDegree, DEFAULT_DEGREE);
        mScaleOffset = a.getFloat(R.styleable.LaminatedImageView_scaleOffset, mScaleOffset);
        a.recycle();

        if(mOnClickListener != null){
            setOnClickListener(mOnClickListener);
        }
    }

    public void setAdapter(@NonNull BaseCardAdapter mAdapter){
        this.mAdapter = null;
        this.mAdapter = mAdapter;
        removeAllViewsInLayout();
        for (int i = 0; i < mAdapter.getVisibleCardCount(); i++) {
            View childView = LayoutInflater.from(getContext()).inflate(getLayoutId(mAdapter.getCardLayoutId()), this, false);
            if (childView == null) {
                return;
            }
            bindCardData(i, childView);
            addView(childView, i);
        }
    }

    private int getLayoutId(int layoutid) {
        String resourceTypeName = getContext().getResources().getResourceTypeName(layoutid);
        if (!resourceTypeName.contains("layout")) {
            String errorMsg = getContext().getResources().getResourceName(layoutid) + " is a illegal layoutid , please check your layout id first !";
            throw new RuntimeException(errorMsg);
        }
        return layoutid;
    }

    private void bindCardData(int position, @NonNull View cardview) {
        if (mAdapter != null) {
            mAdapter.onBindData(position, cardview);
            cardview.setTag(position);
        }
    }

    public void autoAnimator(){
        int count = getChildCount();

        for(int i = 0; i < count; i++){
            View child = getChildAt(i);

            float scale = 1 - mScaleOffset * (count - 1 - i);

            child.setPivotX(child.getWidth() / 2);
            child.setPivotY(child.getHeight() * 2 / 3);
            child.setScaleX(scale);
            child.setScaleY(scale);

            switch (mCardStatus) {
                case STATUS_CLOSE:
                    startAnimator(child, i, mRotationDegree * (count - 1 - i), STATUS_DISPLAY);
                    break;
                case STATUS_DISPLAY:
                    startAnimator(child, i, 0, STATUS_CLOSE);
                    break;
            }
        }
    }

    private void startAnimator(View targetView, final int position, float degree, final int status){
        PropertyValuesHolder close = PropertyValuesHolder.ofFloat("rotation",  degree);
        ObjectAnimator closeAnimator = ObjectAnimator.ofPropertyValuesHolder(targetView, close);
        closeAnimator.setInterpolator(sInterpolator);
        closeAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("LaminatedView", "onAnimationEnd ");
                Log.d("LaminatedView", "onAnimationEnd position : " + position);
                Log.d("LaminatedView", "onAnimationEnd getCount : " + mAdapter.getVisibleCardCount());
                if(position == (mAdapter.getVisibleCardCount() - 1)){
                    Log.d("LaminatedView", "onAnimationEnd reset. ");
                    isAnimating = false;
                    mCardStatus = status;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimating = false;
                mCardStatus = status;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                isAnimating = true;
            }
        });
        closeAnimator.start();
    }

    public void setOnClickListener(OnClickListener mOnClickListener){
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        autoAnimator();
        return super.requestFocus(direction, previouslyFocusedRect);
    }

    @Override
    public void setFocusable(boolean focusable) {
        if(!focusable){
            autoAnimator();
        }
        super.setFocusable(focusable);
    }

    @Override
    public void setFocusableInTouchMode(boolean focusableInTouchMode) {
        if(!focusableInTouchMode){
            autoAnimator();
        }
        super.setFocusableInTouchMode(focusableInTouchMode);
    }
}
