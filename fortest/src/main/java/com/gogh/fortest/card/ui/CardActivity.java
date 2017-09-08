package com.gogh.fortest.card.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.gogh.fortest.R;
import com.gogh.fortest.card.adpter.BaseCardAdapter;
import com.gogh.fortest.card.adpter.MeiziAdapter;
import com.gogh.fortest.card.entity.MeiziBean;
import com.gogh.fortest.card.view.LaminatedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 2/21/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 2/21/2017 do fisrt create. </li>
 */
public class CardActivity extends Activity {

    private boolean focusAble = false;

//    private SwipeCardsView cardsView;
    private LaminatedImageView cardsView;

    private static final String[] IMAGES = {
            "http://pic.kor.domybox.com/2017/01/26/11/51/50/1485399110748.jpg",
            "http://pic.kor.domybox.com/2017/02/17/11/50/03/1487299803647.jpg",
            "http://pic.kor.domybox.com/2016/08/19/20/31/47/1471663907262.jpg",
            "http://pic.kor.domybox.com/2016/09/06/11/55/47/1473130547326.jpg",
            "http://pic.kor.domybox.com/2016/09/01/11/13/01/1472695981710.jpg",
            "http://pic.kor.domybox.com/2016/09/01/10/39/25/1472693965486.jpg",
            "http://pic.kor.domybox.com/2016/11/11/18/11/27/1478855487662.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meizipic_layout);
//        cardsView = (SwipeCardsView) findViewById(R.id.swipCardsView);
        cardsView = (LaminatedImageView) findViewById(R.id.swipCardsView);
//        cardsView.requestFocus(View.FOCUS_DOWN, null);
        findViewById(R.id.display_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(focusAble){
                    cardsView.setFocusable(focusAble);
                    cardsView.requestFocus(View.FOCUS_DOWN, null);
                } else {
                    cardsView.setFocusable(focusAble);
                }
                focusAble =!focusAble;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter(new MeiziAdapter(getDataList(), this));
    }

    private void setAdapter(BaseCardAdapter<MeiziBean> adapter) {
        cardsView.setAdapter(adapter);
    }

    private List<MeiziBean> getDataList(){
        List<MeiziBean> list = new ArrayList<>();
        for(String imageUrl : IMAGES){
            list.add(new MeiziBean(imageUrl));
        }
        return list;
    }

}
