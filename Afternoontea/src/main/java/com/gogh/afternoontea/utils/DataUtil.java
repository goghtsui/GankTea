package com.gogh.afternoontea.utils;

import com.gogh.afternoontea.entity.gank.GankEntity;
import com.gogh.afternoontea.log.Logger;

import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/5/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/5/2017 do fisrt create. </li>
 */
public class DataUtil {

    private static final String TAG = "DataUtil";

    public static List<GankEntity.ResultsBean> removeDuplicateData(
            List<GankEntity.ResultsBean> orignalDatas, List<GankEntity.ResultsBean> newDatas) {
        if (orignalDatas == null) {
            return newDatas;
        }

        if (newDatas == null) {
            return orignalDatas;
        }

        String orignalId = orignalDatas.get(0).get_id();

        for (GankEntity.ResultsBean resultsBean : newDatas) {
            if (resultsBean.get_id().equals(orignalId)) {
                return orignalDatas;
            }
        }

        orignalDatas.addAll(0, newDatas);

        return orignalDatas;
    }

    public static List<GankEntity.ResultsBean> getAvalibleData(
            List<GankEntity.ResultsBean> orignalDatas, List<GankEntity.ResultsBean> newDatas) {
        if (newDatas != null) {
            String lastOldId = orignalDatas.get(orignalDatas.size() - 1).get_id();
            int index = -1;

            for (int i = 0; i < newDatas.size(); i++) {
                if (newDatas.get(i).get_id().equals(lastOldId)) {
                    index = i;
                }
            }

            if (index != -1) {// 有重复数据
                Logger.d(TAG, "has Duplicate datas.");
                for (int i = index; i < newDatas.size(); i++) {
                    orignalDatas.add(newDatas.get(i));
                }
            } else {
                Logger.d(TAG, "has no Duplicate datas.");
                orignalDatas.addAll(newDatas);
            }
        }

        return orignalDatas;
    }

}
