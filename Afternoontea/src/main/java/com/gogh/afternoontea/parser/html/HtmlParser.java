package com.gogh.afternoontea.parser.html;

import com.gogh.afternoontea.entity.meizi.MeiziBean;
import com.gogh.afternoontea.log.Logger;
import com.gogh.afternoontea.utils.StringUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: html解析器，将图片的url从html里筛选出来</p>
 * <p> Created by <b>高晓峰</b> on 12/24/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/24/2016 do fisrt create. </li>
 */
public class HtmlParser {

    private static final String TAG = "HtmlParser";

    public static List<MeiziBean> parserHtml(String html) {
        List<MeiziBean> meiziList = new ArrayList<>();

        Document doc = Jsoup.parse(html);
        Elements links = doc.select("li");
        Logger.d(TAG, "parserHtml links : " + links.size());

        for (int i = 7; i < links.size(); i++) {
            Element imgelement = links.get(i).select("img").first();
            Logger.d(TAG, "parserHtml imgelement : " + imgelement.toString());
            Element aelement = links.get(i).select("a").first();
            Logger.d(TAG, "parserHtml aelement : " + aelement.toString());

            MeiziBean bean = new MeiziBean();
            bean.setOrder(i);
            bean.setTitle(imgelement.attr("alt").toString());
            bean.setHeight(Integer.valueOf(imgelement.attr("height").toString()));//element.attr("height")
            bean.setWidth(Integer.valueOf(imgelement.attr("width").toString()));
            bean.setImageurl(imgelement.attr("data-original"));
            bean.setUrl(aelement.attr("href"));
            int groupId = StringUtil.url2groupid(bean.getUrl());
            bean.setGroupid(groupId);//首页的这个是从大到小排序的 可以当做排序依据
            Logger.d(TAG, "parserHtml MeiziBean : " + bean.toString());
            meiziList.add(bean);
        }

        Logger.d(TAG, "parserHtml datas: " + meiziList.size());
        return meiziList;
    }

}
