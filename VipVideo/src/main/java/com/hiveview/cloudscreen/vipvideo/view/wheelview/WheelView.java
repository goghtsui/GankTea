package com.hiveview.cloudscreen.vipvideo.view.wheelview;

import java.util.concurrent.atomic.AtomicBoolean;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.AdapterView;
import android.widget.Scroller;

public class WheelView extends AdapterView<BaseWheelAdapter> implements OnLayoutChangeListener {

	private static final String TAG = "WheelView";
	private int duration = 300;
	private BaseWheelAdapter adapter;
	private Scroller scroller;
	private TypeInterpolator interpolator;
	private int currentItem = 0;
	private AtomicBoolean isOnLayout = new AtomicBoolean(false);
	private int startScrollCoord = 0;
	private int endScrollCoord = 0;

	private int startCoord = 0;

	private int endCoord = 0;

	private int selectionCoord = 0;

	private View selectedView;
	private View preView;
	private View nextView;
	private int preItemPosition = 0;
	private int nextItemPosition = 0;

	private float startScrollPercent = 0.5f;

	private float endScrollPercent = 0.5f;

	private float startPercent = 0.5f;

	private float endPercent = 0.5f;

	private float selectionPercent = 0.5f;


	private int lastOffset = 0;


	private boolean isHorizontal = false;

	private final DataSetObserver dataSetObserver = new DataSetObserver() {
		@Override
		public void onChanged() {
			synchronized (WheelView.this) {
				Log.d(TAG, "WheelView$DataSetObserver --after notifyDataSetChanged-- onChanged");
				isOnLayout.set(true);
				removeAllViewsInLayout();
				setScrollY(0);
				setScrollX(0);
				requestLayout();
			}
		}

		@Override
		public void onInvalidated() {
			Log.d(TAG, "onInvalidated");
		}
	};

