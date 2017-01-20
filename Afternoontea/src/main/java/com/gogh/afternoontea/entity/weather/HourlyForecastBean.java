package com.gogh.afternoontea.entity.weather;

import android.support.annotation.NonNull;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description:  每三小时天气预报</p>
 * <p> Created by <b>高晓峰</b> on 12/22/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/22/2016 do fisrt create. </li>
 */
public class HourlyForecastBean {
    /**
     * date : 2016-12-22 13:00
     * hum : 27
     * pop : 0
     * pres : 1025
     * tmp : 6
     * wind : {"deg":"320","dir":"西北风","sc":"微风","spd":"24"}
     */

    /**
     * 时间
     */
    private String date;

    /**
     * 相对湿度（%）
     */
    private String hum;

    /**
     * 降水概率
     */
    private String pop;

    /**
     * 气压
     */
    private String pres;

    /**
     * 温度
     */
    private String tmp;

    /**
     * 风力风向
     */
    private WindBeanXX wind;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public WindBeanXX getWind() {
        return wind;
    }

    public void setWind(WindBeanXX wind) {
        this.wind = wind;
    }

    public static class WindBeanXX {
        /**
         * deg : 320
         * dir : 西北风
         * sc : 微风
         * spd : 24
         */

        /**
         * 风向（360度）
         */
        private String deg;

        /**
         * 风向
         */
        private String dir;

        /**
         * 风力
         */
        private String sc;

        /**
         * 风速（kmph）
         */
        private String spd;

        public String getDeg() {
            return deg;
        }

        public void setDeg(String deg) {
            this.deg = deg;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getSc() {
            return sc;
        }

        public void setSc(String sc) {
            this.sc = sc;
        }

        public String getSpd() {
            return spd;
        }

        public void setSpd(String spd) {
            this.spd = spd;
        }

        @NonNull
        @Override
        public String toString() {
            return "WindBeanXX{" +
                    "deg='" + deg + '\'' +
                    ", dir='" + dir + '\'' +
                    ", sc='" + sc + '\'' +
                    ", spd='" + spd + '\'' +
                    '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "HourlyForecastBean{" +
                "date='" + date + '\'' +
                ", hum='" + hum + '\'' +
                ", pop='" + pop + '\'' +
                ", pres='" + pres + '\'' +
                ", tmp='" + tmp + '\'' +
                ", wind=" + wind +
                '}';
    }

}