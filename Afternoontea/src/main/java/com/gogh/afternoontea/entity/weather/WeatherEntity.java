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

    private List<ResultsBean> results;

    public WeatherEntity() {
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * location : {"id":"WX4FBXXFKE4F","name":"北京","country":"CN","path":"北京,北京,中国","timezone":"Asia/Shanghai","timezone_offset":"+08:00"}
         * now : {"text":"晴","code":"0","temperature":"24"}
         * last_update : 2017-09-06T07:55:00+08:00
         */

        private LocationBean location;
        private NowBean now;
        private String last_update;

        public ResultsBean() {
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }

        public static class LocationBean {
            /**
             * id : WX4FBXXFKE4F
             * name : 北京
             * country : CN
             * path : 北京,北京,中国
             * timezone : Asia/Shanghai
             * timezone_offset : +08:00
             */

            private String id;
            private String name;
            private String country;
            private String path;
            private String timezone;
            private String timezone_offset;

            public LocationBean() {
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }

            public String getTimezone_offset() {
                return timezone_offset;
            }

            public void setTimezone_offset(String timezone_offset) {
                this.timezone_offset = timezone_offset;
            }

            @Override
            public String toString() {
                return "LocationBean{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", country='" + country + '\'' +
                        ", path='" + path + '\'' +
                        ", timezone='" + timezone + '\'' +
                        ", timezone_offset='" + timezone_offset + '\'' +
                        '}';
            }
        }

        public static class NowBean {
            /**
             * text : 晴
             * code : 0
             * temperature : 24
             */

            private String text;
            private String code;
            private String temperature;

            public NowBean() {
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            @Override
            public String toString() {
                return "NowBean{" +
                        "text='" + text + '\'' +
                        ", code='" + code + '\'' +
                        ", temperature='" + temperature + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "location=" + location +
                    ", now=" + now +
                    ", last_update='" + last_update + '\'' +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "WeatherEntity{" +
                "results=" + results +
                '}';
    }

}
