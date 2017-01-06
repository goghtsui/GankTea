package com.gogh.afternoontea.entity.weather;

import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description:  天气数据封装实体</p>
 * <p> Created by <b>高晓峰</b> on 12/22/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/22/2016 do fisrt create. </li>
 */
public class WeatherEntity {

    /**
     * aqi : {"city":{"aqi":"22","co":"0","no2":"17","o3":"47","pm10":"69","pm25":"22","qlty":"优","so2":"4"}}
     * basic : {"city":"北京","cnty":"中国","id":"CN101010100","lat":"39.904000","lon":"116.391000","update":{"loc":"2016-12-22 10:51","utc":"2016-12-22 02:51"}}
     * daily_forecast : [{"astro":{"sr":"07:32","ss":"16:53"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-12-22","hum":"38","pcpn":"0.0","pop":"0","pres":"1026","tmp":{"max":"6","min":"-5"},"uv":"1","vis":"10","wind":{"deg":"320","dir":"北风","sc":"3-4","spd":"11"}},{"astro":{"sr":"07:33","ss":"16:53"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-12-23","hum":"43","pcpn":"0.0","pop":"0","pres":"1031","tmp":{"max":"3","min":"-6"},"uv":"1","vis":"10","wind":{"deg":"151","dir":"北风","sc":"微风","spd":"1"}},{"astro":{"sr":"07:33","ss":"16:54"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2016-12-24","hum":"53","pcpn":"0.0","pop":"0","pres":"1030","tmp":{"max":"3","min":"-5"},"uv":"1","vis":"10","wind":{"deg":"137","dir":"南风","sc":"微风","spd":"9"}},{"astro":{"sr":"07:34","ss":"16:55"},"cond":{"code_d":"502","code_n":"502","txt_d":"霾","txt_n":"霾"},"date":"2016-12-25","hum":"71","pcpn":"1.2","pop":"97","pres":"1029","tmp":{"max":"3","min":"-3"},"uv":"1","vis":"7","wind":{"deg":"126","dir":"南风","sc":"微风","spd":"1"}},{"astro":{"sr":"07:34","ss":"16:55"},"cond":{"code_d":"104","code_n":"100","txt_d":"阴","txt_n":"晴"},"date":"2016-12-26","hum":"79","pcpn":"0.1","pop":"28","pres":"1034","tmp":{"max":"2","min":"-6"},"uv":"1","vis":"10","wind":{"deg":"161","dir":"北风","sc":"微风","spd":"10"}},{"astro":{"sr":"07:34","ss":"16:56"},"cond":{"code_d":"100","code_n":"101","txt_d":"晴","txt_n":"多云"},"date":"2016-12-27","hum":"66","pcpn":"0.1","pop":"27","pres":"1035","tmp":{"max":"1","min":"-6"},"uv":"-999","vis":"10","wind":{"deg":"274","dir":"南风","sc":"微风","spd":"1"}},{"astro":{"sr":"07:35","ss":"16:57"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-12-28","hum":"47","pcpn":"0.0","pop":"0","pres":"1034","tmp":{"max":"2","min":"-7"},"uv":"-999","vis":"10","wind":{"deg":"322","dir":"北风","sc":"3-4","spd":"10"}}]
     * hourly_forecast : [{"date":"2016-12-22 13:00","hum":"27","pop":"0","pres":"1025","tmp":"6","wind":{"deg":"320","dir":"西北风","sc":"微风","spd":"24"}},{"date":"2016-12-22 16:00","hum":"31","pop":"0","pres":"1025","tmp":"5","wind":{"deg":"328","dir":"西北风","sc":"微风","spd":"16"}},{"date":"2016-12-22 19:00","hum":"39","pop":"0","pres":"1027","tmp":"1","wind":{"deg":"341","dir":"西北风","sc":"微风","spd":"12"}},{"date":"2016-12-22 22:00","hum":"43","pop":"0","pres":"1028","tmp":"-1","wind":{"deg":"348","dir":"无持续风向","sc":"微风","spd":"11"}}]
     * now : {"cond":{"code":"101","txt":"多云"},"fl":"-3","hum":"31","pcpn":"0","pres":"1025","tmp":"3","vis":"10","wind":{"deg":"300","dir":"北风","sc":"5-6","spd":"29"}}
     * status : ok
     * suggestion : {"air":{"brf":"良","txt":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"},"comf":{"brf":"较舒适","txt":"白天虽然天气晴好，但早晚会感觉偏凉，午后舒适、宜人。"},"cw":{"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},"drsg":{"brf":"冷","txt":"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。"},"flu":{"brf":"较易发","txt":"昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。"},"sport":{"brf":"较不宜","txt":"天气较好，但考虑风力较大，天气寒冷，推荐您进行室内运动，若在户外运动须注意保暖。"},"trav":{"brf":"一般","txt":"天气较好，温度稍低，加之风稍大，让人感觉有点凉，会对外出有一定影响，外出注意防风保暖。"},"uv":{"brf":"中等","txt":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"}}
     */

    private String status;
    private AqiBean aqi;
    private BasicBean basic;
    private NowBean now;
    private SuggestionBean suggestion;
    private List<DailyForecastBean> daily_forecast;
    private List<HourlyForecastBean> hourly_forecast;

    public WeatherEntity() {
    }

    public AqiBean getAqi() {
        return aqi;
    }

    public void setAqi(AqiBean aqi) {
        this.aqi = aqi;
    }

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public NowBean getNow() {
        return now;
    }

    public void setNow(NowBean now) {
        this.now = now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SuggestionBean getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(SuggestionBean suggestion) {
        this.suggestion = suggestion;
    }

    public List<DailyForecastBean> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public List<HourlyForecastBean> getHourly_forecast() {
        return hourly_forecast;
    }

    public void setHourly_forecast(List<HourlyForecastBean> hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    @Override
    public String toString() {
        return "WeatherEntity{" +
                "status='" + status + '\'' +
                ",aqi=" + aqi +
                ", basic=" + basic +
                ", now=" + now +
                ", suggestion=" + suggestion +
                ", daily_forecast=" + daily_forecast +
                ", hourly_forecast=" + hourly_forecast +
                '}';
    }
}
