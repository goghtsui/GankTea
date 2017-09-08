package com.hiveview.cloudscreen.vipvideo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hiveview.cloudscreen.vipvideo.R;

/**
 * 
 * @ClassName NoResultView
 * @Description 无结果页，对应为搜索历史、收藏历史、播放历史
 * @author xieyi
 * @date 2014-11-5 上午10:46:22
 *
 */
public class NoResultView extends RelativeLayout {

	public NoResultView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public NoResultView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public NoResultView(Context context) {
		super(context);
		init();
	}
	
	private ImageView no_result_color;
	private TypeFaceTextView title_text1;
	private TypeFaceTextView empty_title;
	
	private void init() {
		LayoutInflater mLayoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View container = mLayoutInflater.inflate(R.layout.no_result_view_layout, null);
		addView(container);
		no_result_color = (ImageView) container.findViewById(R.id.no_result_color);
		title_text1 = (TypeFaceTextView) container.findViewById(R.id.title_text1);
		empty_title = (TypeFaceTextView) container.findViewById(R.id.empty_title);
	}
	
	public void setData(int resourceId, String title, String content){
		no_result_color.setBackgroundResource(resourceId);
		title_text1.setText(title);
		empty_title.setText(content);
	}
}
