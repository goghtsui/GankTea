package com.hiveview.cloudscreen.vipvideo.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Scroller;

import com.hiveview.cloudscreen.vipvideo.activity.adapter.RecordAdapter;

import java.util.LinkedList;
import java.util.Queue;

public class HistoryView extends AdapterView<ListAdapter> {

	public boolean mAlwaysOverrideTouch = true;
	protected ListAdapter mAdapter;
	private int mTopViewIndex = -1; //完整移出屏幕上边视图的个数
	private int mBottomViewIndex = 0; //从屏幕上边到整个布局下边的子视图个数
	private int mCurrentY;
	protected int mNextY;
	private int mMaxY = Integer.MAX_VALUE;
	private int mDisplayOffset = 0; //完整移出屏幕上边视图的总长度
	protected Scroller mScroller;
	private Queue<View> mRemovedViewQueue = new LinkedList<View>(); //删除视图队列
	private boolean mDataChanged = false; //数据集是否有改变

	
	public Scroller getmScroller() {
		return mScroller;
	}

	public int getmNextY() {
		return mNextY;
	}

	public HistoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private synchronized void initView() {
		mTopViewIndex = -1;
		mBottomViewIndex = 0;
		mDisplayOffset = 0;
		mCurrentY = 0;
		mNextY = 0;
		mMaxY = Integer.MAX_VALUE;
		mScroller = new Scroller(getContext(), new DecelerateInterpolator());
	}

	private DataSetObserver mDataObserver = new DataSetObserver() {

		@Override
		public void onChanged() {
			synchronized (HistoryView.this) {
				mDataChanged = true;
			}
			invalidate();
			requestLayout();
		}

		@Override
		public void onInvalidated() {
			reset();
			invalidate();
			requestLayout();
		}

	};

	@Override
	public ListAdapter getAdapter() {
		return mAdapter;
	}

	@Override
	public View getSelectedView() {
		return null;
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		if (mAdapter != null) {
			mAdapter.unregisterDataSetObserver(mDataObserver);
		}
		mAdapter = adapter;
		if (mAdapter != null) {
			mAdapter.registerDataSetObserver(mDataObserver);
		}
		reset();
	}

	private synchronized void reset() {
		initView();
		removeAllViewsInLayout();
		requestLayout();
	}

	@Override
	public void setSelection(int position) {
	}

