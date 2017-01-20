package com.gogh.afternoontea.entity;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/10/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/10/2017 do fisrt create. </li>
 */
public class FloatMenu {

    private View mTargetView;
    private Pointer mPointer;

    public FloatMenu() {
    }

    public FloatMenu(View mTargetView) {
        this.mTargetView = mTargetView;
    }

    public Pointer getPointer() {
        return mPointer;
    }

    public void setPointer(Pointer mPointer) {
        this.mPointer = mPointer;
    }

    public View getTargetView() {
        return mTargetView;
    }

    public void setTargetView(View mTargetView) {
        this.mTargetView = mTargetView;
    }

    @NonNull
    @Override
    public String toString() {
        return "FloatMenu{" +
                "Pointer=" + mPointer +
                ", TargetView=" + mTargetView +
                '}';
    }

    public static class Pointer {

        private float x;
        private float y;

        public Pointer(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        @NonNull
        @Override
        public String toString() {
            return "Pointer{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

}
