package com.gogh.afternoontea.entity.gank;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 9/12/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 9/12/2017 do fisrt create. </li>
 */

public class SearchEntity implements Serializable {

    private static final long serialVersionUID = 7094107103081510041L;
    /**
     * category : ["iOS","Android","瞎推荐","拓展资源","福利","休息视频"]
     * error : false
     * results : {"Android":[{"_id":"56cc6d23421aa95caa707a69","createdAt":"2015-08-06T07:15:52.65Z","desc":"类似Link Bubble的悬浮式操作设计","publishedAt":"2015-08-07T03:57:48.45Z","type":"Android","url":"https://github.com/recruit-lifestyle/FloatingView","used":true,"who":"mthli"},{"_id":"56cc6d23421aa95caa707a6f","createdAt":"2015-08-07T01:33:07.815Z","desc":"Android开发中，有哪些让你觉得相见恨晚的方法、类或接口？","publishedAt":"2015-08-07T03:57:47.317Z","type":"Android","url":"http://www.zhihu.com/question/33636939","used":true,"who":"lxxself"},{"_id":"56cc6d23421aa95caa707c69","createdAt":"2015-08-06T15:00:38.350Z","desc":"使用注解来处理Activity的状态恢复","publishedAt":"2015-08-07T03:57:48.76Z","type":"Android","url":"https://github.com/tom91136/Akatsuki","used":true,"who":"鲍永章"},{"_id":"56cc6d23421aa95caa707c71","createdAt":"2015-08-07T02:19:44.342Z","desc":"Android Lollipop联系人之PinnedListView简单使用","publishedAt":"2015-08-07T03:57:48.142Z","type":"Android","url":"https://git.oschina.net/way/PinnedHeaderListView","used":true,"who":"有时放纵"},{"_id":"56cc6d23421aa95caa707c78","createdAt":"2015-08-06T14:58:28.171Z","desc":"图片可以自动滚动的ImageView，可以实现视差效果。","publishedAt":"2015-08-07T03:57:48.73Z","type":"Android","url":"https://github.com/Q42/AndroidScrollingImageView","used":true,"who":"鲍永章"}],"iOS":[{"_id":"56cc6d1d421aa95caa707769","createdAt":"2015-08-07T01:32:51.588Z","desc":"LLVM 简介","publishedAt":"2015-08-07T03:57:48.70Z","type":"iOS","url":"http://adriansampson.net/blog/llvm.html","used":true,"who":"CallMeWhy"},{"_id":"56cc6d23421aa95caa707a6b","createdAt":"2015-08-06T14:45:18.733Z","desc":"基于TextKit的UILabel，支持超链接和自定义表达式。","publishedAt":"2015-08-07T03:57:47.242Z","type":"iOS","url":"https://github.com/molon/MLLabel","used":true,"who":"鲍永章"},{"_id":"56cc6d23421aa95caa707bea","createdAt":"2015-08-07T01:33:30.871Z","desc":"Swift 和 C 函数","publishedAt":"2015-08-07T03:57:48.83Z","type":"iOS","url":"http://chris.eidhof.nl/posts/swift-c-interop.html","used":true,"who":"CallMeWhy"},{"_id":"56cc6d23421aa95caa707c77","createdAt":"2015-08-07T01:34:00.984Z","desc":"Arrays Linked Lists 和性能比较","publishedAt":"2015-08-07T03:57:48.174Z","type":"iOS","url":"http://airspeedvelocity.net/2015/08/03/arrays-linked-lists-and-performance/","used":true,"who":"CallMeWhy"}],"休息视频":[{"_id":"56cc6d23421aa95caa707c68","createdAt":"2015-08-06T13:06:17.211Z","desc":"听到就心情大好的歌，简直妖魔哈哈哈哈哈，原地址\nhttp://v.youku.com/v_show/id_XMTQxODA5NDM2.html","publishedAt":"2015-08-07T03:57:48.104Z","type":"休息视频","url":"http://www.zhihu.com/question/21778055/answer/19905413?utm_source=weibo&utm_medium=weibo_share&utm_content=share_answer&utm_campaign=share_button","used":true,"who":"lxxself"}],"拓展资源":[{"_id":"56cc6d23421aa95caa707bdf","createdAt":"2015-08-07T01:36:06.932Z","desc":"Display GitHub code in tree format","publishedAt":"2015-08-07T03:57:48.81Z","type":"拓展资源","url":"https://github.com/buunguyen/octotree","used":true,"who":"lxxself"}],"瞎推荐":[{"_id":"56cc6d23421aa95caa707bd0","createdAt":"2015-08-07T01:52:30.267Z","desc":"程序员的电台FmM，这个页面chrome插件有问题啊哭，我写了回删除不了啊","publishedAt":"2015-08-07T03:57:48.84Z","type":"瞎推荐","url":"https://cmd.fm/","used":true,"who":"lxxself"}],"福利":[{"_id":"56cc6d23421aa95caa707c52","createdAt":"2015-08-07T01:21:06.112Z","desc":"8.7\u2014\u2014（1）","publishedAt":"2015-08-07T03:57:47.310Z","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1eutscfcqtcj20dw0i0q4l.jpg","used":true,"who":"张涵宇"},{"_id":"56cc6d23421aa95caa707c56","createdAt":"2015-08-07T01:21:33.518Z","desc":"8.7\u2014\u2014（2）","publishedAt":"2015-08-07T03:57:47.229Z","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bgw1eutsd0pgiwj20go0p0djn.jpg","used":true,"who":"张涵宇"}]}
     */

