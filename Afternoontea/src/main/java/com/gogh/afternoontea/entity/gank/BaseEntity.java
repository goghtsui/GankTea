package com.gogh.afternoontea.entity.gank;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 9/13/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 9/13/2017 do fisrt create. </li>
 */

public interface BaseEntity extends Serializable {

    String get_id();

    void set_id(String _id);

    String getCreatedAt();

    void setCreatedAt(String createdAt);

    String getDesc();

    void setDesc(String desc);

    String getPublishedAt();

    void setPublishedAt(String publishedAt);

    String getType();

    void setType(String type);

    String getUrl();

    void setUrl(String url);

    boolean isUsed();

    void setUsed(boolean used);

    String getWho();

    void setWho(String who);

    String getSource();

    void setSource(String source);

    List<String> getImages();

    void setImages(List<String> images);
}
