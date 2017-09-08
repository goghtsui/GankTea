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
import android.graphics.Color;
import android.text.TextUtils;
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
import android.widget.TextView;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import com.hiveview.cloudscreen.vipvideo.view.MarqueeTextView;


/**
 * @author haozening
 * @ClassName PageItemMaker
 * @Description
 * @date 2015-5-5 上午9:45:02
 */
@SuppressLint("NewApi")
public class FilmPageItemView extends FrameLayout implements OnFocusChangeListener, OnKeyListener {

    /**
     * FilmPageItemView
     */
    private static final String TAG = FilmPageItemView.class.getSimpleName();
    private Context context;
    private ViewGroup firstRow;
    private ViewGroup secondRow;
    private static final int ITEM_DURATION = 200;
    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
    private AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
    private int title_translation_y;
    private static int lastPosition = 0;
    private static boolean shouldRequestFocus = false;
    private static boolean shouldRequestLastFocus = false;
    private static final int countPerLine = 5;

    public FilmPageItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public FilmPageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FilmPageItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        ViewGroup viewContainer = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_film_list_page, null);
        title_translation_y = (int) context.getResources().getDimensionPixelSize(R.dimen.six_column_item_tv_title_translationY);
        firstRow = (ViewGroup) viewContainer.findViewById(R.id.ll_row_1);
        secondRow = (ViewGroup) viewContainer.findViewById(R.id.ll_row_2);
        viewContainer.removeAllViews();
        addView(firstRow);
        addView(secondRow);
        for (int i = 0; i < firstRow.getChildCount(); i++) {
            firstRow.getChildAt(i).setOnFocusChangeListener(this);
            firstRow.getChildAt(i).setOnKeyListener(this);
//            firstRow.getChildAt(i).setBackgroundResource(R.drawable.focused_view_vip_selector);
        }
        for (int i = 0; i < secondRow.getChildCount(); i++) {
            secondRow.getChildAt(i).setOnFocusChangeListener(this);
            secondRow.getChildAt(i).setOnKeyListener(this);
        }
        //根据用户状态设置焦点背景色
        resetUserColor();
    }

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
        if (index / countPerLine >= 1) {
            return secondRow.getChildAt(index - countPerLine);
        } else {
            Log.d(TAG, "firstRow.getChildCount : " + firstRow.getChildCount());
            return firstRow.getChildAt(index);
        }
    }


    public int getCurrentIndex() {
        if (null != firstRow.findFocus()) {
            return firstRow.indexOfChild(firstRow.findFocus());
        } else {
            return secondRow.indexOfChild(secondRow.findFocus()) + countPerLine;
        }
    }

    public boolean hasNextDownView(int index) {
        if (index / countPerLine == 1) {
            return false;
        } else {
            View view = secondRow.getChildAt(index);
            if (view.getVisibility() == View.VISIBLE) {
                return true;
            } else {
                if (secondRow.getChildAt(0).getVisibility() == View.VISIBLE) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public View getLastView(int index) {
        // 如果index指向第二行，但是第二行无数据，则重新指向第一行
        Log.d(TAG, "secondRow.getChildCount() : " + secondRow.getChildCount() + " index : " + index);
        if (secondRow.getChildAt(0).getVisibility() != View.VISIBLE && index >= countPerLine) {
            return getLastView(index - countPerLine);
        }
        if (index / countPerLine >= 1) { // 如果index指向第二行，并且第二行有数据
            View secondView = secondRow.getChildAt(index - countPerLine); // 尝试获取第二行指定index的View
            if (null != secondView && secondView.getVisibility() == View.VISIBLE) { // 如果View不为空返回
                Log.d(TAG, "null != secondView");
                return secondView;
            } else { // 如果为空，第一行肯定是整行都存在，肯定存在对应位置的View，返回
                Log.d(TAG, "null == secondView ");
                return firstRow.getChildAt(index - countPerLine);
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
        return (ImageView) v.findViewById(R.id.image_cover);
    }

    public ImageView getCornerView(View v) {
        return (ImageView) v.findViewById(R.id.v_corner);
    }

    public View getBackgroundOneLine(View v) {
        return v.findViewById(R.id.bg_oneline);
    }

    public View getBackgroundMultiLine(View v) {
        return v.findViewById(R.id.bg_multiline);
    }

    public MarqueeTextView getTitleTextView(View v) {
        return (MarqueeTextView) v.findViewById(R.id.tv_title);
    }

    public TextView getScoreTextView(View v) {
        return (TextView) v.findViewById(R.id.tv_score_star_bottom);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            Log.d(TAG, "onFocusChange index : " + getViewIndex(v));
            //TODO 这里的交互还需要在细化
//            if (shouldRequestLastFocus && getViewIndex(v) == 0) { // 判断如果翻页后，焦点会落在第一个上面，然后重置焦点，如果不在第一个上面，就不重置焦点
//                Log.d(TAG, "shouldRequestLastFocus");
//                shouldRequestLastFocus = false;
//                if (getViewIndex(v) != getViewIndex(getLastView(11))) {
//                    getLastView(11).requestFocus();
//                    return;
//                }
//            }
            if (lastPosition != getViewIndex(v) && shouldRequestFocus) {
                shouldRequestFocus = false;
                Log.d(TAG, "lastPosition : " + lastPosition);
                if (getViewIndex(v) != getViewIndex(getLastView(lastPosition))) {
                    getLastView(lastPosition).requestFocus();
                    return;
                }
            }
            // 默认将标志位都置为false
            shouldRequestLastFocus = false;
            shouldRequestFocus = false;
            startMarquee(getTitleTextView(v));
//            getBackgroundOneLine(v).animate().alpha(0f).setDuration(ITEM_DURATION).start();
            getBackgroundOneLine(v).setVisibility(INVISIBLE);
//            getBackgroundMultiLine(v).animate().alpha(1f).setDuration(ITEM_DURATION).start();
            getBackgroundMultiLine(v).setVisibility(VISIBLE);
            getTitleTextView(v).animate().translationY(-title_translation_y).setDuration(ITEM_DURATION).start();
            getTitleTextView(v).setTextColor(Color.WHITE);
            getScoreTextView(v).animate().translationY(0).alpha(1f).setDuration(ITEM_DURATION).setInterpolator(accelerateInterpolator).start();
            v.animate().scaleX(1.05f).scaleY(1.05f).start();
            if (null != onItemSelectedListener) {
                onItemSelectedListener.onItemSelected(v);
            }
        } else {
            stopMarquee(getTitleTextView(v));
//            getBackgroundOneLine(v).animate().alpha(1f).setDuration(ITEM_DURATION).start();
            getBackgroundOneLine(v).setVisibility(VISIBLE);
//            getBackgroundMultiLine(v).animate().alpha(0f).setDuration(ITEM_DURATION).start();
            getBackgroundMultiLine(v).setVisibility(INVISIBLE);
            getTitleTextView(v).animate().translationY(0).setDuration(ITEM_DURATION).start();
            getTitleTextView(v).setTextColor(Color.parseColor("#AAAAAA"));
            getScoreTextView(v).animate().translationY(title_translation_y).alpha(0f).setDuration(ITEM_DURATION)
                    .setInterpolator(decelerateInterpolator).start();
            v.animate().scaleX(1f).scaleY(1f).start();
            lastPosition = getViewIndex(v);
        }
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
//			case KeyEvent.KEYCODE_DPAD_LEFT:
//				if (secondRow.indexOfChild(v) == 0) {
//					firstRow.getChildAt(firstRow.getChildCount() - 1).requestFocus();
//					return true;
//				}
//				break;
//			case KeyEvent.KEYCODE_DPAD_RIGHT:
//				Log.d(TAG, "KEYCODE_DPAD_RIGHT : " + secondRow.indexOfChild(v));
//				if (secondRow.indexOfChild(v) >= 0) {
//					View view = secondRow.getChildAt(secondRow.indexOfChild(v) + 1);
//					if (null != view && view.getVisibility() != View.VISIBLE) {
//						Log.d(TAG, "KEYCODE_DPAD_RIGHT return true");
//						return true;
//					}
//				}
//				if (firstRow.indexOfChild(v) == firstRow.getChildCount() - 1) {
//					if (firstRow.getChildCount() > 0) {
//						secondRow.getChildAt(0).requestFocus();
//						return true;
//					}
//				}
//				break;
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

    private OnItemSelectedListener onItemSelectedListener;

    public static interface OnItemSelectedListener {
        public void onItemSelected(View v);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        onItemSelectedListener = listener;
    }

    /**
     * 开始滚动文本
     *
     * @Title startMarquee
     * @author haozening
     * @Description
     */
    private void startMarquee(MarqueeTextView titleView) {
        String title = (String) titleView.getText();
        titleView.setSingleLine(true);
        titleView.setEllipsize(TextUtils.TruncateAt.valueOf("MARQUEE"));
        titleView.setIsInFocusView(true);
        titleView.setText(title);
    }

    /**
     * 结束文本滚动
     *
     * @Title stopMarquee
     * @author haozening
     * @Description
     */
    private void stopMarquee(MarqueeTextView titleView) {
        String title = (String) titleView.getText();
        titleView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        titleView.setIsInFocusView(false);
        titleView.setMaxLines(1);
        titleView.setText(title);
    }

}