    private boolean error;
    private ResultsBean results;
    private List<String> category;

    public SearchEntity() {
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "SearchEntity{" +
                "error=" + error +
                ", results=" + results +
                ", category=" + category +
                '}';
    }

    public static class ResultsBean {

        private List<AndroidBean> Android;
        private List<IOSBean> iOS;
        private List<前端Bean> 前端;
        private List<休息视频Bean> 休息视频;
        private List<拓展资源Bean> 拓展资源;
        private List<瞎推荐Bean> 瞎推荐;
        private List<福利Bean> 福利;

        public List<AndroidBean> getAndroid() {
            return Android;
        }

        public void setAndroid(List<AndroidBean> Android) {
            this.Android = Android;
        }

        public List<IOSBean> getIOS() {
            return iOS;
        }

        public void setIOS(List<IOSBean> iOS) {
            this.iOS = iOS;
        }

        public List<休息视频Bean> get休息视频() {
            return 休息视频;
        }

        public void set休息视频(List<休息视频Bean> 休息视频) {
            this.休息视频 = 休息视频;
        }

        public List<拓展资源Bean> get拓展资源() {
            return 拓展资源;
        }

        public void set拓展资源(List<拓展资源Bean> 拓展资源) {
            this.拓展资源 = 拓展资源;
        }

        public List<瞎推荐Bean> get瞎推荐() {
            return 瞎推荐;
        }

        public void set瞎推荐(List<瞎推荐Bean> 瞎推荐) {
            this.瞎推荐 = 瞎推荐;
        }

        public List<福利Bean> get福利() {
            return 福利;
        }

        public void set福利(List<福利Bean> 福利) {
            this.福利 = 福利;
        }

        public List<前端Bean> get前端() {
            return 前端;
        }

        public void set前端(List<前端Bean> 前端) {
            this.前端 = 前端;
        }

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "Android=" + Android +
                    ", iOS=" + iOS +
                    ", 休息视频=" + 休息视频 +
                    ", 拓展资源=" + 拓展资源 +
                    ", 瞎推荐=" + 瞎推荐 +
                    ", 福利=" + 福利 +
                    '}';
        }

        public static class AndroidBean implements BaseEntity {

            private String _id;
            private String createdAt;
            private String desc;
            private String publishedAt;
            private String source;
            private String type;
            private String url;
            private boolean used;
            private String who;
            private List<String> images;

            public AndroidBean() {
            }

            @Override
            public String get_id() {
                return _id;
            }

            @Override
            public void set_id(String _id) {
                this._id = _id;
            }

            @Override
            public String getCreatedAt() {
                return createdAt;
            }

            @Override
            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            @Override
            public String getDesc() {
                return desc;
            }

            @Override
            public void setDesc(String desc) {
                this.desc = desc;
            }

            @Override
            public String getPublishedAt() {
                return publishedAt;
            }

            @Override
            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            @Override
            public String getType() {
                return type;
            }

            @Override
            public void setType(String type) {
                this.type = type;
            }

            @Override
            public String getUrl() {
                return url;
            }

            @Override
            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public boolean isUsed() {
                return used;
            }

