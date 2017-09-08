/**
 * @Title AppConstants.java
 * @Package com.hiveview.cloudscreen.video
 * @author haozening
 * @date 2014年9月13日 下午2:59:56
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.common;

/**
 * 应用常量
 *
 * @author haozening
 * @ClassName AppConstants
 * @Description
 * @date 2014年9月13日 下午2:59:56
 */
public interface AppConstants {
    /**
     * apk包名
     */
    String APK_PACKAGE_NAME = "com.hiveview.cloudscreen.vipvideo";
    /**
     * Intent附加参数，频道ID
     */
    String LIST_CHANNEL_ID = "channel_id";
    /**
     * Intent附加参数，频道类型
     */
    String LIST_CHANNEL_TYPE = "channel_type";
    /**
     * Intent附加参数，频道名
     */
    String LIST_TITLE = "list_title";
    /**
     *
     */
    String LIST_HOTWORD_ID = "list_hotword_id";
    /**
     * Intent附加参数，和播放器进行交互传递的AlbumEntity
     */
    String EXTRA_ALBUM_ENTITY = "com.hiveview.tv.FilmNewEntity";
    /**
     * Intent附加参数，和播放器进行交互传递的VideoEntity
     */
    String EXTRA_VIDEO_ENTITY = "com.hiveview.tv.VideoNewEntity";
    /**
     * Intent附加参数，和播放器交互传递的是否是从剧集传递过来的数据
     */
    String EXTRA_IS_FROM_EPISODE = "com.hiveview.tv.isFromEpisode";
    /**
     * Intent附加参数，是否是从推荐位进去的
     */
    String EXTRA_IS_RECOMMEND = "com.hiveview.cloudscreen.video.IsRecommend";
    /**
     * Intent附加参数，是否是从试看按钮进去的
     */
    String EXTRA_IS_FROM_TRYSEE = "com.hiveview.tv.isFromTrySeeButton";
    /**
     * Intent附加参数，是否是从讯飞进来的
     */
    String EXTRA_IS_FROM_IFLYTEK = "com.hiveview.cloudscreen.video.IsFromIflytek";
    /**
     * Intent附加参数，是否需要显示点播的背景图
     */
    String EXTRA_SHOULD_SHOW_OUTSID_BG = "com.hiveview.cloudscreen.vipvideo.ShouldShowBackground";
    /**
     * Intent附加参数，是否是从记录进去的
     */
    String EXTRA_IS_RECORD = "com.hiveview.tv.IsRecord";
    /**
     * Intent附加参数，是否按vip播放按钮进入
     */
    String EXTRA_IS_FROM_BTN = "com.hiveview.tv.isFromPlayButton";
    /**
     * Intent附加参数 ,代表4k极清
     */
    String EXTRA_IS_FROM_VIPPLAY = "com.hiveview.tv.isFromVipPlayButton";
    /**
     * Intent附加参数，是否只有ID
     */
    String EXTRA_IS_FROM_ONLY_ID = "com.hiveview.tv.IsOnlyId";
    /**
     * Intent附加参数，传递videoset_id
     */
    String EXTRA_VIDEOSET_ID = "com.hiveview.tv.VideosetId";
    /**
     * Intent附加参数，传递video_id
     */
    String EXTRA_VIDEO_ID = "com.hiveview.tv.VideoId";
    /**
     * Intent附加参数，传递SubjectEntity
     */
    String EXTRA_SUBJECT_ENTITY = "com.hiveview.tv.SubjectEntity";
    /**
     * Intent附加参数，传递Cid
     */
    String EXTRA_CID = "com.hiveview.tv.Cid";
    /**
     * Intent附加参数，网络直播id
     */
    String EXTRA_DLIVE_ID = "com.hiveview.cloudscreen.player.dliveId";

//	String ACTION_PLAYER = "com.hiveview.cloudscreen.action.QIYI_PLAYER";

    /**
     * 一键爽意图
     */
    String INTENT_CLEAR = "com.hiveview.cloudscreen.onekeyclear.detail";

    /**
     * 通知用户中心刷新数据的action
     */
    String ACTION_REFRESH_USER_INFO = "com.hiveview.cloudscreen.user.REFRESH_VIP_USERINFO";
    /**
     * 通知用户中心刷新数据的action（打开大麦影视就通知）
     */
    String ACTION_REFRESH_USER_INFO_ONCE = "com.hiveview.cloudscreen.user.REFRESH_VIP_INFO_ONCE";

    /**
     * 影片详情页用户点击来源（列表）
     */
    String ACTION_LIST_INFO = "com.hiveview.tv.list";

    /**
     * 影片详情页用户点击来源（专辑）
     */
    String ACTION_ALBUM_INFO = "com.hiveview.tv.album";

    /**
     * 打开来源
     * 1 apk首页  2 专题 3 收藏 4 历史记录 5搜索  6列表 7 相关推荐
     */
    String EXTRA_SOURCE = "com.hiveview.cloudscreen.source";

    /**
     * 专题id
     */
    String EXTRA_SUBJECT_ID = "com.hiveview.cloudscreem.subjectid";

    /**
     * 专题name
     */
    String EXTRA_SUBJECT_NAME = "com.hiveview.cloudscreem.subjectname";

    /**
     * 专题name
     */
    String EXTRA_SUBJECT_BACKGROUND = "com.hiveview.cloudscreem.subjectbackground";

    /**
     * 活动id
     */
    String EXTRA_ACTION_ID = "com.hiveview.cloudscreem.actionid";

    /**
     * home见广播
     */
    String REMOTE_ACTION = "com.hiveview.cloudscreen.Action.HOME_CODE";
    /**
     * 活动apk启动参数，活动id
     */
    String ACTIVITY_DETAIL_ACTIVITY_ID = "activityId";

    String KEEP_BACKGROUND = "com.hiveview.cloudscreen.service.KEEP_BACKGROUND_SERVICE";

    /**
     * intent action 跳转我的影片
     */
    String INTENT_FILM_LIST = "com.hiveview.cloudscreen.paycenter.FILM_LIST";
}
