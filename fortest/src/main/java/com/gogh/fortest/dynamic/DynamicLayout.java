package com.gogh.fortest.dynamic;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 8/23/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 8/23/2017 do fisrt create. </li>
 */

public class DynamicLayout extends HorizontalScrollView {

    private static final String TAG = "DynamicLayout";

    private RelativeLayout mRootView;
    private DynamicAdapter dynamicAdapter;

    public DynamicLayout(Context context) {
        this(context, null);
    }

    public DynamicLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSmoothScrollingEnabled(true);
        setSoundEffectsEnabled(true);
        setClipToPadding(false);
        init(context);
    }

    private void init(Context context){
        setPadding(20, 20, 20, 20);
        mRootView = new RelativeLayout(context);
        addView(mRootView);
    }

    private void addItem(View view){
        mRootView.addView(view);
    }

    public void setAdapter(DynamicAdapter dynamicAdapter){
        this.dynamicAdapter = dynamicAdapter;
        bindView();
    }

    private void bindView(){
        if(dynamicAdapter != null){
            for(int i = 0; i < dynamicAdapter.getItemCount(); i++){
                View view = LayoutInflater.from(getContext()).inflate(dynamicAdapter.getLayoutId(), null);
                dynamicAdapter.resetItemParams(view, i);
                dynamicAdapter.onBindView(dynamicAdapter.getViewHolder(view), i);
                addItem(view);
            }
        } else {
            throw new NullPointerException("Adapter is null.");
        }
    }

    @Override
    public boolean arrowScroll(int direction) {
        Log.d(TAG, "arrowScroll");
        View currentFocused = findFocus();
        if (currentFocused == this) currentFocused = null;

        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);

        final int maxJump = getMaxScrollAmount();

        Rect mTempRect = (Rect) FieldReflect.getFieldValue(this);

        if (nextFocused != null && isWithinDeltaOfScreen(nextFocused, maxJump)) {
            nextFocused.getDrawingRect(mTempRect);
            offsetDescendantRectToMyCoords(nextFocused, mTempRect);
            int scrollDelta = computeScrollDeltaToGetChildRectOnScreen(mTempRect);
            Log.d(TAG, "scrollDelta" + scrollDelta);
            Log.d(TAG, "scrollDelta scrollx" + getScrollX());
            FieldReflect.setFieldValue(this, mTempRect);
            if(scrollDelta < 0){
                doScrollX(scrollDelta - getPaddingLeft());
            } else if(scrollDelta > 0) {
                doScrollX(scrollDelta + getPaddingRight());
            } else {
                doScrollX(scrollDelta);
            }

            nextFocused.requestFocus(direction);
        } else {
            // no new focus
            int scrollDelta = maxJump;

            if (direction == View.FOCUS_LEFT && getScrollX() < scrollDelta) {
                scrollDelta = getScrollX();
            } else if (direction == View.FOCUS_RIGHT && getChildCount() > 0) {

                int daRight = getChildAt(0).getRight();

                int screenRight = getScrollX() + getWidth();

                if (daRight - screenRight < maxJump) {
                    scrollDelta = daRight - screenRight;
                }
            }
            if (scrollDelta == 0) {
                return false;
            }
            doScrollX(direction == View.FOCUS_RIGHT ? scrollDelta : -scrollDelta);
        }

        if (currentFocused != null && currentFocused.isFocused()
                && isOffScreen(currentFocused)) {
            // previously focused item still has focus and is off screen, give
            // it up (take it back to ourselves)
            // (also, need to temporarily force FOCUS_BEFORE_DESCENDANTS so we are
            // sure to
            // get it)
            final int descendantFocusability = getDescendantFocusability();  // save
            setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
            requestFocus();
            setDescendantFocusability(descendantFocusability);  // restore
        }
        return true;
    }

    private void doScrollX(int delta) {
        Log.d(TAG, "doScrollX");
        if (delta != 0) {
            if (isSmoothScrollingEnabled()) {
                smoothScrollBy(delta, 0);
            } else {
                scrollBy(delta, 0);
            }
        }
    }

    private boolean isOffScreen(View descendant) {
        Log.d(TAG, "isOffScreen");
        return !isWithinDeltaOfScreen(descendant, 0);
    }

    private boolean isWithinDeltaOfScreen(View descendant, int delta) {
        Log.d(TAG, "isWithinDeltaOfScreen");
        Rect mTempRect = (Rect) FieldReflect.getFieldValue(this);

        descendant.getDrawingRect(mTempRect);
        offsetDescendantRectToMyCoords(descendant, mTempRect);

        FieldReflect.setFieldValue(this, mTempRect);

        return (mTempRect.right + delta) >= getScrollX()
                && (mTempRect.left - delta) <= (getScrollX() + getWidth());
    }

}