            @Override
            public void setUsed(boolean used) {
                this.used = used;
            }

            @Override
            public String getWho() {
                return who;
            }

            @Override
            public void setWho(String who) {
                this.who = who;
            }

            @Override
            public String getSource() {
                return source;
            }

            @Override
            public void setSource(String source) {
                this.source = source;
            }

            @Override
            public List<String> getImages() {
                return images;
            }

            @Override
            public void setImages(List<String> images) {
                this.images = images;
            }

            @Override
            public String toString() {
                return "AndroidBean{" +
                        "_id='" + _id + '\'' +
                        ", createdAt='" + createdAt + '\'' +
                        ", desc='" + desc + '\'' +
                        ", publishedAt='" + publishedAt + '\'' +
                        ", source='" + source + '\'' +
                        ", type='" + type + '\'' +
                        ", url='" + url + '\'' +
                        ", used=" + used +
                        ", who='" + who + '\'' +
                        ", images=" + images +
                        '}';
            }
        }

        public static class IOSBean implements com.gogh.afternoontea.entity.gank.BaseEntity {

            private String _id;
            private String createdAt;
            private String desc;
            private String publishedAt;
            private String source;
            private String type;
            private String url;
            private boolean used;
            private String who;
            private List<String> images;

            public IOSBean() {
            }

            @Override
            public String get_id() {
                return _id;
            }

            @Override
            public void set_id(String _id) {
                this._id = _id;
            }

            @Override
            public String getCreatedAt() {
                return createdAt;
            }

            @Override
            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            @Override
            public String getDesc() {
                return desc;
            }

            @Override
            public void setDesc(String desc) {
                this.desc = desc;
            }

            @Override
            public String getPublishedAt() {
                return publishedAt;
            }

            @Override
            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            @Override
            public String getType() {
                return type;
            }

            @Override
            public void setType(String type) {
                this.type = type;
            }

            @Override
            public String getUrl() {
                return url;
            }

            @Override
            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public boolean isUsed() {
                return used;
            }

            @Override
            public void setUsed(boolean used) {
                this.used = used;
            }

            @Override
            public String getWho() {
                return who;
            }

            @Override
            public void setWho(String who) {
                this.who = who;
            }

            @Override
            public String getSource() {
                return source;
            }

            @Override
            public void setSource(String source) {
                this.source = source;
            }

            @Override
            public List<String> getImages() {
                return images;
            }

            @Override
            public void setImages(List<String> images) {
                this.images = images;
            }

            @Override
            public String toString() {
                return "AndroidBean{" +
                        "_id='" + _id + '\'' +
                        ", createdAt='" + createdAt + '\'' +
                        ", desc='" + desc + '\'' +
                        ", publishedAt='" + publishedAt + '\'' +
                        ", source='" + source + '\'' +
                        ", type='" + type + '\'' +
                        ", url='" + url + '\'' +
                        ", used=" + used +
                        ", who='" + who + '\'' +
                        ", images=" + images +
                        '}';
            }
        }

        public static class 前端Bean implements com.gogh.afternoontea.entity.gank.BaseEntity {

            private String _id;
            private String createdAt;
            private String desc;
            private String publishedAt;
            private String source;
            private String type;
            private String url;
            private boolean used;
            private String who;
            private List<String> images;

            public 前端Bean() {
            }

            @Override
            public String get_id() {
                return _id;
            }

            @Override
            public void set_id(String _id) {
                this._id = _id;
            }

            @Override
            public String getCreatedAt() {
                return createdAt;
            }

            @Override
            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            @Override
            public String getDesc() {
                return desc;
            }

            @Override
            public void setDesc(String desc) {
                this.desc = desc;
            }

            @Override
            public String getPublishedAt() {
                return publishedAt;
            }

            @Override
            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            @Override
            public String getType() {
                return type;
            }

            @Override
            public void setType(String type) {
                this.type = type;
            }

            @Override
            public String getUrl() {
                return url;
            }

            @Override
            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public boolean isUsed() {
                return used;
            }

            @Override
            public void setUsed(boolean used) {
                this.used = used;
            }

            @Override
            public String getWho() {
                return who;
            }

            @Override
            public void setWho(String who) {
                this.who = who;
            }

            @Override
            public String getSource() {
                return source;
            }

            @Override
            public void setSource(String source) {
                this.source = source;
            }

            @Override
            public List<String> getImages() {
                return images;
            }

