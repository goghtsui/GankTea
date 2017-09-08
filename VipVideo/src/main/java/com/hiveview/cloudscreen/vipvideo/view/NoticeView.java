package com.hiveview.cloudscreen.vipvideo.view; /**
 * @Title NoticeView.java
 * @Package com.hiveview.cloudscreen.video.view
 * @author haozening
 * @date 2014年9月16日 上午11:19:47
 * @Description
 * @version V1.0
 */

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 提示信息，底部的按键提示信息
 *
 * @ClassName NoticeView
 * @Description
 * @author haozening
 * @date 2014年9月16日 上午11:19:47
 *
 */
@SuppressLint("NewApi")
public class NoticeView extends LinearLayout {

	public static final int NO_CONTAINER = -1;
	private List<View> viewList = new ArrayList<View>();
	private Context context;

	public NoticeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public NoticeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public NoticeView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		setOrientation(LinearLayout.HORIZONTAL);

	}

	/**
	 * 有序的添加View
	 * @Title addViewSequential
	 * @author haozening
	 * @Description
	 * @param view
	 * @return
	 */
	public NoticeView addViewSequential(View view) {
		viewList.add(view);
		return this;
	}

	/**
	 * 添加View容器
	 * @Title addContainer
	 * @author haozening
	 * @Description
	 * @param count 添加的容器个数
	 * @return
	 */
	public NoticeView addContainer(int count) {
		for (int i = 0; i < count; i++) {
			LinearLayout layout = new LinearLayout(context);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			viewList.add(layout);
		}
		return this;
	}

	/**
	 * 添加图片资源
	 *
	 * @Title addImageView
	 * @author haozening
	 * @Description
	 * @param resId
	 * @return
	 */
	public NoticeView addImageRes(int resId) {
		return addImageRes(resId, NO_CONTAINER);
	}

	/**
	 * 在视图容器中添加图片资源
	 *
	 * @Title addImageRes
	 * @author haozening
	 * @Description
	 * @param resId
	 * @param containerIndex
	 * @return
	 */
	public NoticeView addImageRes(int resId, int containerIndex) {
		ImageView iv = new ImageView(context);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_VERTICAL;
		iv.setLayoutParams(params);
		iv.setImageResource(resId);
		if (containerIndex == NO_CONTAINER) {
			viewList.add(iv);
		} else {
			LinearLayout layout = (LinearLayout) viewList.get(containerIndex);
			if(null != layout) {
				layout.addView(iv);
			}
		}
		return this;
	}

	/**
	 * 显示文本信息
	 *
	 * @Title addTextView
	 * @author haozening
	 * @Description
	 * @param text
	 * @return
	 */
	public NoticeView addText(String text) {
		return addText(text, NO_CONTAINER);
	}

	/**
	 * 显示文本信息
	 *
	 * @Title addTextView
	 * @author haozening
	 * @Description
	 * @param text
	 * @return
	 */
	public NoticeView addText(String text, int containerIndex) {
		TextView tv = new TextView(context);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_VERTICAL;
		tv.setLayoutParams(params);
		tv.setText(text);
		if (containerIndex == NO_CONTAINER) {
			viewList.add(tv);
		} else {
			LinearLayout layout = (LinearLayout) viewList.get(containerIndex);
			if (null != layout) {
				layout.addView(tv);
			}
		}
		return this;
	}

	public List<View> getViews() {
		return viewList;
	}

	public void display() {
		requestLayout();
	}

	public void clear() {
		viewList.clear();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		removeAllViews();
		for (View view : viewList) {
			if (null != view) {
				addView(view);
			}
		}
		super.onLayout(changed, l, t, r, b);
	}

}