	public WheelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public WheelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public WheelView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		interpolator = new TypeInterpolator();
		scroller = new Scroller(context);
	}

	@Override
	public BaseWheelAdapter getAdapter() {
		return adapter;
	}

	@Override
	public void setAdapter(BaseWheelAdapter adapter) {
		this.adapter = adapter;
		adapter.registerDataSetObserver(dataSetObserver);
	}

	@Override
	public View getSelectedView() {
		return selectedView;
	}

	public void setInterpolatorType(int type) {
		interpolator.setInterpolatorType(type);
	}


	public void setStartScrollPosition(float percent) {
		startScrollPercent = percent;
	}


	public void setEndScrollPosition(float percent) {
		endScrollPercent = percent;
	}


	public void resetStartScrollPosition(float percent) {
		int measuredSize = 0;
		if (isHorizontal) {
			measuredSize = getMeasuredWidth();
		} else {
			measuredSize = getMeasuredHeight();
		}
		startScrollPercent = percent;
		startScrollCoord = (int) (measuredSize * startScrollPercent);
	}


	public void resetEndScrollPosition(float percent) {
		int measuredSize = 0;
		if (isHorizontal) {
			measuredSize = getMeasuredWidth();
		} else {
			measuredSize = getMeasuredHeight();
		}
		endScrollPercent = percent;
		endScrollCoord = (int) (measuredSize * endScrollPercent);
	}

	public void resetStartPosition(float percent) {
		int measuredSize = 0;
		if (isHorizontal) {
			measuredSize = getMeasuredWidth();
		} else {
			measuredSize = getMeasuredHeight();
		}
		startPercent = percent;
		startCoord = (int) (measuredSize * startPercent);
	}

	public void resetEndPosition(float percent) {
		int measuredSize = 0;
		if (isHorizontal) {
			measuredSize = getMeasuredWidth();
		} else {
			measuredSize = getMeasuredHeight();
		}
		endPercent = percent;
		endCoord = (int) (measuredSize * endPercent);
	}


	public void setStartPosition(float percent) {
		startPercent = percent;
	}


	public void setEndPosition(float percent) {
		endPercent = percent;
	}


	public void setSelectionPosition(float percent) {
		selectionPercent = percent;
	}


	public void setHorizontal(boolean isHorizontal) {
		this.isHorizontal = isHorizontal;
	}

	public void setDuration(int scrollDuration) {
		duration = scrollDuration;
	}


	public int getCurrentItem() {
		return currentItem;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (getChildCount() <= 0) {
			fillList();
		}
	}


	private int selectionPositionAfterDataSetChanged;


	public void setSelectionAfterDataSetChanged(int position) {
		selectionPositionAfterDataSetChanged = position;
	}


	@Override
	public synchronized void setSelection(int position) {
		if (null == adapter || null == selectedView) {
			Log.d(TAG, "null == adapter : " + (null == adapter) + "   null == selectedView : " + (null == selectedView));
			return;
		}

		lastTime = System.currentTimeMillis();
		if (null != onSelectionChangedListener) {
			onSelectionChangedListener.unSelected(selectedView);
		}
		if (null != onSelectedChangedListener) {
			onSelectedChangedListener.onChanged(this, selectedView, position, false);
		}
		View view = getChildAt(position);
		int currentViewPosition = 0;
		if (isHorizontal) {
			if (null != selectedView) {
				currentViewPosition = selectedView.getLeft() + selectedView.getMeasuredWidth() / 2;
			}
		} else {
			if (null != selectedView) {
				currentViewPosition = selectedView.getTop() + selectedView.getMeasuredHeight() / 2;
			}
		}
		if (view != null) {
			int targetViewCenter = 0;
			if (isHorizontal) {
				targetViewCenter = view.getLeft() + view.getMeasuredWidth() / 2;
			} else {
				targetViewCenter = view.getTop() + view.getMeasuredHeight() / 2;
			}
			adapter.preView(selectedView);
			adapter.currentView(view);

			int lastCurrentItem = currentItem;
			currentItem = position;
			nextItemPosition = position + 1;
			preItemPosition = position - 1;
			getView();

			int selectedViewCenterPosition = 0;
			int selectViewEnd = 0;
			if (isHorizontal) {
				selectViewEnd = selectedView.getMeasuredWidth();
				selectedViewCenterPosition = selectedView.getLeft() + selectViewEnd / 2 - getScrollX();
			} else {
				selectViewEnd = selectedView.getMeasuredHeight();
				selectedViewCenterPosition = selectedView.getTop() + selectViewEnd / 2 - getScrollY();
			}
			if (currentItem > lastCurrentItem || selectedViewCenterPosition > startCoord) {
				Log.d(TAG, "moveDown dy : " + (currentViewPosition - targetViewCenter));
				Log.d(TAG, "moveDown getScrollY : " + getScrollY() + "  currentViewPositionY : " + currentViewPosition + "  targetViewCenterY : "
						+ targetViewCenter);
				moveToEnd(targetViewCenter - currentViewPosition);
			} else if (currentItem < lastCurrentItem || selectedViewCenterPosition < startCoord) {
				Log.d(TAG, "moveUp dy : " + (currentViewPosition - targetViewCenter));
				Log.d(TAG, "moveDown getScrollY : " + getScrollY() + "  currentViewPositionY : " + currentViewPosition + "  targetViewCenterY : "
						+ targetViewCenter);
				moveToStart(currentViewPosition - targetViewCenter + selectViewEnd);
			} else {

			}
			selectionPositionAfterDataSetChanged = 0;
			if (null != onSelectionChangedListener) {
				onSelectionChangedListener.selected(selectedView);
			}
			if (null != onSelectedChangedListener) {
				onSelectedChangedListener.onChanged(this, selectedView, position, true);
			}
		}

	}

	private void moveToStart(int distance) {
		Log.d(TAG, "moveToStart distance="+distance);
		if (null == adapter) {
			return;
		}
		int firstItemStart = 0;
		int selectedViewCenterPosition = 0;
		int selectViewEnd = 0;
		if (isHorizontal) {
			selectViewEnd = selectedView.getMeasuredWidth();
			View firstView = getChildAt(0);
			if (null != firstView) {
				firstItemStart = getChildAt(0).getLeft() - getScrollX() + getChildAt(0).getMeasuredWidth() / 2; // 计算第一个Item位置
			} else {
				return;
			}
			selectedViewCenterPosition = selectedView.getLeft() + selectViewEnd / 2 - getScrollX();
		} else {
			selectViewEnd = selectedView.getMeasuredHeight();
			View firstView = getChildAt(0);
			if (null != firstView) {
				firstItemStart = getChildAt(0).getTop() - getScrollY() + getChildAt(0).getMeasuredHeight() / 2;
			} else {
				return;
			}
			selectedViewCenterPosition = selectedView.getTop() + selectViewEnd / 2 - getScrollY();
		}
		if (firstItemStart < startCoord) {
			if (selectedViewCenterPosition >= startScrollCoord) {
				Log.d(TAG, "if distance " + distance);
				if (null != onScrollListener) {
					onScrollListener.beforeScroll(this, selectedView, -distance, SCROLL_STATE_IDLE, isFastMode);
				}
				return;
			} else if (selectedViewCenterPosition < startScrollCoord && startScrollCoord - selectedViewCenterPosition < selectViewEnd) {
				Log.d(TAG, "elseif distance " + distance);
				if (null != onScrollListener) {
					onScrollListener.beforeScroll(this, selectedView, startScrollCoord - selectedViewCenterPosition, SCROLL_STATE_SCROLLING, isFastMode);
				}
				smoothScroll(startScrollCoord - selectedViewCenterPosition);
			} else {
				Log.d(TAG, "else distance " + distance + " startCoord " + startCoord + " firstItemStart " + firstItemStart);
				if (startCoord - firstItemStart < distance) {
					Log.d(TAG, "if startCoord " + startCoord + " firstItemStart " + firstItemStart + " distance " + distance);
					if (null != onScrollListener) {
						onScrollListener.beforeScroll(this, selectedView, startCoord - firstItemStart, SCROLL_STATE_SCROLLING, isFastMode);
					}
					smoothScroll(startCoord - firstItemStart);
				} else {
					int realDistance = distance;
					if (startScrollCoord - selectedViewCenterPosition < distance) {
						realDistance = startScrollCoord - selectedViewCenterPosition;
						if (startCoord - firstItemStart < realDistance) {
							realDistance = startCoord - firstItemStart;
						}
					}
					if (null != onScrollListener) {
						onScrollListener.beforeScroll(this, selectedView, realDistance, SCROLL_STATE_SCROLLING, isFastMode);
					}
					Log.d(TAG, "else realDistance " + realDistance + " distance " + distance);
					smoothScroll(realDistance);
				}
			}
		} else {
			if (null != onScrollListener) {
				Log.d(TAG, "else beforeScroll");
				onScrollListener.beforeScroll(this, selectedView, -distance, SCROLL_STATE_IDLE, isFastMode);
			}
		}
	}

	private void moveToEnd(int distance) {
		Log.d(TAG, "moveToEnd");
		if (null == adapter) {
			return;
		}
		int lastItemEnd = 0;
		int selectedViewCenterPosition = 0;
		int selectViewEnd = 0;
		measureView(selectedView);
		if (isHorizontal) {
			selectViewEnd = selectedView.getMeasuredWidth();
			Log.d(TAG, "selectedView.getMeasuredWidth() : " + selectedView.getMeasuredWidth());
			View lastView = getChildAt(adapter.getCount() - 1);
			if (null != lastView) {
				lastItemEnd = getChildAt(adapter.getCount() - 1).getRight() - getScrollX() - getChildAt(adapter.getCount() - 1).getMeasuredWidth() / 2;
			} else {
				Log.e(TAG, "moveToEnd null == lastView block scroll");
				return;
			}
			selectedViewCenterPosition = selectedView.getLeft() + selectViewEnd / 2 - getScrollX();
		} else {
			selectViewEnd = selectedView.getMeasuredHeight();
			View lastView = getChildAt(adapter.getCount() - 1);
			if (null != lastView) {
				lastItemEnd = getChildAt(adapter.getCount() - 1).getBottom() - getScrollY() - getChildAt(adapter.getCount() - 1).getMeasuredHeight() / 2;
			} else {
				Log.e(TAG, "moveToEnd null == lastView block scroll");
				return;
			}
			selectedViewCenterPosition = selectedView.getTop() + selectViewEnd / 2 - getScrollY();
		}
		if (lastItemEnd > endCoord) {
			Log.d(TAG, "selectedViewCenterPosition : " + selectedViewCenterPosition + "  endScrollCoord : " + endScrollCoord);
			if (selectedViewCenterPosition <= endScrollCoord) {
				Log.d(TAG, "selectedViewCenterPosition : " + selectedViewCenterPosition + "endScrollCoord : " + endScrollCoord);
				if (null != onScrollListener) {
					onScrollListener.beforeScroll(this, selectedView, distance, SCROLL_STATE_IDLE, isFastMode);
				}
				return;
			} else if (selectedViewCenterPosition > endScrollCoord && selectedViewCenterPosition - endScrollCoord < selectViewEnd) {
				Log.d(TAG, "selectedViewCenterPosition : " + selectedViewCenterPosition + "endScrollCoord : " + endScrollCoord + " selectViewEnd : "
						+ selectViewEnd);
				if (null != onScrollListener) {
					onScrollListener.beforeScroll(this, selectedView, endScrollCoord - selectedViewCenterPosition, SCROLL_STATE_SCROLLING, isFastMode);
				}
				smoothScroll(endScrollCoord - selectedViewCenterPosition);
			} else {
				if (lastItemEnd - endCoord < distance) {
					Log.d(TAG, "lastItemEnd : " + lastItemEnd + " endCoord : " + endCoord + " distance : " + distance);
					if (null != onScrollListener) {
						onScrollListener.beforeScroll(this, selectedView, endCoord - lastItemEnd, SCROLL_STATE_SCROLLING, isFastMode);
					}
					smoothScroll(endCoord - lastItemEnd);
				} else {
					Log.d(TAG, "lastItemEnd : " + lastItemEnd + " endCoord : " + endCoord + " distance : " + distance);
					int realDistance = distance;
					if (selectedViewCenterPosition - endScrollCoord > distance) {
						realDistance = selectedViewCenterPosition - endScrollCoord;
						if (lastItemEnd - endCoord < realDistance) {
							realDistance = lastItemEnd - endCoord;
						}
					}
					if (null != onScrollListener) {
						onScrollListener.beforeScroll(this, selectedView, -realDistance, SCROLL_STATE_SCROLLING, isFastMode);
					}
					Log.d(TAG, "realDistance : " + realDistance);
					smoothScroll(-realDistance);
				}
			}
		} else {
			if (null != onScrollListener) {
				onScrollListener.beforeScroll(this, selectedView, distance, SCROLL_STATE_IDLE, isFastMode);
			}
		}
	}

	private synchronized void fillList() {
		if (null == adapter) {
			return;
		}
		int count = adapter.getCount();
		int measuredSize = 0;
		if (isHorizontal) {
			measuredSize = getMeasuredWidth();
		} else {
			measuredSize = getMeasuredHeight();
		}
		startScrollCoord = (int) (measuredSize * startScrollPercent);
		endScrollCoord = (int) (measuredSize * endScrollPercent);
		startCoord = (int) (measuredSize * startPercent);
		endCoord = (int) (measuredSize * endPercent);
		selectionCoord = (int) (measuredSize * selectionPercent);

		int totalDistance = 0;
		int selectedViewStart = 0;
		View view;
		for (int i = 0; i < count; i++) {
			view = adapter.getView(i, null, this);
			measureView(view);
			if (i == selectionPositionAfterDataSetChanged) {
				selectedView = view;
				Log.d(TAG, "i : " + i);
				currentItem = i;
				if (isHorizontal) {
					selectedViewStart = totalDistance + startCoord - view.getMeasuredWidth() / 2;
				} else {
					selectedViewStart = totalDistance + startCoord - view.getMeasuredHeight() / 2;
				}
				selectedView.addOnLayoutChangeListener(this);
			}
			LayoutParams params = view.getLayoutParams();
			if (null == params) {
				params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			}
			addViewInLayout(view, i, params);
			if (isHorizontal) {
				int itemStart = totalDistance + startCoord - view.getMeasuredWidth() / 2;
				view.layout(itemStart, 0, itemStart + view.getMeasuredWidth(), view.getMeasuredHeight());
				totalDistance += view.getMeasuredWidth();
			} else {
				int itemStart = totalDistance + startCoord - view.getMeasuredHeight() / 2;
				view.layout(0, itemStart, view.getMeasuredWidth(), itemStart + view.getMeasuredHeight());
				totalDistance += view.getMeasuredHeight();
			}
		}

		if (null != selectedView) {
			int selectViewCenter = 0;
			int scrollDistance = 0;
			int lastItemEnd = 0;
			int distance = 0;
			View lastChild = getChildAt(adapter.getCount() - 1);
			if (null == lastChild) {
				Log.e(TAG, "adapter count : " + adapter.getCount());
				return;
			}
			if (isHorizontal) {
				selectViewCenter = selectedViewStart + selectedView.getMeasuredWidth() / 2;
				scrollDistance = getScrollX();
				lastItemEnd = lastChild.getRight();
				distance = getScrollX() + selectionCoord - selectViewCenter;
			} else {
				selectViewCenter = selectedViewStart + selectedView.getMeasuredHeight() / 2;
				scrollDistance = getScrollY();
				lastItemEnd = lastChild.getBottom();
				distance = getScrollY() + selectionCoord - selectViewCenter;
			}
			Log.d(TAG, "scrollDistance : " + scrollDistance + " distance : " + distance  + " selectViewCenter : " + selectViewCenter);
			if (scrollDistance - distance >= 0) {
				Log.d(TAG, "lastItemEnd : " + lastItemEnd + " measuredSize : " + measuredSize);
				if (lastItemEnd - measuredSize > 0) {
					if (lastItemEnd - measuredSize >= -distance) {
						Log.d(TAG, lastItemEnd - measuredSize + "  distance " + -distance);
						if (getScrollY() - distance > lastItemEnd - measuredSize) {
							distance = -(lastItemEnd - measuredSize);
						}
						if (null != onScrollListener) {
							selectedView.removeOnLayoutChangeListener(this);
							onScrollListener.beforeScroll(this, selectedView, distance, SCROLL_STATE_SCROLLING, isFastMode);
						}
						smoothScroll(distance);
					} else if (lastItemEnd - measuredSize < -distance) {
						if (null != onScrollListener) {
							selectedView.removeOnLayoutChangeListener(this);
							onScrollListener.beforeScroll(this, selectedView, -(lastItemEnd - measuredSize), SCROLL_STATE_SCROLLING, isFastMode);
						}
						smoothScroll(-(lastItemEnd - measuredSize));
					}
				}
			} else {
				Log.d(TAG, "wheelView do not scroll, beforeScroll distance : " + distance);
				onScrollListener.beforeScroll(this, selectedView, -distance, SCROLL_STATE_IDLE, isFastMode);
			}
		}
		nextItemPosition = currentItem + 1;
		preItemPosition = currentItem - 1;
		isOnLayout.set(false);
		getView();
		if(null != onDataSetChangedAndDrawCompletedListener){
			onDataSetChangedAndDrawCompletedListener.onDataSetChangedAndDrawCompleted();
		}
	}


	private void measureView(View view) {
		if (null == view) {
			return;
		}
        LayoutParams params = view.getLayoutParams();
        if (null == params) {
            view.measure(MeasureSpec.makeMeasureSpec(LayoutParams.MATCH_PARENT, MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.UNSPECIFIED));
        } else {
            int widthMeasureSpec = 0;
            int heightMeasureSpec = 0;
            if (params.width == LayoutParams.WRAP_CONTENT) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.UNSPECIFIED);
            } else if (params.width == LayoutParams.MATCH_PARENT) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY);
            } else {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(params.width, MeasureSpec.EXACTLY);
            }
            if (params.height == LayoutParams.WRAP_CONTENT) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.UNSPECIFIED);
            } else if (params.height == LayoutParams.MATCH_PARENT) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY);
            } else {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(params.height, MeasureSpec.EXACTLY);
            }
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }
	private long lastTime = 0;
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		Log.i("WheelView", "WheelView dispatchKeyEvent event = " + event );
		if(event.getAction() == KeyEvent.ACTION_UP){
			isFastMode = false;
		}
		else if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (event.getRepeatCount() >= 1) {
				isFastMode = true;
			} else {
				isFastMode = false;
			}
			if(event.getRepeatCount()%2==1){
				Log.i("WheelView", "WheelView dispatchKeyEvent event ------------------------------ " );
				return true;
			}
			if (!isFastMode) {
				if (!isScrollFinished.get() || System.currentTimeMillis() - lastTime < duration) {
					Log.d(TAG, "dispatchKeyEvent return true  !isScrollFinished : " + !isScrollFinished.get()+",dif="+(System.currentTimeMillis() - lastTime));
					return true;
				} else {
					lastTime = System.currentTimeMillis();
				}
			}
			Log.d(TAG, "switch");
			switch (event.getKeyCode()) {
				case KeyEvent.KEYCODE_DPAD_DOWN:
					if (!isHorizontal) {
						moveToEnd();
					}
					break;
				case KeyEvent.KEYCODE_DPAD_UP:
					if (!isHorizontal) {
						moveToStart();
					}
					break;
				case KeyEvent.KEYCODE_DPAD_LEFT:
					if (isHorizontal) {
						moveToStart();
					}
					break;
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					if (isHorizontal) {
						moveToEnd();
					}
					break;
				case KeyEvent.KEYCODE_DPAD_CENTER:
					if (null != selectedView) {
						performItemClick(selectedView, currentItem, selectedView.getId());
					}
					break;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	private synchronized void moveToStart() {
		Log.d(TAG, "moveToStart");
		if (null == adapter) {
			return;
		}
		if (currentItem <= 0 || null == selectedView) {
			return;
		}
		if (null != onSelectedChangedListener) {
			onSelectedChangedListener.onChanged(this, selectedView, currentItem, false);
		}
		currentItem--;
		nextItemPosition = currentItem + 1;
		preItemPosition = currentItem - 1;
		getView();
		if (null != onSelectedChangedListener) {
			onSelectedChangedListener.onChanged(this, selectedView, currentItem, true);
		}
		int scrollDy = 0;
		if (null != preView) {
			measureView(preView);
			if (isHorizontal) {
				scrollDy = preView.getMeasuredWidth();
			} else {
				scrollDy = preView.getMeasuredHeight();
			}
		} else {
			measureView(selectedView);
			if (null == selectedView) {
				Log.d(TAG, "moveToStart null == selectedView");
				scrollDy = 0;
			} else {
				if (isHorizontal) {
					scrollDy = selectedView.getMeasuredWidth();
				} else {
					scrollDy = selectedView.getMeasuredHeight();
				}
			}
		}
		moveToStart(scrollDy);
	}

	private synchronized void moveToEnd() {
		if (null == adapter) {
			return;
		}
		if (currentItem >= adapter.getCount() - 1 || null == selectedView) {
			return;
		}

		if (null != onSelectedChangedListener) {
			onSelectedChangedListener.onChanged(this, selectedView, currentItem, false);
		}
		currentItem++;
		nextItemPosition = currentItem + 1;
		preItemPosition = currentItem - 1;
		getView();
		if (null != onSelectedChangedListener) {
			onSelectedChangedListener.onChanged(this, selectedView, currentItem, true);
		}
		int scrollDy = 0;
		if (null != nextView) {
			measureView(nextView);
			if (isHorizontal) {
				scrollDy = nextView.getMeasuredWidth();
			} else {
				scrollDy = nextView.getMeasuredHeight();
			}
		} else {
			measureView(selectedView);
			if (null == selectedView) {
				Log.d(TAG, "moveToEnd null == selectedView");
				scrollDy = 0;
			} else {
				if (isHorizontal) {
					scrollDy = selectedView.getMeasuredWidth();
				} else {
					scrollDy = selectedView.getMeasuredHeight();
				}
			}
		}
		moveToEnd(scrollDy);
	}

	private void getView() {
		if (null == adapter || isOnLayout.get()) {
			return;
		}
		if (currentItem >= 0 && currentItem < adapter.getCount()) {
			selectedView = getChildAt(currentItem);
			adapter.currentView(selectedView);
		} else {
			Log.e(TAG, "getView selectedView == null currentItem : " + currentItem + "  count : " + adapter.getCount());
			selectedView = null;
		}
		if (nextItemPosition >= 0 && nextItemPosition < adapter.getCount()) {
			nextView = getChildAt(nextItemPosition);
			adapter.nextView(nextView);
		} else {
			nextView = null;
		}
		if (preItemPosition >= 0 && preItemPosition < adapter.getCount()) {
			preView = getChildAt(preItemPosition);
			adapter.preView(preView);
		} else {
			preView = null;
		}

	}

	private AtomicBoolean isScrollFinished = new AtomicBoolean(true);

	@Override
	public synchronized void computeScroll() {
		if (scroller.computeScrollOffset()) {
			isScrollFinished.set(false);
			scrollTo(scroller.getCurrX(), scroller.getCurrY());
			postInvalidate();
			if (null != onScrollListener) {
				onScrollListener.onScrollChanged(this, selectedView, SCROLL_STATE_SCROLLING);
			}
		} else {
			Log.d(TAG, "!computeScrollOffset");
			isScrollFinished.set(true);
			if (null != onScrollListener) {
				onScrollListener.onScrollChanged(this, selectedView, SCROLL_STATE_IDLE);
			}
		}
		super.computeScroll();
	}
	private boolean isFastMode = false;
	public boolean isFastMode(){
		return isFastMode;
	}
	public void setIsFastMode(boolean b){
		isFastMode = b;
	}
	private void smoothScroll(int distance) {
		if (isHorizontal) {
			if (lastOffset == getScrollX() - distance) {
				scrollTo(getScrollX() - distance, 0);
			} else {
				lastOffset = getScrollX() - distance;
				if (isFastMode) {
					scrollBy(-distance, 0);
				} else {
					scroller.startScroll(getScrollX(), 0, -distance, 0, duration);
				}
			}
		} else {
			Log.d(TAG, "lastOffset : " + lastOffset + "  getScrollY " + getScrollY() + "  distance  " + distance);
			if (lastOffset == getScrollY() - distance) {
				scrollTo(0, getScrollY() - distance);
			} else {
				lastOffset = getScrollY() - distance;
				if (isFastMode) {
					scrollBy(0, -distance);
				} else {
					scroller.startScroll(0, getScrollY(), 0, -distance, duration);
					postInvalidate();
				}
			}
		}
	}

	public static final int SCROLL_STATE_IDLE = 100921;
	public static final int SCROLL_STATE_SCROLLING = 100922;

	private OnScrollListener onScrollListener;

	public void setOnScrollListener(OnScrollListener listener) {
		onScrollListener = listener;
	}

	public static interface OnScrollListener {
		public void beforeScroll(View parent, View view, int scrollDy, int nextState, boolean isFastMode);

		public void onScrollChanged(View parent, View view, int scrollState);
	}

	private OnSelectionChangedListener onSelectionChangedListener;

	public void setOnViewSelectedListener(OnSelectionChangedListener listener) {
		onSelectionChangedListener = listener;
	}

	public static interface OnSelectionChangedListener {
		public void unSelected(View view);

		public void selected(View view);
	}

	private OnSelectedChangedListener onSelectedChangedListener;

	public void setOnSelectedChangedListener(OnSelectedChangedListener listener) {
		onSelectedChangedListener = listener;
	}

	public static interface OnSelectedChangedListener {
		public void onChanged(View parent, View view, int position, boolean selected);
	}

	private OnViewLayoutChangeListener onViewLayoutChangeListener;

	public void setOnViewLayoutChangeListener(OnViewLayoutChangeListener listener) {
		onViewLayoutChangeListener = listener;
	}

	public static interface OnViewLayoutChangeListener {
		public void onLayoutChange(View view);
	}

	@Override
	public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
		if (null != onViewLayoutChangeListener) {
			onViewLayoutChangeListener.onLayoutChange(selectedView);
		}
	}

	private OnDataSetChangedAndDrawCompletedListener onDataSetChangedAndDrawCompletedListener;
	public void setonDataSetChangedAndDrawCompletedListener(OnDataSetChangedAndDrawCompletedListener listener){
		this.onDataSetChangedAndDrawCompletedListener = listener;
	}

	public static interface OnDataSetChangedAndDrawCompletedListener {
		public void onDataSetChangedAndDrawCompleted();
	}
}
