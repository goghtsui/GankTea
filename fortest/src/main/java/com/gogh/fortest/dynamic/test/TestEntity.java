package com.gogh.fortest.dynamic.test;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 8/23/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 8/23/2017 do fisrt create. </li>
 */

public class TestEntity {

    private int width;
    private int height;
    private int pointX;
    private int pointY;
    private String imageUrl;

    public TestEntity() {
    }

    public TestEntity(int width, int height, int pointX, int pointY, String imageUrl) {
        this.width = width;
        this.height = height;
        this.pointX = pointX;
        this.pointY = pointY;
        this.imageUrl = imageUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPointX() {
        return pointX;
    }

    public void setPointX(int pointX) {
        this.pointX = pointX;
    }

    public int getPointY() {
        return pointY;
    }

    public void setPointY(int pointY) {
        this.pointY = pointY;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "width=" + width +
                ", height=" + height +
                ", pointX=" + pointX +
                ", pointY=" + pointY +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

}
