package com.hiveview.cloudscreen.vipvideo.common;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.VideoChannelEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.util.BitmapBlurUtil;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import com.hiveview.cloudscreen.vipvideo.util.VideoChannelUtils;
import com.hiveview.statistics.HSApi;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CloudScreenApplication extends Application {

    private static CloudScreenApplication instance;
    /**
     * 内存大小
     */
    private static final int MAX_MEMORY_CACHE_SIZE = 8 * 1024 * 1024;
    /**
     * 图片内存大小
     */
    private static final int MAX_DISK_CACHE_SIZE = 20 * 1024 * 1024;

    private static final String TAG = "CloudScreenApplication";

    public LRULimitedMemoryCache cache = new LRULimitedMemoryCache(MAX_MEMORY_CACHE_SIZE);

    public ImageLoader imageLoader;

    private HashMap<Integer, Integer> showTypeMap = null;

    private List<ShowTypeWather> wathers = new ArrayList<ShowTypeWather>();
    /**
     * 保存截取的高斯模糊图片，使用完必须及时回收
     */
    public Bitmap blurBitmap;

    /**
     * 埋点来源
     */
    public String source;
    public static HSApi api = null;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Logger.d(TAG, "CloudScreenApplication.onCreate");
        //初始化国内/海外资源提供器
        ResourceProvider.init();
        //启动保持后台运行服务
        Intent keepBackIntent = new Intent(AppConstants.KEEP_BACKGROUND);
        keepBackIntent.setPackage(getPackageName());
        startService(keepBackIntent);
        //加载网络请求服务
        CloudScreenService.init(this);
        //配置ImageLoader
        configImageLoader();
        //初始化设备信息
        DeviceInfoUtil.getInstance().initDeviceInfo(getApplicationContext());
        //初始化用户数据
        UserStateUtil.getInstance().notifyReFreshUserState(getApplicationContext());
        //取得showType类型对应表
        if (null == showTypeMap) {// 初始化视频类型和show_type的对应表
            showTypeMap = VideoChannelUtils.getShowTypeAndVideoTypeMap(this);
            if (showTypeMap == null || showTypeMap.size() == 0) {
                CloudScreenService.getInstance().getChannelList(listener, DeviceInfoUtil.getInstance().getDeviceInfo(this).templetId, AppConstants.APK_PACKAGE_NAME);//第二个参数为空表示请求所有类型的分类
            } else {
                notifyShowTypeWather();
            }
            // 发送启动本地server的广播
//            sendBroadcast(new Intent(this, BootReceiver.class));
            BitmapBlurUtil.getInstance().clearCache();
        }
        //初始化果仁apk的单例对象
        api = HSApi.getInstance(this);
    }

    public static HSApi getHSApi() {
        return api;
    }

    public static CloudScreenApplication getInstance() {
        return instance;
    }

    private static final int DISK_SIZE = 100 * 1024 * 1024;

    private int getMemorySize() {
        long size = Runtime.getRuntime().maxMemory();
        if (size == Long.MAX_VALUE) {
            Log.d(TAG, "image memory size : " + 48 * 1024 * 1024);
            return 48 * 1024 * 1024;
        } else {
            Log.d(TAG, "image memory size : " + (int) (size / 4));
            return (int) (size / 4);
        }
    }

    public void configImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()).diskCacheSize(DISK_SIZE).tasksProcessingOrder(QueueProcessingType.LIFO).memoryCacheSize(getMemorySize()).writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    public int getShowTypeByVideoTypeId(int videoSetTypeId) {
        return getShowTypeByVideoTypeId(videoSetTypeId, null);
    }


    /**
     * 根据视频的类型的ID,得到其对应的show_type字段，根据show_type字段可判断跳转的详情页和列表页
     *
     * @param videoSetTypeId 视频类型ID
     * @return
     * @Title: CloudScreenApplication
     * @author:陈丽晓
     * @Description: TODO
     */
    public int getShowTypeByVideoTypeId(int videoSetTypeId, ShowTypeWather wather) {
        if (null == showTypeMap || showTypeMap.size() <= 0) {
            showTypeMap = VideoChannelUtils.getShowTypeAndVideoTypeMap(this);
        }
        if (null != wather) {
            if (null == showTypeMap || showTypeMap.size() <= 0) {
                registerShowTypeWahter(wather);
            } else {
                removeShowTypeWather(wather);
            }
        }
        Log.d(TAG, "" + videoSetTypeId);
        try {
            Integer showType = showTypeMap.get(videoSetTypeId);
            showType = null != showType ? showType : 0;
            return showType;
        } catch (Exception e) {
            Log.e(TAG, "e=" + e.toString());
            return 0;
        }

    }

    /**
     * 获取频道列表监听
     */
    private OnRequestResultListener listener = new OnRequestResultListener<ResultEntity>() {
        @Override
        public void onSucess(ResultEntity entity) {
            HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
            for (Object obj : entity.getList()) {
                VideoChannelEntity e = (VideoChannelEntity) obj;
                map.put(e.getId(), getShowCategory(e));
            }
            showTypeMap = map;
            notifyShowTypeWather();
            Log.d(TAG, "showTypeMap==" + map.toString());
        }

        private int getShowCategory(VideoChannelEntity e) {
            if (e.getChannelType() == 10) {
                if (e.getIsSpecific() == 1) {
                    return ContentShowType.TYPE_SUBJECT_VIDEO_DETAIL;
                } else if (e.getIsSpecific() == 3) {
                    return ContentShowType.TYPE_VARIETY_VIDEO_DETAIL;
                } else if (e.getIsHasDetail() == 0) {
                    return ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL;
                } else {
                    if (e.getIsMultichip() == 1) {
                        return ContentShowType.TYPE_SINGLE_VIDEO_DETAIL;
                    } else {
                        return ContentShowType.TYPE_MULTIPLE_VIDEO_DETAIL;
                    }
                }
            } else if (e.getChannelType() == 9) {
                if (e.getIsSpecific() == 1) {
                    return ContentShowType.TYPE_SUBJECT_VIDEO_DETAIL;
                } else {
                    return ContentShowType.TYPE_SINGLE_VIDEO_DETAIL;
                }
            } else {
                return ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL;
            }
        }

        @Override
        public void onFail(Exception e) {
            notifyShowTypeWather();
        }

        @Override
        public void onParseFail(HiveviewException e) {
            notifyShowTypeWather();
        }
    };


    /**
     * 注册成为观察者
     *
     * @param wather
     */
    public synchronized void registerShowTypeWahter(ShowTypeWather wather) {
        wathers.add(wather);
    }

    /**
     * 移除反注册观察者
     *
     * @param wather
     */
    public synchronized void removeShowTypeWather(ShowTypeWather wather) {
        wathers.remove(wather);
    }

    /**
     * 通知观测者数据变化
     */
    private synchronized void notifyShowTypeWather() {
        List<ShowTypeWather> copy = new ArrayList<ShowTypeWather>(wathers);
        for (ShowTypeWather wather : copy) {
            wather.onShowTypeGet();
        }
    }

    /**
     * showType观察者接口
     */
    public interface ShowTypeWather {
        public void onShowTypeGet();
    }

}
