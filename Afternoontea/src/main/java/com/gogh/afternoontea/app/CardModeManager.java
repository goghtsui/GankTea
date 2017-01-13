package com.gogh.afternoontea.app;

import com.gogh.afternoontea.listener.OnCardModeChangedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/12/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/12/2017 do fisrt create. </li>
 */
public class CardModeManager {

    private static List<OnCardModeChangedListener> cardModeListeners = new ArrayList<>();

    private static CardModeManager INSTANCE;

    private CardModeManager() {
    }

    public static CardModeManager newInstance() {
        if (INSTANCE == null) {
            INSTANCE = SingleHolder.MANAGER;
        }
        return INSTANCE;
    }

    public void registerCardModeChangedListener(OnCardModeChangedListener onCardModeChangedListener) {
        cardModeListeners.add(onCardModeChangedListener);
    }

    public void unRegisterCardModeChangedListener(OnCardModeChangedListener onCardModeChangedListener) {
        cardModeListeners.remove(onCardModeChangedListener);
    }

    public void notifyCardModeChanged() {
        if (cardModeListeners.size() > 0) {
            for(int i = 0; i< cardModeListeners.size(); i++){
                cardModeListeners.get(i).onChanged();
            }
        }
    }

    private static final class SingleHolder {
        private static final CardModeManager MANAGER = new CardModeManager();
    }
}
