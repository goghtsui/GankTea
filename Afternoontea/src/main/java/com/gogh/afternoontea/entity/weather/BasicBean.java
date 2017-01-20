package com.gogh.afternoontea.entity.weather;

import android.support.annotation.NonNull;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description:  基础信息</p>
 * <p> Created by <b>高晓峰</b> on 12/22/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/22/2016 do fisrt create. </li>
 */
public class BasicBean {
    /**
     * city : 北京
     * cnty : 中国
     * id : CN101010100
     * lat : 39.904000
     * lon : 116.391000
     * update : {"loc":"2016-12-22 10:51","utc":"2016-12-22 02:51"}
     */

    /**
     * 城市
     */
    private String city;

    /**
     * 国家
     */
    private String cnty;

    /**
     * 城市ID
     */
    private String id;

    /**
     * 经度
     */
    private String lat;

    /**
     * 纬度
     */
    private String lon;

    /**
     * 更新时间
     */
    private UpdateBean update;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public UpdateBean getUpdate() {
        return update;
    }

    public void setUpdate(UpdateBean update) {
        this.update = update;
    }

    public static class UpdateBean {

        /**
         * loc : 2016-12-22 10:51
         * utc : 2016-12-22 02:51
         */

        /**
         *  当地时间
         */
        private String loc;

        /**
         *  UTC时间
         */
        private String utc;

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getUtc() {
            return utc;
        }

        public void setUtc(String utc) {
            this.utc = utc;
        }

        @NonNull
        @Override
        public String toString() {
            return "UpdateBean{" +
                    "loc='" + loc + '\'' +
                    ", utc='" + utc + '\'' +
                    '}';
        }
    }
}