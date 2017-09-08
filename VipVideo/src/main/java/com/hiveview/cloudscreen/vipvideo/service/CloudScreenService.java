package com.hiveview.cloudscreen.vipvideo.service;

import android.content.Context;

import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.service.entity.CollectEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.FilmAuthenticationEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.GetActivityEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.LiveDetailEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.LiveStatusEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ProductInfoEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.RemindStrategyEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ServiceTimeEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.service.parser.ActivityListParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.ChannelListParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.CollectAddPostParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.CollectDeletePostParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.CollectGetPostParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.DetailRecommendListParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.DeviceCodeParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.EpisodeListParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.FilmParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.FreeActivityParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.FreeActivityPostParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.GetMessageParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.GetReminStrategyParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.GetServiceTimeParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.HotWordListParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.HotWordsContentParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.LiveDetailGetParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.LiveProductInfoParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.LiveStatusParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.MovieDetailAuthenicationParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.PrerogativeListParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.PricePkgListParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.ProductInfoParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.QuickMarkPostParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.RecommendListParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.SaveOrderParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.SubjectDetailParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.SubjectListParser;
import com.hiveview.cloudscreen.vipvideo.service.parser.VideoDetailParser;
import com.hiveview.cloudscreen.vipvideo.service.request.ActivityListRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.ChannelListRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.CollectAddPostRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.CollectDeletePostRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.CollectGetPostRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.DetailRecommendListRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.DeviceCodeRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.EpisodeListRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.FilmRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.FreeActivityPostRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.GetActivityRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.GetMessagesRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.GetProductInfoRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.GetRemindStrategyRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.GetServiceTimeRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.HotWordContentRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.HotWordListRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.LiveDetailGetRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.LiveStatusRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.MoiveDetailAuthenticationRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.PrerogativeListRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.PricePkgPostRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.QuickMarkPostRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.RecommendListRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.SaveOrderRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.SubjectDetailRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.SubjectListRequest;
import com.hiveview.cloudscreen.vipvideo.service.request.VideoDetailRequest;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;

/**
 * Created by Administrator on 2015/10/12.
 */
public class CloudScreenService extends BaseService {
    public static CloudScreenService dataService = null;

    public CloudScreenService(Context context) {
        super(context);
    }


    public static void init(Context context) {
        dataService = new CloudScreenService(context);
    }