            @Override
            public void setImages(List<String> images) {
                this.images = images;
            }

            @Override
            public String toString() {
                return "AndroidBean{" +
                        "_id='" + _id + '\'' +
                        ", createdAt='" + createdAt + '\'' +
                        ", desc='" + desc + '\'' +
                        ", publishedAt='" + publishedAt + '\'' +
                        ", source='" + source + '\'' +
                        ", type='" + type + '\'' +
                        ", url='" + url + '\'' +
                        ", used=" + used +
                        ", who='" + who + '\'' +
                        ", images=" + images +
                        '}';
            }
        }

        public static class 休息视频Bean implements com.gogh.afternoontea.entity.gank.BaseEntity {

            private String _id;
            private String createdAt;
            private String desc;
            private String publishedAt;
            private String source;
            private String type;
            private String url;
            private boolean used;
            private String who;
            private List<String> images;

            public 休息视频Bean() {
            }

            @Override
            public String get_id() {
                return _id;
            }

            @Override
            public void set_id(String _id) {
                this._id = _id;
            }

            @Override
            public String getCreatedAt() {
                return createdAt;
            }

            @Override
            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            @Override
            public String getDesc() {
                return desc;
            }

            @Override
            public void setDesc(String desc) {
                this.desc = desc;
            }

            @Override
            public String getPublishedAt() {
                return publishedAt;
            }

            @Override
            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            @Override
            public String getType() {
                return type;
            }

            @Override
            public void setType(String type) {
                this.type = type;
            }

            @Override
            public String getUrl() {
                return url;
            }

            @Override
            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public boolean isUsed() {
                return used;
            }

            @Override
            public void setUsed(boolean used) {
                this.used = used;
            }

            @Override
            public String getWho() {
                return who;
            }

            @Override
            public void setWho(String who) {
                this.who = who;
            }

            @Override
            public String getSource() {
                return source;
            }

            @Override
            public void setSource(String source) {
                this.source = source;
            }

            @Override
            public List<String> getImages() {
                return images;
            }

            @Override
            public void setImages(List<String> images) {
                this.images = images;
            }

            @Override
            public String toString() {
                return "AndroidBean{" +
                        "_id='" + _id + '\'' +
                        ", createdAt='" + createdAt + '\'' +
                        ", desc='" + desc + '\'' +
                        ", publishedAt='" + publishedAt + '\'' +
                        ", source='" + source + '\'' +
                        ", type='" + type + '\'' +
                        ", url='" + url + '\'' +
                        ", used=" + used +
                        ", who='" + who + '\'' +
                        ", images=" + images +
                        '}';
            }
        }

        public static class 拓展资源Bean implements com.gogh.afternoontea.entity.gank.BaseEntity {

            private String _id;
            private String createdAt;
            private String desc;
            private String publishedAt;
            private String source;
            private String type;
            private String url;
            private boolean used;
            private String who;
            private List<String> images;

            public 拓展资源Bean() {
            }

            @Override
            public String get_id() {
                return _id;
            }

            @Override
            public void set_id(String _id) {
                this._id = _id;
            }

            @Override
            public String getCreatedAt() {
                return createdAt;
            }

            @Override
            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            @Override
            public String getDesc() {
                return desc;
            }

            @Override
            public void setDesc(String desc) {
                this.desc = desc;
            }

            @Override
            public String getPublishedAt() {
                return publishedAt;
            }

            @Override
            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            @Override
            public String getType() {
                return type;
            }

            @Override
            public void setType(String type) {
                this.type = type;
            }

            @Override
            public String getUrl() {
                return url;
            }

            @Override
            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public boolean isUsed() {
                return used;
            }

            @Override
            public void setUsed(boolean used) {
                this.used = used;
            }

            @Override
            public String getWho() {
                return who;
            }

            @Override
            public void setWho(String who) {
                this.who = who;
            }

            @Override
            public String getSource() {
                return source;
            }

            @Override
            public void setSource(String source) {
                this.source = source;
            }

            @Override
            public List<String> getImages() {
                return images;
            }

            @Override
            public void setImages(List<String> images) {
                this.images = images;
            }

