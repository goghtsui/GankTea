package com.gogh.afternoontea.entity.weather;

import android.support.annotation.NonNull;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 空气质量，仅限国内部分城市，国际城市无此字段</p>
 * <p> Created by <b>高晓峰</b> on 12/22/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/22/2016 do fisrt create. </li>
 */
public class AqiBean {
    /**
     * city : {"aqi":"22","co":"0","no2":"17","o3":"47","pm10":"69","pm25":"22","qlty":"优","so2":"4"}
     */

    private CityBean city;

    public CityBean getCity() {
        return city;
    }

    public void setCity(CityBean city) {
        this.city = city;
    }

    public static class CityBean {
        /**
         * aqi : 22 //空气质量指数
         * co : 0  //一氧化碳1小时平均值(ug/m³)
         * no2 : 17 //二氧化氮1小时平均值(ug/m³)
         * o3 : 47  //臭氧1小时平均值(ug/m³)
         * pm10 : 69 //PM10 1小时平均值(ug/m³)
         * pm25 : 22 //PM2.5 1小时平均值(ug/m³)
         * qlty : 优  //空气质量类别
         * so2 : 4 //二氧化硫1小时平均值(ug/m³)
         */

        /**
         * 空气质量指数
         */
        private String aqi;

        /**
         * 一氧化碳1小时平均值(ug/m³)
         */
        private String co;

        /**
         * 二氧化氮1小时平均值(ug/m³)
         */
        private String no2;

        /**
         * 臭氧1小时平均值(ug/m³)
         */
        private String o3;

        /**
         * PM10 1小时平均值(ug/m³)
         */
        private String pm10;

        /**
         * PM2.5 1小时平均值(ug/m³)
         */
        private String pm25;

        /**
         * 空气质量类别
         */
        private String qlty;

        /**
         * 二氧化硫1小时平均值(ug/m³)
         */
        private String so2;

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getQlty() {
            return qlty;
        }

        public void setQlty(String qlty) {
            this.qlty = qlty;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        @NonNull
        @Override
        public String toString() {
            return "CityBean{" +
                    "aqi='" + aqi + '\'' +
                    ", co='" + co + '\'' +
                    ", no2='" + no2 + '\'' +
                    ", o3='" + o3 + '\'' +
                    ", pm10='" + pm10 + '\'' +
                    ", pm25='" + pm25 + '\'' +
                    ", qlty='" + qlty + '\'' +
                    ", so2='" + so2 + '\'' +
                    '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "AqiBean{" +
                "city=" + city +
                '}';
    }
}