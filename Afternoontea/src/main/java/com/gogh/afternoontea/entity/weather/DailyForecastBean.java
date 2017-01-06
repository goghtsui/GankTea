package com.gogh.afternoontea.entity.weather;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description:  7天天气预报</p>
 * <p> Created by <b>高晓峰</b> on 12/22/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/22/2016 do fisrt create. </li>
 */
public class DailyForecastBean {
    /**
     * astro : {"sr":"07:32","ss":"16:53"}
     * cond : {"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"}
     * date : 2016-12-22
     * hum : 38
     * pcpn : 0.0
     * pop : 0
     * pres : 1026
     * tmp : {"max":"6","min":"-5"}
     * uv : 1
     * vis : 10
     * wind : {"deg":"320","dir":"北风","sc":"3-4","spd":"11"}
     */

    /**
     * 天文数值
     */
    private AstroBean astro;

    /**
     * 天气状况
     */
    private CondBeanX cond;

    /**
     * 预报日期
     */
    private String date;

    /**
     * 相对湿度（%）
     */
    private String hum;

    /**
     * 降水量（mm）
     */
    private String pcpn;

    /**
     *  降水概率
     */
    private String pop;

    /**
     * 气压
     */
    private String pres;

    /**
     * 温度
     */
    private TmpBean tmp;

    /**
     * 紫外线指数
     */
    private String uv;

    /**
     * 能见度（km）
     */
    private String vis;

    /**
     *
     */
    private WindBeanX wind;

    public AstroBean getAstro() {
        return astro;
    }

    public void setAstro(AstroBean astro) {
        this.astro = astro;
    }

    public CondBeanX getCond() {
        return cond;
    }

    public void setCond(CondBeanX cond) {
        this.cond = cond;
    }

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

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
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

    public TmpBean getTmp() {
        return tmp;
    }

    public void setTmp(TmpBean tmp) {
        this.tmp = tmp;
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public WindBeanX getWind() {
        return wind;
    }

    public void setWind(WindBeanX wind) {
        this.wind = wind;
    }

    /**
     * 天文数值
     */
    public static class AstroBean {
        /**
         * sr : 07:32
         * ss : 16:53
         */

        /**
         * 日出
         */
        private String sr;

        /**
         * 日落
         */
        private String ss;

        public String getSr() {
            return sr;
        }

        public void setSr(String sr) {
            this.sr = sr;
        }

        public String getSs() {
            return ss;
        }

        public void setSs(String ss) {
            this.ss = ss;
        }

        @Override
        public String toString() {
            return "AstroBean{" +
                    "sr='" + sr + '\'' +
                    ", ss='" + ss + '\'' +
                    '}';
        }
    }

    /**
     * 天气状况
     */
    public static class CondBeanX {
        /**
         * code_d : 100
         * code_n : 100
         * txt_d : 晴
         * txt_n : 晴
         */

        /**
         * 白天天气状况代码 参考http://www.heweather.com/documents/condition-code
         */
        private String code_d;

        /**
         * 夜间天气状况代码
         */
        private String code_n;

        /**
         * 白天天气状况描述
         */
        private String txt_d;

        /**
         * 夜间天气状况描述
         */
        private String txt_n;

        public String getCode_d() {
            return code_d;
        }

        public void setCode_d(String code_d) {
            this.code_d = code_d;
        }

        public String getCode_n() {
            return code_n;
        }

        public void setCode_n(String code_n) {
            this.code_n = code_n;
        }

        public String getTxt_d() {
            return txt_d;
        }

        public void setTxt_d(String txt_d) {
            this.txt_d = txt_d;
        }

        public String getTxt_n() {
            return txt_n;
        }

        public void setTxt_n(String txt_n) {
            this.txt_n = txt_n;
        }

        @Override
        public String toString() {
            return "CondBeanX{" +
                    "code_d='" + code_d + '\'' +
                    ", code_n='" + code_n + '\'' +
                    ", txt_d='" + txt_d + '\'' +
                    ", txt_n='" + txt_n + '\'' +
                    '}';
        }
    }

    /**
     * 温度
     */
    public static class TmpBean {
        /**
         * max : 6
         * min : -5
         */

        /**
         * 最高温度
         */
        private String max;

        /**
         * 最低温度
         */
        private String min;

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        @Override
        public String toString() {
            return "TmpBean{" +
                    "max='" + max + '\'' +
                    ", min='" + min + '\'' +
                    '}';
        }
    }

    /**
     * 风力风向
     */
    public static class WindBeanX {
        /**
         * deg : 320
         * dir : 北风
         * sc : 3-4
         * spd : 11
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

        @Override
        public String toString() {
            return "WindBeanX{" +
                    "deg='" + deg + '\'' +
                    ", dir='" + dir + '\'' +
                    ", sc='" + sc + '\'' +
                    ", spd='" + spd + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DailyForecastBean{" +
                "astro=" + astro +
                ", cond=" + cond +
                ", date='" + date + '\'' +
                ", hum='" + hum + '\'' +
                ", pcpn='" + pcpn + '\'' +
                ", pop='" + pop + '\'' +
                ", pres='" + pres + '\'' +
                ", tmp=" + tmp +
                ", uv='" + uv + '\'' +
                ", vis='" + vis + '\'' +
                ", wind=" + wind +
                '}';
    }
}