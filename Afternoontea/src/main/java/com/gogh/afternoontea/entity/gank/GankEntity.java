package com.gogh.afternoontea.entity.gank;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/27/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/27/2016 do fisrt create. </li>
 */
public class GankEntity implements Serializable {

    private static final long serialVersionUID = -8006737060774872812L;

    /**
     * error : false
     * results : [{"_id":"5861d017421aa97240ef9f41","createdAt":"2016-12-27T10:21:11.590Z","desc":"macOS 10.12 Picture-in-Picture 快速实现","images":["http://img.gank.io/1d556c90-396c-4dae-a3d3-5cb3db7fab97"],"publishedAt":"2016-12-27T12:06:15.638Z","source":"chrome","type":"iOS","url":"https://github.com/insidegui/PIPContainer","used":true,"who":"代码家"},{"_id":"586080b6421aa97240ef9f32","createdAt":"2016-12-26T10:30:14.823Z","desc":"致力于提高项目 UI 开发效率的解决方案","publishedAt":"2016-12-26T11:40:05.242Z","source":"chrome","type":"iOS","url":"http://qmuiteam.com/ios/page/index.html","used":true,"who":"代码家"},{"_id":"5855392b421aa97237bca8bd","createdAt":"2016-12-17T21:10:03.126Z","desc":"史上最详细的iOS难点之Runtime运行时机制","images":["http://img.gank.io/25c9fcc7-c907-4d03-9f96-1fb100b53cac"],"publishedAt":"2016-12-23T11:41:19.908Z","source":"web","type":"iOS","url":"http://www.imlifengfeng.com/blog/?p=316","used":true,"who":"李峰峰"},{"_id":"585c922c421aa97240ef9f23","createdAt":"2016-12-23T10:55:40.179Z","desc":"Swift 实现的打字机效果","images":["http://img.gank.io/b99f194f-b337-467b-b637-9dc5529fbae8"],"publishedAt":"2016-12-23T11:41:19.908Z","source":"chrome","type":"iOS","url":"https://github.com/wibosco/GhostTypewriter","used":true,"who":"代码家"},{"_id":"585b3753421aa97240ef9f16","createdAt":"2016-12-22T10:15:47.813Z","desc":"iOS 漂亮的 Tip 效果组件","images":["http://img.gank.io/f7bed457-eba6-4e64-9bb8-11caf42dc3af"],"publishedAt":"2016-12-22T11:34:37.39Z","source":"chrome","type":"iOS","url":"https://github.com/nitishmakhija/EasyTipView","used":true,"who":"代码家"},{"_id":"585b3774421aa97237bca8e5","createdAt":"2016-12-22T10:16:20.19Z","desc":"iOS 密码效果组件","images":["http://img.gank.io/b96661c5-ecdd-4137-a7b5-bd840b03155d"],"publishedAt":"2016-12-22T11:34:37.39Z","source":"chrome","type":"iOS","url":"https://github.com/eddy-lau/UXPasscodeField","used":true,"who":"代码家"},{"_id":"585b378d421aa9723d29b989","createdAt":"2016-12-22T10:16:45.257Z","desc":"打分效果组件","publishedAt":"2016-12-22T11:34:37.39Z","source":"chrome","type":"iOS","url":"https://github.com/titman/LCStarRatingView","used":true,"who":"tit"},{"_id":"5858a810421aa9723a5a7796","createdAt":"2016-12-20T11:40:00.930Z","desc":"Swift 花式输出 String 效果。","images":["http://img.gank.io/0013a338-fc59-4644-9577-bc5a80e4b7f8"],"publishedAt":"2016-12-20T11:48:13.616Z","source":"chrome","type":"iOS","url":"https://github.com/malcommac/SwiftRichString","used":true,"who":"代码家"},{"_id":"5858a870421aa97240ef9f01","createdAt":"2016-12-20T11:41:36.412Z","desc":"Swift Sql database 抽象层","publishedAt":"2016-12-20T11:48:13.616Z","source":"chrome","type":"iOS","url":"https://github.com/IBM-Swift/Swift-Kuery","used":true,"who":"代码家"},{"_id":"585582ab421aa9723d29b970","createdAt":"2016-12-18T02:23:39.454Z","desc":"干货：最详细的iOS难点之CoreData讲解，附实例源码","images":["http://img.gank.io/9d0aea5b-d645-49bc-8395-15d9f20c24ad","http://img.gank.io/a639a461-96c3-44f9-9c70-b1c4dbeafe2c"],"publishedAt":"2016-12-19T11:57:16.232Z","source":"chrome","type":"iOS","url":"http://www.imlifengfeng.com/blog/?p=325","used":true,"who":"feng"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public GankEntity() {
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }


    public static class ResultsBean implements Serializable{
        private static final long serialVersionUID = 6772674961097610569L;
        /**
         * _id : 5861d017421aa97240ef9f41
         * createdAt : 2016-12-27T10:21:11.590Z
         * desc : macOS 10.12 Picture-in-Picture 快速实现
         * images : ["http://img.gank.io/1d556c90-396c-4dae-a3d3-5cb3db7fab97"]
         * publishedAt : 2016-12-27T12:06:15.638Z
         * source : chrome
         * type : iOS
         * url : https://github.com/insidegui/PIPContainer
         * used : true
         * who : 代码家
         */

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

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        @Override
        public String toString() {
            return "ResultsBean{" +
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

    @Override
    public String toString() {
        return "GankEntity{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
