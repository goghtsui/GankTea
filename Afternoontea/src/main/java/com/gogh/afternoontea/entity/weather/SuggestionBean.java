package com.gogh.afternoontea.entity.weather;

import android.support.annotation.NonNull;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description:  生活指数，仅限国内城市，国际城市无此字段</p>
 * <p> Created by <b>高晓峰</b> on 12/22/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/22/2016 do fisrt create. </li>
 */
public class SuggestionBean {
    /**
     * air : {"brf":"良","txt":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"}
     * comf : {"brf":"较舒适","txt":"白天虽然天气晴好，但早晚会感觉偏凉，午后舒适、宜人。"}
     * cw : {"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"}
     * drsg : {"brf":"冷","txt":"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。"}
     * flu : {"brf":"较易发","txt":"昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。"}
     * sport : {"brf":"较不宜","txt":"天气较好，但考虑风力较大，天气寒冷，推荐您进行室内运动，若在户外运动须注意保暖。"}
     * trav : {"brf":"一般","txt":"天气较好，温度稍低，加之风稍大，让人感觉有点凉，会对外出有一定影响，外出注意防风保暖。"}
     * uv : {"brf":"中等","txt":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"}
     */

    /**
     * 空气指数
     */
    private AirBean air;

    /**
     * 舒适度指数
     */
    private ComfBean comf;

    /**
     * 洗车指数
     */
    private CwBean cw;

    /**
     * 穿衣指数
     */
    private DrsgBean drsg;

    /**
     * 感冒指数
     */
    private FluBean flu;

    /**
     * 运动指数
     */
    private SportBean sport;

    /**
     * 旅游指数
     */
    private TravBean trav;

    /**
     * 紫外线指数
     */
    private UvBean uv;

    public AirBean getAir() {
        return air;
    }

    public void setAir(AirBean air) {
        this.air = air;
    }

    public ComfBean getComf() {
        return comf;
    }

    public void setComf(ComfBean comf) {
        this.comf = comf;
    }

    public CwBean getCw() {
        return cw;
    }

    public void setCw(CwBean cw) {
        this.cw = cw;
    }

    public DrsgBean getDrsg() {
        return drsg;
    }

    public void setDrsg(DrsgBean drsg) {
        this.drsg = drsg;
    }

    public FluBean getFlu() {
        return flu;
    }

    public void setFlu(FluBean flu) {
        this.flu = flu;
    }

    public SportBean getSport() {
        return sport;
    }

    public void setSport(SportBean sport) {
        this.sport = sport;
    }

    public TravBean getTrav() {
        return trav;
    }

    public void setTrav(TravBean trav) {
        this.trav = trav;
    }

    public UvBean getUv() {
        return uv;
    }

    public void setUv(UvBean uv) {
        this.uv = uv;
    }

    public static class AirBean {
        /**
         * brf : 良
         * txt : 气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。
         */

        private String brf;
        private String txt;

        public String getBrf() {
            return brf;
        }

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        @NonNull
        @Override
        public String toString() {
            return "AirBean{" +
                    "brf='" + brf + '\'' +
                    ", txt='" + txt + '\'' +
                    '}';
        }
    }

    public static class ComfBean {
        /**
         * brf : 较舒适
         * txt : 白天虽然天气晴好，但早晚会感觉偏凉，午后舒适、宜人。
         */

        private String brf;
        private String txt;

        public String getBrf() {
            return brf;
        }

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        @NonNull
        @Override
        public String toString() {
            return "ComfBean{" +
                    "brf='" + brf + '\'' +
                    ", txt='" + txt + '\'' +
                    '}';
        }
    }

    public static class CwBean {
        /**
         * brf : 较适宜
         * txt : 较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。
         */

        private String brf;
        private String txt;

        public String getBrf() {
            return brf;
        }

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        @NonNull
        @Override
        public String toString() {
            return "CwBean{" +
                    "brf='" + brf + '\'' +
                    ", txt='" + txt + '\'' +
                    '}';
        }
    }

    public static class DrsgBean {
        /**
         * brf : 冷
         * txt : 天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。
         */

        private String brf;
        private String txt;

        public String getBrf() {
            return brf;
        }

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        @NonNull
        @Override
        public String toString() {
            return "DrsgBean{" +
                    "brf='" + brf + '\'' +
                    ", txt='" + txt + '\'' +
                    '}';
        }
    }

    public static class FluBean {
        /**
         * brf : 较易发
         * txt : 昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。
         */

        private String brf;
        private String txt;

        public String getBrf() {
            return brf;
        }

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        @NonNull
        @Override
        public String toString() {
            return "FluBean{" +
                    "brf='" + brf + '\'' +
                    ", txt='" + txt + '\'' +
                    '}';
        }
    }

    public static class SportBean {
        /**
         * brf : 较不宜
         * txt : 天气较好，但考虑风力较大，天气寒冷，推荐您进行室内运动，若在户外运动须注意保暖。
         */

        private String brf;
        private String txt;

        public String getBrf() {
            return brf;
        }

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        @NonNull
        @Override
        public String toString() {
            return "SportBean{" +
                    "brf='" + brf + '\'' +
                    ", txt='" + txt + '\'' +
                    '}';
        }
    }

    public static class TravBean {
        /**
         * brf : 一般
         * txt : 天气较好，温度稍低，加之风稍大，让人感觉有点凉，会对外出有一定影响，外出注意防风保暖。
         */

        private String brf;
        private String txt;

        public String getBrf() {
            return brf;
        }

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        @NonNull
        @Override
        public String toString() {
            return "TravBean{" +
                    "brf='" + brf + '\'' +
                    ", txt='" + txt + '\'' +
                    '}';
        }
    }

    public static class UvBean {
        /**
         * brf : 中等
         * txt : 属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。
         */

        private String brf;
        private String txt;

        public String getBrf() {
            return brf;
        }

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        @NonNull
        @Override
        public String toString() {
            return "UvBean{" +
                    "brf='" + brf + '\'' +
                    ", txt='" + txt + '\'' +
                    '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "SuggestionBean{" +
                "air=" + air +
                ", comf=" + comf +
                ", cw=" + cw +
                ", drsg=" + drsg +
                ", flu=" + flu +
                ", sport=" + sport +
                ", trav=" + trav +
                ", uv=" + uv +
                '}';
    }
}