	private void addAndMeasureChild(final View child, int viewPos) {
		LayoutParams params = child.getLayoutParams();
		if (params == null) {
			params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		}

		addViewInLayout(child, viewPos, params, true);
		//测量子视图按照它自己的参数来布局
		child.measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.AT_MOST),
					  MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.AT_MOST));
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		if (mAdapter == null) {
			return;
		}

		if (mDataChanged) {
			int oldCurrentY = mCurrentY;
			initView();
			removeAllViewsInLayout();
			mNextY = oldCurrentY; // FIXME 删除逻辑，删除后最大偏移量发生会发生变化
			mDataChanged = false;
		}
		//得到scroller的真实偏移量,false时代表滚动到目的地,true时是实时的获取
		if (mScroller.computeScrollOffset()) {
			int scrolly = mScroller.getCurrY();
			mNextY = scrolly;
		}

		//如果偏移量<0，说明处于第一个位置，就位移出完整的图片
		if (mNextY <= 0) {
			mNextY = 0;
			mScroller.forceFinished(true); //强制位移在指定位置停止
		}
		
		//如果偏移量大于了整个长度，说明在最后一个位置
		if (mNextY >= mMaxY) {
			mNextY = mMaxY;
			mScroller.forceFinished(true);
		}

		int dy = mCurrentY - mNextY;

		removeNonVisibleItems(dy);
		fillList(dy); //填充布局
		positionItems(dy); //计算子视图布局位置

		mCurrentY = mNextY; //得到当前父布局的X坐标

		//位移动画未完成就重新布局，属性动画原理
		if (!mScroller.isFinished()) {
			post(new Runnable() {
				public void run() {
					requestLayout();
					if(direction == 1 && ((RecordAdapter.RecordHolder)getChildAt(0).getTag()).position != position){
						getChildAt(0).requestFocus();
						direction = 0;
					}else if(direction == 2 && ((RecordAdapter.RecordHolder)getChildAt(1).getTag()).position != position){
						getChildAt(1).requestFocus();
						direction = 0;
					}
				}
			});
		}
	}

	private void fillList(final int dy) {
		int edge = 0;
		View child = getChildAt(getChildCount() - 1);
		if (child != null) {
			edge = child.getBottom();
		}
		fillListBottom(edge, dy);

		edge = 0;
		child = getChildAt(0);
		if (child != null) {
			edge = child.getTop();
		}
		fillListTop(edge, dy);

	}

	private void fillListBottom(int bottomEdge, final int dy) {
		while (bottomEdge + dy < getHeight() && mBottomViewIndex < mAdapter.getCount()) {

			View child = mAdapter.getView(mBottomViewIndex, mRemovedViewQueue.poll(), this);
			addAndMeasureChild(child, -1); //添加子视图，-1为保持持续添加（系统自动计算子视图索引值）
			bottomEdge += child.getMeasuredHeight(); //计算当前布局里子视图长度

			if (mBottomViewIndex == mAdapter.getCount() - 1) {//如果布局到了最后一个子视图，计算滑动最大值，为正：可以左移   为负：可以右移
				mMaxY = mCurrentY + bottomEdge - getHeight();
			}

			if (mMaxY < 0) {
				mMaxY = 0;
			}
			mBottomViewIndex++;
		}

	}

	private void fillListTop(int topEdge, final int dy) {
		while (topEdge + dy > 0 && mTopViewIndex >= 0) {
			View child = mAdapter.getView(mTopViewIndex, mRemovedViewQueue.poll(), this);
			addAndMeasureChild(child, 0);
			topEdge -= child.getMeasuredHeight();
			mTopViewIndex--;
			mDisplayOffset -= child.getMeasuredHeight();
		}
	}
	
	//删除不再屏幕内显示的视图
	private void removeNonVisibleItems(final int dy) {
		View child = getChildAt(0);
		//超出屏幕上边视图：整个子视图超出
		while (child != null && child.getBottom() + dy <= 0) {
			mDisplayOffset += child.getMeasuredHeight();
			mRemovedViewQueue.offer(child); //删除一个就添加进删除队列
			removeViewInLayout(child); //把子视图从总布局首端移除
			mTopViewIndex++;
			child = getChildAt(0); //获取移除后的首个子视图
		}

		child = getChildAt(getChildCount() - 1);
		//整个超出屏幕右边视图
		while (child != null && child.getTop() + dy >= getHeight()) {
			mRemovedViewQueue.offer(child);
			removeViewInLayout(child);
			mBottomViewIndex--;
			child = getChildAt(getChildCount() - 1);
		}
	}

	private void positionItems(final int dy) {
		if (getChildCount() > 0) {
			mDisplayOffset += dy;
			int top = mDisplayOffset;
			//依次挨着前一个子视图布局
			for (int i = 0; i < getChildCount(); i++) {
				View child = getChildAt(i);
				int childHeight = child.getMeasuredHeight();
				child.layout(0, top, child.getMeasuredWidth(),top+childHeight);
				top += childHeight;
			}
		}
	}
	
	private int direction; //0:自由焦点  1:向上强制第一位获取焦点 2:向下强制第一位获取焦点
	private int position; //发起滑动的焦点position
	//一次位移动画是会调用N次onLayout()方法，具体多少次是根据设置的interpolator和duration以及动画因子计算得出的，属性动画原理
	public void scrollTo(int y, int direction, int position) {
		this.direction = direction;
		this.position = position;
		mScroller.startScroll(0, mNextY, 0, y-mNextY, 400); //这里不是开始位移动画，而是设置位移动画各项参数，在调用了requestLayout后动画开始
		requestLayout();
	}

	protected boolean onDown(MotionEvent e) {
		mScroller.forceFinished(true);
		return true;
	}
}