            @Override
            public String toString() {
                return "AndroidBean{" +
                        "_id='" + _id + '\'' +
                        ", createdAt='" + createdAt + '\'' +
                        ", desc='" + desc + '\'' +
                        ", publishedAt='" + publishedAt + '\'' +
                        ", source='" + source + '\'' +
                        ", type='" + type + '\'' +
                        ", url='" + url + '\'' +
                        ", used=" + used +
                        ", who='" + who + '\'' +
                        ", images=" + images +
                        '}';
            }

        }

        public static class 瞎推荐Bean implements com.gogh.afternoontea.entity.gank.BaseEntity {

            private String _id;
            private String createdAt;
            private String desc;
            private String publishedAt;
            private String source;
            private String type;
            private String url;
            private boolean used;
            private String who;
            private List<String> images;

            public 瞎推荐Bean() {
            }

            @Override
            public String get_id() {
                return _id;
            }

            @Override
            public void set_id(String _id) {
                this._id = _id;
            }

            @Override
            public String getCreatedAt() {
                return createdAt;
            }

            @Override
            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            @Override
            public String getDesc() {
                return desc;
            }

            @Override
            public void setDesc(String desc) {
                this.desc = desc;
            }

            @Override
            public String getPublishedAt() {
                return publishedAt;
            }

            @Override
            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            @Override
            public String getType() {
                return type;
            }

            @Override
            public void setType(String type) {
                this.type = type;
            }

            @Override
            public String getUrl() {
                return url;
            }

            @Override
            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public boolean isUsed() {
                return used;
            }

            @Override
            public void setUsed(boolean used) {
                this.used = used;
            }

            @Override
            public String getWho() {
                return who;
            }

            @Override
            public void setWho(String who) {
                this.who = who;
            }

            @Override
            public String getSource() {
                return source;
            }

            @Override
            public void setSource(String source) {
                this.source = source;
            }

            @Override
            public List<String> getImages() {
                return images;
            }

            @Override
            public void setImages(List<String> images) {
                this.images = images;
            }

            @Override
            public String toString() {
                return "AndroidBean{" +
                        "_id='" + _id + '\'' +
                        ", createdAt='" + createdAt + '\'' +
                        ", desc='" + desc + '\'' +
                        ", publishedAt='" + publishedAt + '\'' +
                        ", source='" + source + '\'' +
                        ", type='" + type + '\'' +
                        ", url='" + url + '\'' +
                        ", used=" + used +
                        ", who='" + who + '\'' +
                        ", images=" + images +
                        '}';
            }

        }

        public static class 福利Bean implements com.gogh.afternoontea.entity.gank.BaseEntity {

            private String _id;
            private String createdAt;
            private String desc;
            private String publishedAt;
            private String source;
            private String type;
            private String url;
            private boolean used;
            private String who;
            private List<String> images;

            public 福利Bean() {
            }

            @Override
            public String get_id() {
                return _id;
            }

            @Override
            public void set_id(String _id) {
                this._id = _id;
            }

            @Override
            public String getCreatedAt() {
                return createdAt;
            }

            @Override
            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            @Override
            public String getDesc() {
                return desc;
            }

            @Override
            public void setDesc(String desc) {
                this.desc = desc;
            }

            @Override
            public String getPublishedAt() {
                return publishedAt;
            }

            @Override
            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            @Override
            public String getType() {
                return type;
            }

            @Override
            public void setType(String type) {
                this.type = type;
            }

            @Override
            public String getUrl() {
                return url;
            }

            @Override
            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public boolean isUsed() {
                return used;
            }

            @Override
            public void setUsed(boolean used) {
                this.used = used;
            }

            @Override
            public String getWho() {
                return who;
            }

            @Override
            public void setWho(String who) {
                this.who = who;
            }

            @Override
            public String getSource() {
                return source;
            }

            @Override
            public void setSource(String source) {
                this.source = source;
            }

            @Override
            public List<String> getImages() {
                return images;
            }

            @Override
            public void setImages(List<String> images) {
                this.images = images;
            }

            @Override
            public String toString() {
                return "AndroidBean{" +
                        "_id='" + _id + '\'' +
                        ", createdAt='" + createdAt + '\'' +
                        ", desc='" + desc + '\'' +
                        ", publishedAt='" + publishedAt + '\'' +
                        ", source='" + source + '\'' +
                        ", type='" + type + '\'' +
                        ", url='" + url + '\'' +
                        ", used=" + used +
                        ", who='" + who + '\'' +
                        ", images=" + images +
                        '}';
            }
        }

    }

}