package com.hiveview.cloudscreen.vipvideo.service;

import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;

/**
 * Created by Spr_ypt on 2015/10/12.
 */
public interface ApiConstants {
    float APP_VERSION = 1.0f;
    // 新云屏 VIP正式域名
    String CLOUDSCREEN_DOMAIN = "http://api.bc.pthv.gitv.tv";
    //    String CLOUDSCREEN_DOMAIN = "http://211.103.138.119:8117";
    //VIP活动接口正式域名
    String CLOUDSCREEN_ACTIVITY = "http://api.bc.pthv.gitv.tv";
    //相关推荐域名
    String RECOMMEND_DOMAIN = "http://api.live.pthv.gitv.tv";
    //    String RECOMMEND_DOMAIN = "http://211.103.138.119:8119";
    //收藏同步接口
    String COLLECT_DOMAIN = "http://passport.domybox.com";
    //获取设备码接口
    String DEVICE_DOMAIN = "http://deviceapi.pthv.gitv.tv";
    //统计
    String STATISTICS_URL = "http://data.pthv.gitv.tv/data_collect/data/LogForV2.json";

    //国外域名
    String CLOUDSCREEN_DOMAIN_OS = "http://api.bc.global.domybox.com";
    String CLOUDSCREEN_ACTIVITY_OS = "http://api.bc.global.domybox.com";
    String RECOMMEND_DOMAIN_OS = "http://api.bc.global.domybox.com";
    String COLLECT_DOMAIN_OS = "http://passport.global.domybox.com";
    String DEVICE_DOMAIN_OS = "http://api.device.global.domybox.com";
    String STATISTICS_URL_OS = "http://data.global.domybox.com/data_collect/data/LogForV2.json";


    class UrlMaker {
        private static final String TAG = "UrlMaker";

        public static String getUrl(String url) {
            return ResourceProvider.getInstance().getCloudScreenDomain() + url;
        }

        public static String getActivityUrl(String url) {
            return ResourceProvider.getInstance().getActivityDomain() + url;
        }

        public static String getRecommendUrl(String url) {
            return ResourceProvider.getInstance().getRecommendDomain() + url;
        }

        public static String getCollectUrl(String url) {
            return ResourceProvider.getInstance().getCollectDomain() + url;
        }

        public static String getDeviceUrl(String url) {
            return ResourceProvider.getInstance().getDeviceDomain() + url;
        }

        public static String getStatisticsUrl() {
            return ResourceProvider.getInstance().getStatisticsDomain();

        }

    }


    /**
     * 电影列表接口
     */
    String FILM_LIST = "/api/open/special/video/getVideoSetList/%s-%s-%s-%s-%s-%s-" + APP_VERSION + ".json";
    /**
     * 点播专题详情接口
     */
    String SUBJECT_DETAIL = "/api/open/vip/subject/getSubjectContent/%s-" + APP_VERSION + ".json";

    /**
     * 3.2
     * 点播专题列表接口
     */
    String SUBJECT_LIST = "/api/open/vip/subject/getSubjectList/%s-%s-%s-%s-" + APP_VERSION + ".json";

    /**
     * 获取频道列表接口
     */
    String HIVEVIEW_API_GET_CHANNEL_LIST = "/api/open/special/channel/getChannelList/%s-%s-" + APP_VERSION + ".json";
    /**
     * 获取热词列表接口
     */
    String HOTWORD_LIST = "/api/open/vip/first/hotWords-%s-%s-%s.json";
    /**
     * 根据热词获取内容
     */
    String HOTWORD_CONTENT = "/api/open/vip/first/getHotContent-%s-%s-%s-%s-%s-%s.json";
    /**
     * 获取推荐数据的接口
     */
    String RECOMMEND_LIST = "/api/open/vip/videoSetRecom/getVideoSetRecomList/%s-%s-%s-%s.json";

    /**
     * 电影详情中的相关推荐接口,暂无正式域名
     */
    String DETAIL_RECOMMEND_LIST = "/api/recommend/getRecommend/%s/%s.json";

    /**
     * 剧集列表接口
     */
    String VIDEO_LIST = "/api/open/special/video/videoList/%s-%s-%s-%s-%s-" + APP_VERSION + ".json";

    /**
     * vip特权接口
     */
    String PREROGATIVE_LIST = "/api/open/vip/prerogative/getPrerogativeList/-" + APP_VERSION + ".json";

    /**
     * 电影详情接口
     */

    String FILM_DETAIL = "/api/open/special/video/videoSetDetail-%s-%s-%s.json";


    /**
     * 常规活动接口
     */
    String ACTIVITY_LIST = "/api/open/vip/activity/getActivityList/%s-%s-%s-%s-" + APP_VERSION + ".json";

    /**
     * 3.2  获取计费包
     */
    String VIPGOODS = "/api/open/special/vipGoods/getVipGoods/%s-" + APP_VERSION + ".json";
    /**
     * 客户端生成订单接口
     */
    String SAVE_ORDER = "/api/open/vip/order/saveOrder/%s-%s-%s-%s-%s-%s-" + APP_VERSION + ".json";
    /**
     * 3.1.1 获取免费包信息接口
     */
    String ACTIVITY_FREE_LIST = "/api/open/vip/activity/getActivityFreeList_V2/" + APP_VERSION + ".json";
    /**
     * 3.2 获取免费包信息接口
     */
    String GET_ACTIVITY = "/api/open/special/activity/getActivity/%s-%s-" + APP_VERSION + ".json";

    /**
     * 3.2 免费领取接口
     */
    String FREE_ACTIVTY_POST = "/api/open/special/vipOrder/saveFreeOrder.json";

    String GET_MESSAGES = "/api/open/vip/first/message-%s.json";

    /**
     * 3.2 二维码图片接口
     */
    String API_POST_PUSH_CODE = "http://api.multi.pthv.gitv.tv/wx/message.json";

    /**
     * 获取设备码
     */
    String GET_DEVICE_CODE = "/device/code.json?mac=%s&sn=%s";

    /**
     * 获取用户收藏信息
     */
    String COLLECT_GET_DATA = "/api/user/getUserCollects.json";
    /**
     * 添加用户收藏
     */
    String COLLECT_ADD_DATA = "/api/user/addUserCollect.json";
    /**
     * 删除用户收藏
     */
    String COLLECT_DELETE_DATA = "/api/user/deleteUserCollect.json";

    /**
     * 获取会员提醒策略
     * TODO 修改为正式域名
     */
    String GET_REMIND_STRATEGY = "/api/open/strategy/getRemindStrategy.json";

    /**
     * 直播节目鉴权接口，是否已购买
     */
    String GET_LIVE_CHANNEL_STATUS = "/api/open/vip/live/getLiveChannelListStatus-%s-%s-%s-[%s]-%s.json?random=%s&sign=%s";
    /**
     * 影片详情节目单是否已购买
     */
    String GET_MOVIE_DETAILS_STATUS = "/api/open/special/goodsOrder/confirmEpg/%s-%s-%s-%s-%s-" + APP_VERSION + ".json";
    /**
     * 直播详情接口
     */
    String GET_LIVE_CHANNEL_DETAIL = "/api/open/vip/vipLive/getApiVipLiveList/%s-%s-%s-%s-%s.json";
    /**
     * 根据产品ID获取商品信息
     */
    String GET_PRODUCTINFO = "/api/open/vip/first/getProductInfo-%s-%s-%s-%s.json";
    /**
     * 获取服务器时间
     */
    String GET_SERVICETIME = "/api/open/vip/first/getTime.json";

}
