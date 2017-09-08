/**
 * @Title PageItemMaker.java
 * @Package com.hiveview.cloudscreen.video.view
 * @author haozening
 * @date 2015-5-5 上午9:45:02
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.view.pageItemView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import com.hiveview.cloudscreen.vipvideo.view.TypeFaceTextView;

/**
 * @author haozening
 * @ClassName PageItemMaker
 * @Description
 * @date 2015-5-5 上午9:45:02
 */
@SuppressLint("NewApi")
public class SubjectPageItemView extends FrameLayout implements OnFocusChangeListener, OnKeyListener {

    /**
     * FilmPageItemView
     */
    private static final String TAG = SubjectPageItemView.class.getSimpleName();
    private Context context;
    private ViewGroup firstRow;
    private ViewGroup secondRow;
    private static final int ITEM_DURATION = 300;
    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    private AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
    private int title_translation_y;
    private static int lastPosition = 0;
    private static boolean shouldRequestFocus = false;
    private static boolean shouldRequestLastFocus = false;

    public SubjectPageItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public SubjectPageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SubjectPageItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        ViewGroup viewContainer = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_subject_list_page, null);
        title_translation_y = (int) context.getResources().getDimensionPixelSize(R.dimen.six_column_item_tv_title_translationY);
        firstRow = (ViewGroup) viewContainer.findViewById(R.id.ll_row_1);
        secondRow = (ViewGroup) viewContainer.findViewById(R.id.ll_row_2);
        viewContainer.removeAllViews();
        addView(firstRow);
        addView(secondRow);
        for (int i = 0; i < firstRow.getChildCount(); i++) {
            firstRow.getChildAt(i).setOnFocusChangeListener(this);
            firstRow.getChildAt(i).setOnKeyListener(this);
        }
        for (int i = 0; i < secondRow.getChildCount(); i++) {
            secondRow.getChildAt(i).setOnFocusChangeListener(this);
            secondRow.getChildAt(i).setOnKeyListener(this);
        }

        resetUserColor();
    }

    public ViewGroup firstRowView() {
        return firstRow;
    }

    public ViewGroup secondRowView() {
        return secondRow;
    }

    /**
     * 获取子View
     *
     * @param index
     * @return
     * @Title getItem
     * @author haozening
     * @Description
     */
    public View getSubviewAt(int index) {
        if (index / 3 >= 1) {
            return secondRow.getChildAt(index - 3);
        } else {
            Log.d(TAG, "firstRow.getChildCount : " + firstRow.getChildCount());
            return firstRow.getChildAt(index);
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/25
     * @Description 返回现出来的所有view中最应该获取焦点的view
     */
    public View getLastView(int index) {
        // 如果index指向第二行，但是第二行无数据，则重新指向第一行
        Log.d(TAG, "secondRow.getChildCount() : " + secondRow.getChildCount() + " index : " + index);
        if (secondRow.getChildAt(0).getVisibility() != View.VISIBLE && index >= 3) {
            return getLastView(index - 3);
        }
        if (index / 3 >= 1) { // 如果index指向第二行，并且第二行有数据
            View secondView = secondRow.getChildAt(index - 3); // 尝试获取第二行指定index的View
            if (null != secondView && secondView.getVisibility() == View.VISIBLE) { // 如果View不为空返回
                Log.d(TAG, "null != secondView");
                return secondView;
            } else { // 如果为空，第一行肯定是整行都存在，肯定存在对应位置的View，返回
                Log.d(TAG, "null == secondView ");
                return firstRow.getChildAt(index - 3);
            }
        } else { // 指向第一行，第一行数据不一定是全的
            Log.d(TAG, "firstRow.getChildCount : " + firstRow.getChildCount());
            View firstView = firstRow.getChildAt(index);
            if (null != firstView && firstView.getVisibility() == View.VISIBLE) {
                Log.d(TAG, "null != firstView ： " + (null != firstView));
                return firstView;
            } else {
                return getLastView(index - 1);
            }
        }
    }

    public int getViewIndex(View view) {
        int firstRowIndex = firstRow.indexOfChild(view);
        if (firstRowIndex >= 0) {
            return firstRowIndex;
        } else {
            int secondRowIndex = secondRow.indexOfChild(view);
            if (secondRowIndex >= 0) {
                return secondRowIndex + firstRow.getChildCount();
            } else {
                return 0 + firstRow.getChildCount();
            }
        }
    }

    public ImageView getImageView(View v) {
        return (ImageView) v.findViewById(R.id.subject_list_back);
    }

    public ImageView getCorner(View v) {
        return (ImageView) v.findViewById(R.id.v_corner);
    }

    public View getBackgroundOneLine(View v) {
        return v.findViewById(R.id.subject_list_text_nopress_back);
    }

    public View getBackgroundMultiLine(View v) {
        return v.findViewById(R.id.subject_list_text_press_back);
    }

    public TypeFaceTextView getTitleTextView(View v) {
        return (TypeFaceTextView) v.findViewById(R.id.subject_list_text1);
    }

    public TypeFaceTextView getDescriptTextView(View v) {
        return (TypeFaceTextView) v.findViewById(R.id.subject_list_text2);
    }

    public TypeFaceTextView getCountTextView(View v) {
        return (TypeFaceTextView) v.findViewById(R.id.subject_list_text3);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (hasFocus) {
                Log.d(TAG, "onFocusChange index : " + getViewIndex(v));
                if (shouldRequestLastFocus && getViewIndex(v) == 0) { // 判断如果翻页后，焦点会落在第一个上面，然后重置焦点，如果不在第一个上面，就不重置焦点
                    Log.d(TAG, "shouldRequestLastFocus");
                    shouldRequestLastFocus = false;
                    if (getViewIndex(v) != getViewIndex(getLastView(5))) {
                        getLastView(5).requestFocus();
                        return;
                    }
                }
                if (lastPosition != getViewIndex(v) && shouldRequestFocus) {
                    shouldRequestFocus = false;
                    Log.d(TAG, "lastPosition : " + lastPosition);
                    if (getViewIndex(v) != getViewIndex(getLastView(lastPosition))) {
                        getLastView(lastPosition).requestFocus();
                        return;
                    }
                }
            }
            // 默认将标志位都置为false
            shouldRequestLastFocus = false;
            shouldRequestFocus = false;
            onFocus(v);
        } else {
            loseFocus(v);
            lastPosition = getViewIndex(v);
        }
    }

    private void onFocus(View v) {
//        getBackgroundOneLine(v).animate().alpha(0f).setDuration(ITEM_DURATION).start();
        getBackgroundOneLine(v).setVisibility(INVISIBLE);
//        getBackgroundMultiLine(v).animate().alpha(1f).setDuration(ITEM_DURATION).start();
        getBackgroundMultiLine(v).setVisibility(VISIBLE);
        getTitleTextView(v).animate().translationY(-title_translation_y).setDuration(ITEM_DURATION).start();
        getDescriptTextView(v).animate().translationY(0).alpha(1f).setDuration(ITEM_DURATION).setInterpolator(accelerateInterpolator).start();
        getCountTextView(v).animate().translationY(0).alpha(1f).setDuration(ITEM_DURATION).setInterpolator(accelerateInterpolator).start();
    }

    private void loseFocus(View v) {
//        getBackgroundOneLine(v).animate().alpha(1f).setDuration(ITEM_DURATION).start();
        getBackgroundOneLine(v).setVisibility(VISIBLE);
//        getBackgroundMultiLine(v).animate().alpha(0f).setDuration(ITEM_DURATION).start();
        getBackgroundMultiLine(v).setVisibility(INVISIBLE);
        getTitleTextView(v).animate().translationY(0).setDuration(ITEM_DURATION).start();
        getDescriptTextView(v).animate().translationY(title_translation_y).alpha(0f).setDuration(ITEM_DURATION)
                .setInterpolator(decelerateInterpolator).start();
        getCountTextView(v).animate().translationY(title_translation_y).alpha(0f).setDuration(ITEM_DURATION).setInterpolator(decelerateInterpolator)
                .start();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (null != itemKeyListener) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    int index = getViewIndex(v);
                    Log.d(TAG, "index : " + index);
                    if (index == 0) {
                        shouldRequestLastFocus = true;
                    }
                }
                boolean keyVal = itemKeyListener.onKey(v, keyCode, event);
                if (keyVal) {
                    return true;
                }
            }
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if (secondRow.indexOfChild(v) == 0) {
                        firstRow.getChildAt(firstRow.getChildCount() - 1).requestFocus();
                        return true;
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    if (firstRow.indexOfChild(v) == firstRow.getChildCount() - 1) {
                        if (firstRow.getChildCount() > 0) {
                            secondRow.getChildAt(0).requestFocus();
                            return true;
                        }
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    Log.d(TAG, "secondRow index : " + secondRow.indexOfChild(v));
                    if (secondRow.indexOfChild(v) >= 0) {
                        shouldRequestFocus = true;
                        return false;
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    if (firstRow.indexOfChild(v) >= 0) {
                        shouldRequestFocus = true;
                        return false;
                    }
                    break;
            }
        }
        return false;
    }

    private OnItemKeyListener itemKeyListener;


    public static interface OnItemKeyListener {
        public boolean onKey(View v, int keyCode, KeyEvent event);
    }

    public void setOnItemKeyListener(OnItemKeyListener listener) {
        itemKeyListener = listener;
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/6/28
     * @Description 重置焦点颜色
     */
    public void resetUserColor() {
        switch (UserStateUtil.getInstance().getUserStatus()) {
            case VIPUSER:
                for (int i = 0; i < firstRow.getChildCount(); i++) {
                    firstRow.getChildAt(i).setBackgroundResource(R.drawable.focused_view_vip_selector);
                }
                for (int i = 0; i < secondRow.getChildCount(); i++) {
                    secondRow.getChildAt(i).setBackgroundResource(R.drawable.focused_view_vip_selector);
                }
                break;
            default:
                for (int i = 0; i < firstRow.getChildCount(); i++) {
                    firstRow.getChildAt(i).setBackgroundResource(R.drawable.focused_view_selector);
                }
                for (int i = 0; i < secondRow.getChildCount(); i++) {
                    secondRow.getChildAt(i).setBackgroundResource(R.drawable.focused_view_selector);
                }
                break;
        }
    }
}