    public static CloudScreenService getInstance() {
        return dataService;
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/17
     * @Description 获取影片列表
     */
    public void getFilm(OnRequestResultListener filmListener, int channelId, int channelType, int pageSize, int pageNumber) {
        get(new FilmRequest(channelId, channelType, pageSize, pageNumber), new FilmParser(), filmListener);
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/17
     * @Description 获取专辑详情
     */
    public void getSubjectDetailInfo(OnRequestResultListener subjectDataResponseListener, int subject_id) {
        get(new SubjectDetailRequest(subject_id), new SubjectDetailParser(), subjectDataResponseListener);
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/17
     * @Description 获取专辑列表
     */
    public void getSubjectListInfo(OnRequestResultListener subjectListListener, int templateId, String apkBagName, int pageNo, int pageSize) {
        get(new SubjectListRequest(templateId, apkBagName, pageNo, pageSize), new SubjectListParser(), subjectListListener);
    }

    /**
     * 剧集列表接口
     * wangbei 2015/11/18
     *
     * @param episodeListener
     * @param videoset_id     爱奇艺专辑id
     * @param cid             频道ID
     * @param page_number
     * @param page_size
     */
    public void getEpisodeList(OnRequestResultListener episodeListener, Integer videoset_id, Integer cid, String stype, Integer page_number,
                               Integer page_size) {
        get(new EpisodeListRequest(videoset_id, cid, stype, page_number, page_size), new EpisodeListParser(), episodeListener);
    }

    /**
     * 计费包接口请求
     *
     * @param resultListener
     */
    public void getPricePkgList(OnRequestResultListener resultListener, int templetId) {
        get(new PricePkgPostRequest(templetId), new PricePkgListParser(), resultListener);
    }

    /**
     * wangbei 2015/11/18
     * 特权接口
     *
     * @param prerogativeList
     */
    public void getPrerogativeList(OnRequestResultListener prerogativeList) {
        get(new PrerogativeListRequest(), new PrerogativeListParser(), prerogativeList);
    }

    /**
     * 详情页数据接口
     * wangbei 2015/11/18
     *
     * @param listener
     * @param videoset_id
     */
    public void getFilmDetail(OnRequestResultListener listener, int temptedId, int videoset_id) {
        get(new VideoDetailRequest(temptedId, videoset_id), new VideoDetailParser(), listener);
    }

    /**
     * 常规活动接口
     * wangbei 2015/11/18
     *
     * @param listListener
     * @param isVip
     * @param pageNo
     * @param pageSize
     */
    public void getActivityList(OnRequestResultListener listListener, Integer isVip, Integer actionId, Integer pageNo, Integer pageSize) {
        get(new ActivityListRequest(isVip, actionId, pageNo, pageSize), new ActivityListParser(), listListener);
    }

    /**
     * 相关推荐接口
     *
     * @param listener
     * @param id
     */
    public void getFilmDetailRecommend(OnRequestResultListener listener, int id, int cid) {
        get(new DetailRecommendListRequest(id, cid), new DetailRecommendListParser(), listener);
    }


    /**
     * @Author Spr_ypt
     * @Date 2015/11/17
     * @Description 获取频道列表
     */
    public void getChannelList(OnRequestResultListener firstClassListener, int template, String apkBagName) {
        get(new ChannelListRequest(template, apkBagName), new ChannelListParser(), firstClassListener);
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/17
     * @Description 获取热词列表
     */
    public void getHotWordsList(OnRequestResultListener hotWordListListener, int cid) {
        get(new HotWordListRequest(cid), new HotWordListParser(), hotWordListListener);
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/23
     * @Description 获取热词对应的具体影片内容
     */
    public void getHotWordsContent(OnRequestResultListener listener, int wordId, int chnId, int pageNo, int pageSize) {
        get(new HotWordContentRequest(wordId, chnId, pageNo, pageSize), new HotWordsContentParser(), listener);
    }


    /**
     * @Author Spr_ypt
     * @Date 2015/11/20
     * @Description 获取推荐位数据的接口
     */
    public void getLaunchRecommendList(OnRequestResultListener listener, int pageNo, int pageSize) {
        if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
            get(new RecommendListRequest(1, pageNo, pageSize), new RecommendListParser(), listener);
        } else {
            get(new RecommendListRequest(0, pageNo, pageSize), new RecommendListParser(), listener);
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/30  3.2已废弃
     * @Description 获取订单信息
     */
    public void getSaveOrder(OnRequestResultListener listener, int orderType, int userId, DeviceInfoUtil.DeviceInfo info, String duration) {
        get(new SaveOrderRequest(orderType, userId, info, duration), new SaveOrderParser(), listener);
    }

    /**
     * @param listener
     * @param templateId
     * @param apkPkgName
     * @Author wangbei
     * @Date 2016/10/31
     * @Description 3.2 获取免费活动信息
     */
    public void getActivity(OnRequestResultListener listener, int templateId, String apkPkgName) {
        get(new GetActivityRequest(templateId, apkPkgName), new FreeActivityParser(), listener);
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/12/29
     * @Description 发送免费活动订单
     */
    public void postActivityFreeOrder(OnRequestResultListener listener, GetActivityEntity entity, UserStateUtil.UserInfo userInfo, DeviceInfoUtil.DeviceInfo deviceInfo) {
        post(new FreeActivityPostRequest(entity, userInfo, deviceInfo), new FreeActivityPostParser(), listener);
    }

    /**
     * 请求后台生成二维码
     *
     * @param listener
     * @param userInfo
     * @param deviceInfo
     */
    public void postQuickMark(OnRequestResultListener listener, UserStateUtil.UserInfo userInfo, DeviceInfoUtil.DeviceInfo deviceInfo) {
        post(new QuickMarkPostRequest(userInfo, deviceInfo), new QuickMarkPostParser(), listener);
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/12/29
     * @Description 获取跑马灯信息
     */
    public void getMessageVipList(OnRequestResultListener listener) {
        get(new GetMessagesRequest(), new GetMessageParser(), listener);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/3/28
     * @Description 获取设备码
     */
    public void getDeviceCode(String mac, String sn, OnRequestResultListener<String> listener) {
        get(new DeviceCodeRequest(mac, sn), new DeviceCodeParser(), listener);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/19
     * @Description 获取收藏记录
     */
    public void postCollectGetData(String userId, int pageNo, int pageSize, OnRequestResultListener<ResultEntity<CollectEntity>> listener) {
        post(new CollectGetPostRequest(userId, pageNo, pageSize), new CollectGetPostParser(), listener);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/5/19
     * @Description 获取收藏记录
     */
    public void postCollectAddData(String userId, CollectEntity entity, OnRequestResultListener<ResultEntity> listener) {
        post(new CollectAddPostRequest(userId, entity), new CollectAddPostParser(), listener);
    }

    /**
     * @param collectId 传null为全部删除
     * @Author Spr_ypt
     * @Date 2016/5/19
     * @Description 删除收藏记录
     */
    public void postCollectDeleteData(String userId, Integer collectId, OnRequestResultListener<ResultEntity> listener) {
        post(new CollectDeletePostRequest(userId, collectId), new CollectDeletePostParser(), listener);

    }

    /**
     * @Author Spr_ypt
     * @Date 2016/11/3
     * @Description 获取会员到期提醒策略
     */
    public void getRemindStrategy(OnRequestResultListener<ResultEntity<RemindStrategyEntity>> listener) {
        get(new GetRemindStrategyRequest(), new GetReminStrategyParser(), listener);
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/11/3
     * @Description 直播鉴权
     */
    public void checkLiveStatus(String channelId, String dmID, OnRequestResultListener<ResultEntity<LiveStatusEntity>> listener) {
        DeviceInfoUtil.DeviceInfo info = DeviceInfoUtil.getInstance().getDeviceInfo(CloudScreenApplication.getInstance().getApplicationContext());
        get(new LiveStatusRequest(info.mac, info.sn, UserStateUtil.getInstance().getUserInfo().userId, channelId, dmID), new LiveStatusParser(), listener);
    }

    /**
     * @param epgIds
     * @param listener
     * @Description 影片详情鉴权
     */
    public void getFilmAuthentication(String epgIds, OnRequestResultListener<ResultEntity<FilmAuthenticationEntity>> listener) {
        DeviceInfoUtil.DeviceInfo info = DeviceInfoUtil.getInstance().getDeviceInfo(CloudScreenApplication.getInstance().getApplicationContext());
        get(new MoiveDetailAuthenticationRequest(info.templetId + "", UserStateUtil.getInstance().getUserInfo().userId, epgIds), new MovieDetailAuthenicationParser(), listener);
    }

    public void getLiveDetail(final String tvId, final OnRequestResultListener<ResultEntity<LiveDetailEntity>> listener) {
        get(new LiveDetailGetRequest(tvId), new LiveDetailGetParser(), new OnRequestResultListener<ResultEntity<LiveDetailEntity>>() {
            @Override
            public void onSucess(ResultEntity<LiveDetailEntity> liveDetailEntityResultEntity) {
                LiveDetailEntity entity = (LiveDetailEntity) liveDetailEntityResultEntity.getEntity();
                if ("2".equals(entity.getIsPay())) {
                    getProductInfo(tvId, "1".equals(entity.getSignalType()) ? 4 : 6, entity, listener);
                } else {
                    listener.onSucess(liveDetailEntityResultEntity);
                }
            }

            @Override
            public void onFail(Exception e) {
                listener.onFail(e);
            }

            @Override
            public void onParseFail(HiveviewException e) {
                listener.onParseFail(e);
            }
        });
    }


    /**
     * @Author Spr_ypt
     * @Date 2017/3/2
     * @Description 直播、轮播鉴权，合并解析
     */
    private void getProductInfo(String tvId, int contentType, LiveDetailEntity entity, OnRequestResultListener<ResultEntity<LiveDetailEntity>> listener) {
        DeviceInfoUtil.DeviceInfo info = DeviceInfoUtil.getInstance().getDeviceInfo(CloudScreenApplication.getInstance().getApplicationContext());
        get(new GetProductInfoRequest(info.templetId, Integer.parseInt(tvId), contentType), new LiveProductInfoParser(entity), listener);
    }

    /**
     * 根据产品ID获取商品信息
     *
     * @param videosetId
     * @param contentType 产品类型 1、点播，4、直播，6、轮播
     * @param listener
     */
    public void getProductInfo(int videosetId, int contentType, OnRequestResultListener<ResultEntity<ProductInfoEntity>> listener) {
        DeviceInfoUtil.DeviceInfo info = DeviceInfoUtil.getInstance().getDeviceInfo(CloudScreenApplication.getInstance().getApplicationContext());
        get(new GetProductInfoRequest(info.templetId, videosetId, contentType), new ProductInfoParser(), listener);
    }

    /**
     * 获取当前访问服务器时间
     */
    public void getServiceTime(OnRequestResultListener<ResultEntity<ServiceTimeEntity>> listener) {
        get(new GetServiceTimeRequest(), new GetServiceTimeParser(), listener);
    }
}
