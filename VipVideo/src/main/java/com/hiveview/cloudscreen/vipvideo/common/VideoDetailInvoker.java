package com.hiveview.cloudscreen.vipvideo.common;

import android.annotation.SuppressLint;

import java.util.HashMap;

public class VideoDetailInvoker {

    /**
     * 详情页
     */
//    public static final String ACTION_DETAIL = "com.hiveview.cloudscreen.video.action.VIP_DETAIL";

    /**
     * 电影详情的页的Action
     */
    public static final String ACTION_FILMDETAIL = "com.hiveview.cloudscreen.video.action.DETAIL_FILM";

    /**
     * 电视剧、动漫详情页的Action
     */
    public static final String ACTION_TELEPLAYDETAIL = "com.hiveview.cloudscreen.video.action.DETAIL_EPISODE";

    /**
     * 综艺详情页的Action
     */
    public static final String ACTION_VARIETYDETAIL = "com.hiveview.cloudscreen.video.action.DETAIL_VARIETY";

    /**
     * 专题详情页的Action
     */
    public static final String ACTION_SUBJECTDETAIL = "com.hiveview.cloudscreen.video.action.SUBJECT_DETAIL";

    /**
     * 体育、娱乐、音乐、记录片直接播放的Action
     */
    public static final String ACTION_PLAYER = "com.hiveview.cloudscreen.action.QIYI_PLAYER";
    /**
     * 打开活动页面的action，因为没有对应频道，所以这里直接调用
     * 不需要调用{@link #getDetailActivityAction(Integer videoType)}方法获取
     */
    public static final String ACTION_ACTIVITY = "com.hiveview.cloudscreen.video.action.VIP_ACTIVITY_DETAIL";
    /**
     * 打开直播的action
     */
    public static final String ACTION_DLIVE = "com.hiveview.cloudscreen.video.action.DETAIL_DLIVE";
    /**
     * 打开活动apk的action
     */
    public static final String ACTION_APK = "com.hiveview.lotteryactivity.action.SoleActivity";
    /**
     * 打开热词的action
     */
    public static final String ACTION_HOT_WORD = "com.hiveview.cloudscreen.video.action.FILMLIST";
    /**
     * 我的影片界面action
     */
    public static final String ACTION_FILM_LIST = "com.hiveview.cloudscreen.paycenter.FILM_LIST";
    /**
     * 产品包列表action
     */
    public static final String ACTION_GOODS_LIST = "com.hiveview.cloudscreen.paycenter.GOODS_LIST";
    /**
     * 产片包详情action
     */
    public static final String ACTION_GOODS_PAG = "com.hiveview.cloudscreen.paycenter.RELATED_GOODS";


    private static VideoDetailInvoker container = new VideoDetailInvoker();
    @SuppressLint("UseSparseArrays")
    private static HashMap<Integer, String> map = new HashMap<Integer, String>();


    public static VideoDetailInvoker getInstance() {

        if (map.size() == 0) {
            initTips();
        }

        return container;
    }

    public String getDetailActivityAction(Integer videoType) {
        if (null != videoType) {
            // 把视频的类型的ID转换成show_type类型，通过show_type对应的Action可跳转到详情页
            int showType = CloudScreenApplication.getInstance().getShowTypeByVideoTypeId(videoType);
            return map.get(showType);
        } else {
            return map.get(0);
        }
    }

    private static void initTips() {
        map.put(ContentShowType.TYPE_SINGLE_VIDEO_DETAIL, ACTION_FILMDETAIL);// 电影
        map.put(ContentShowType.TYPE_MULTIPLE_VIDEO_DETAIL, ACTION_TELEPLAYDETAIL);// 电视剧,动漫
        map.put(ContentShowType.TYPE_VARIETY_VIDEO_DETAIL, ACTION_VARIETYDETAIL);// 综艺
        map.put(ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL, ACTION_PLAYER);// 体育，音乐，纪录片
        map.put(ContentShowType.TYPE_SUBJECT_VIDEO_DETAIL, ACTION_SUBJECTDETAIL);// 专题详情
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/11/7
     * @Description 推荐位内容类型
     */
    public interface ContentType {
        int TYPE_SUBJECT = 0;//专题类型
        int TYPE_VIDEO = 1;//专辑类型
        int TYPE_ACTIVITY = 2;//活动页面类型
        int TYPE_HOT_WORLD = 3;//热词类型
        int TYPE_DLIVE = 4;//直播类型
        int TYPE_APK = 5;//活动apk类型（暂时只有活动apk）
        int TYPE_CAROUSEL = 6;//轮播
        int TYPE_GOODS_PAG = 7;//商品包
        int TYPE_TAG = 8;//标签
    }

}
