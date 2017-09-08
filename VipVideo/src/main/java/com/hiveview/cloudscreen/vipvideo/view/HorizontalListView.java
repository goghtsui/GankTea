package com.hiveview.cloudscreen.vipvideo.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Scroller;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @ClassName HorizontalListView
 * @Description 水平滚动的ListView  观看顺序为setAdapter()->onLayout()
 * @author xieyi
 * @date 2014-9-10 下午8:02:19
 *
 */
public class HorizontalListView extends AdapterView<ListAdapter> {

	public boolean mAlwaysOverrideTouch = true;
	protected ListAdapter mAdapter;
	private int mLeftViewIndex = -1; //完整移出屏幕左边视图的个数
	private int mRightViewIndex = 0; //从屏幕左边到整个布局右边的子视图个数
	private int mCurrentX;
	protected int mNextX;
	private int mMaxX = Integer.MAX_VALUE;
	private int mDisplayOffset = 0; //完整移出屏幕左边视图的总长度
	protected Scroller mScroller;
	private Queue<View> mRemovedViewQueue = new LinkedList<View>(); //删除视图队列
	private boolean mDataChanged = false; //数据集是否有改变

	public int getmNextX() {
		return mNextX;
	}

	public Scroller getmScroller() {
		return mScroller;
	}

	public HorizontalListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private synchronized void initView() {
		mLeftViewIndex = -1;
		mRightViewIndex = 0;
		mDisplayOffset = 0;
		mCurrentX = 0;
		mNextX = 0;
		mMaxX = Integer.MAX_VALUE;
		mScroller = new Scroller(getContext(), new LinearInterpolator());
	}

	private DataSetObserver mDataObserver = new DataSetObserver() {

		@Override
		public void onChanged() {
			synchronized (HorizontalListView.this) {
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
	public void computeScroll() {
		if(mAdapter == null)
			return;
		if (mScroller.computeScrollOffset()) {
			int scrollx = mScroller.getCurrX();
			mNextX = scrollx;
		}
		//如果偏移量<0，说明处于第一个位置，就位移出完整的图片
		if (mNextX <= 0) {
			mNextX = 0;
			mScroller.forceFinished(true); //强制位移在指定位置停止
		}
		
		//如果偏移量大于了整个长度，说明在最后一个位置
		if (mNextX >= mMaxX) {
			mNextX = mMaxX;
			mScroller.forceFinished(true);
		}

		int dx = mCurrentX - mNextX;

		removeNonVisibleItems(dx);
		fillList(dx); //填充布局
		positionItems(dx); //计算子视图布局位置

		mCurrentX = mNextX; //得到当前父布局的X坐标

		//位移动画未完成就重新布局，属性动画原理
		if (!mScroller.isFinished()) {
			post(new Runnable() {
				public void run() {
					requestLayout();
				}
			});
		}
		super.computeScroll();
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top,
			int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		if (mAdapter == null) {
			return;
		}

		if (mDataChanged) {
			int oldCurrentX = mCurrentX;
			initView();
			removeAllViewsInLayout();
			mNextX = oldCurrentX;
			mDataChanged = false;
		}
		//得到scroller的真实偏移量,false时代表滚动到目的地,true时是实时的获取
		if (mScroller.computeScrollOffset()) {
			int scrollx = mScroller.getCurrX();
			mNextX = scrollx;
		}

		//如果偏移量<0，说明处于第一个位置，就位移出完整的图片
		if (mNextX <= 0) {
			mNextX = 0;
			mScroller.forceFinished(true); //强制位移在指定位置停止
		}
		
		//如果偏移量大于了整个长度，说明在最后一个位置
		if (mNextX >= mMaxX) {
			mNextX = mMaxX;
			mScroller.forceFinished(true);
		}

		int dx = mCurrentX - mNextX;

		removeNonVisibleItems(dx);
		fillList(dx); //填充布局
		positionItems(dx); //计算子视图布局位置

		mCurrentX = mNextX; //得到当前父布局的X坐标

		//位移动画未完成就重新布局，属性动画原理
		if (!mScroller.isFinished()) {
			post(new Runnable() {
				public void run() {
					requestLayout();
				}
			});
		}
	}

	private void fillList(final int dx) {
		int edge = 0;
		View child = getChildAt(getChildCount() - 1);
		if (child != null) {
			edge = child.getRight();
		}
		fillListRight(edge, dx);

		edge = 0;
		child = getChildAt(0);
		if (child != null) {
			edge = child.getLeft();
		}
		fillListLeft(edge, dx);

	}

	private void fillListRight(int rightEdge, final int dx) {
		while (rightEdge + dx < getWidth() && mRightViewIndex < mAdapter.getCount()) {

			View child = mAdapter.getView(mRightViewIndex, mRemovedViewQueue.poll(), this);
			addAndMeasureChild(child, -1); //添加子视图，-1为保持持续添加（系统自动计算子视图索引值）
			rightEdge += child.getMeasuredWidth(); //计算当前布局里子视图长度

			if (mRightViewIndex == mAdapter.getCount() - 1) {//如果布局到了最后一个子视图，计算滑动最大值，为正：可以左移   为负：可以右移
				mMaxX = mCurrentX + rightEdge - getWidth();
			}

			if (mMaxX < 0) {
				mMaxX = 0;
			}
			mRightViewIndex++;
		}

	}

	private void fillListLeft(int leftEdge, final int dx) {
		while (leftEdge + dx > 0 && mLeftViewIndex >= 0) {
			View child = mAdapter.getView(mLeftViewIndex, mRemovedViewQueue.poll(), this);
			addAndMeasureChild(child, 0);
			leftEdge -= child.getMeasuredWidth();
			mLeftViewIndex--;
			mDisplayOffset -= child.getMeasuredWidth();
		}
	}
	
	//删除不再屏幕内显示的视图
	private void removeNonVisibleItems(final int dx) {
		View child = getChildAt(0);
		//超出屏幕左边视图：整个子视图超出
		while (child != null && child.getRight() + dx <= 0) {
			mDisplayOffset += child.getMeasuredWidth();
			mRemovedViewQueue.offer(child); //删除一个就添加进删除队列
			removeViewInLayout(child); //把子视图从总布局首端移除
			mLeftViewIndex++;
			child = getChildAt(0); //获取移除后的首个子视图
		}

		child = getChildAt(getChildCount() - 1);
		//整个超出屏幕右边视图
		while (child != null && child.getLeft() + dx >= getWidth()) {
			mRemovedViewQueue.offer(child);
			removeViewInLayout(child);
			mRightViewIndex--;
			child = getChildAt(getChildCount() - 1);
		}
	}

	private void positionItems(final int dx) {
		if (getChildCount() > 0) {
			mDisplayOffset += dx;
			int left = mDisplayOffset;
			//依次挨着前一个子视图布局
			for (int i = 0; i < getChildCount(); i++) {
				View child = getChildAt(i);
				int childWidth = child.getMeasuredWidth();
				child.layout(left, 0, left + childWidth, child.getMeasuredHeight());
				left += childWidth;
			}
		}
	}
	
	//一次位移动画是会调用N次onLayout()方法，具体多少次是根据设置的interpolator和duration以及动画因子计算得出的，属性动画原理
	public void scrollTo(int x) {
		mScroller.startScroll(mNextX, 0, x - mNextX, 0, 200); //这里不是开始位移动画，而是设置位移动画各项参数，在调用了requestLayout后动画开始
		requestLayout();
	}

	protected boolean onDown(MotionEvent e) {
		mScroller.forceFinished(true);
		return true;
	}
}
