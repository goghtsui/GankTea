package com.gogh.afternoontea.entity.weather;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description:  实况天气</p>
 * <p> Created by <b>高晓峰</b> on 12/22/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/22/2016 do fisrt create. </li>
 */
public class NowBean {
    /**
     * cond : {"code":"101","txt":"多云"}
     * fl : -3
     * hum : 31
     * pcpn : 0
     * pres : 1025
     * tmp : 3
     * vis : 10
     * wind : {"deg":"300","dir":"北风","sc":"5-6","spd":"29"}
     */

    /**
     *  天气状况
     */
    private CondBean cond;

    /**
     *  体感温度
     */
    private String fl;

    /**
     *  相对湿度（%）
     */
    private String hum;

    /**
     *  降水量（mm）
     */
    private String pcpn;

    /**
     *  气压
     */
    private String pres;

    /**
     *  温度
     */
    private String tmp;

    /**
     *  能见度（km）
     */
    private String vis;

    /**
     *  风向
     */
    private WindBean wind;

    public CondBean getCond() {
        return cond;
    }

    public void setCond(CondBean cond) {
        this.cond = cond;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
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

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public WindBean getWind() {
        return wind;
    }

    public void setWind(WindBean wind) {
        this.wind = wind;
    }

    public static class CondBean {
        /**
         * code : 101
         * txt : 多云
         */

        /**
         *  天气状况代码
         */
        private String code;

        /**
         *  天气状况描述
         */
        private String txt;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        @Override
        public String toString() {
            return "CondBean{" +
                    "code='" + code + '\'' +
                    ", txt='" + txt + '\'' +
                    '}';
        }
    }

    /**
     *  风向
     */
    public static class WindBean {
        /**
         * deg : 300
         * dir : 北风
         * sc : 5-6
         * spd : 29
         */

        /**
         *  风向（360度）
         */
        private String deg;

        /**
         *  风向
         */
        private String dir;

        /**
         *  风力
         */
        private String sc;

        /**
         *  风速（kmph）
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

        @Override
        public String toString() {
            return "WindBean{" +
                    "deg='" + deg + '\'' +
                    ", dir='" + dir + '\'' +
                    ", sc='" + sc + '\'' +
                    ", spd='" + spd + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NowBean{" +
                "cond=" + cond +
                ", fl='" + fl + '\'' +
                ", hum='" + hum + '\'' +
                ", pcpn='" + pcpn + '\'' +
                ", pres='" + pres + '\'' +
                ", tmp='" + tmp + '\'' +
                ", vis='" + vis + '\'' +
                ", wind=" + wind +
                '}';
    }
}