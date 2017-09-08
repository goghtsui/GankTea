package com.hiveview.cloudscreen.vipvideo.common;

import android.annotation.SuppressLint;

import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResArea;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;

import java.util.HashMap;

public class VideoListInvoker {

    private static VideoListInvoker container = new VideoListInvoker();

    /**
     * 显示竖图（电影，电视剧，动漫）列表页Activity的Action
     */
    public static final String VIEDEO_VERTICAL_LIST_ACTION = "com.hiveview.cloudscreen.video.action.FILMLIST";

    /**
     * 显示横图（娱乐，体育，音乐，纪录片）列表页Activity的Action
     */
    public static final String VIEDEO_HORIZONTAL_LIST_ACTION = "com.hiveview.cloudscreen.video.action.VIDEOLIST";

    /**
     * 显示专题列表页Activity的Action
     */
    public static final String VIEDEO_SUBJECT_LIST_ACTION = "com.hiveview.cloudscreen.video.action.SUBJECTLIST";

    @SuppressLint("UseSparseArrays")
    private static HashMap<Integer, String> map = new HashMap<Integer, String>();

    public static VideoListInvoker getInstance() {

        if (map.size() == 0)
            initTips();

        return container;
    }

    public String getListActivityAction(int showType) {
        return map.get(showType);
    }

    private static void initTips() {
        map.put(ContentShowType.TYPE_SINGLE_VIDEO_DETAIL, VIEDEO_VERTICAL_LIST_ACTION);
        if (ResourceProvider.getInstance().getResArea() == ResArea.CH) {
            map.put(ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL, VIEDEO_VERTICAL_LIST_ACTION);
        } else {
            map.put(ContentShowType.TYPE_SINGLE_VIDEO_NO_DETAIL, VIEDEO_HORIZONTAL_LIST_ACTION);
        }
        map.put(ContentShowType.TYPE_MULTIPLE_VIDEO_DETAIL, VIEDEO_VERTICAL_LIST_ACTION);
        map.put(ContentShowType.TYPE_VARIETY_VIDEO_DETAIL, VIEDEO_VERTICAL_LIST_ACTION);
        map.put(ContentShowType.TYPE_SUBJECT_VIDEO_DETAIL, VIEDEO_SUBJECT_LIST_ACTION);
    }

}
