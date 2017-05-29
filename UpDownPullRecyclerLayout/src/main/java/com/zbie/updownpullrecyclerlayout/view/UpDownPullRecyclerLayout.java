package com.zbie.updownpullrecyclerlayout.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by 涛 on 2017/5/2 0002.
 * 项目名           Practice01
 * 包名             com.zbie.updownpullrecyclerlayout.view
 * 创建时间         2017/05/02 23:27
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            自定义的带上下拉刷新的 {@link RecyclerView}
 *                  https://github.com/sw950729/SWPullRecyclerLayout
 */
public class UpDownPullRecyclerLayout extends LinearLayout implements NestedScrollingParent {

    private Context                     mContext;
    private NestedScrollingParentHelper mHelper;

    private LinearLayout     mHeaderLayout; //上拉头
    private ZbieRecyclerView mZbieRecyclerView; //中间的Recycler
    private LinearLayout     mFooterLayout; //下拉头

    private OnTouchUpListener mOnTouchUpListener;

    private int     mHeaderHeight; //headerHeight
    private int     mFooterHeight; //headerHeight
    private int     mTotalY;
    private boolean mIsfling;

    private boolean IsRefresh = true;
    private boolean IsLoad    = true;

    public UpDownPullRecyclerLayout(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public UpDownPullRecyclerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public UpDownPullRecyclerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mHelper = new NestedScrollingParentHelper(this);

        mHeaderLayout = new LinearLayout(mContext);
        mZbieRecyclerView = new ZbieRecyclerView(mContext);
        mFooterLayout = new LinearLayout(mContext);

        //设置方向
        setOrientation(VERTICAL);
        mHeaderLayout.setOrientation(VERTICAL);
        mFooterLayout.setOrientation(VERTICAL);

        //UpDownPullRecyclerLayout 添加这三个部分view
        addView(mHeaderLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(mZbieRecyclerView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(mFooterLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public void setZbieRecyclerView(RecyclerView.LayoutManager layoutManager, RecyclerView.Adapter adapter) {
        //mZbieRecyclerView.setLayoutManager(layoutManager);
        mZbieRecyclerView.setMiddleLayoutManager(layoutManager);
        mZbieRecyclerView.setAdapter(adapter);
    }

    public void setZbieRecyclerView(RecyclerView.LayoutManager layoutManager, RecyclerView.Adapter adapter, boolean fixed) {
        mZbieRecyclerView.setMiddleLayoutManager(layoutManager);
        mZbieRecyclerView.setAdapter(adapter);
        mZbieRecyclerView.setHasFixedSize(fixed);// TODO: 2017/04/20 23:54:29 待查这个设置的意义
    }

    /**
     * headerView添加ItemView
     *
     * @param headerView
     * @param headerHeight
     */
    public void addHeaderView(View headerView, int headerHeight) {
        mHeaderHeight = headerHeight;
        mHeaderLayout.removeAllViews();
        mHeaderLayout.addView(headerView);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, headerHeight);
        layoutParams.topMargin = -headerHeight;
        mHeaderLayout.setLayoutParams(layoutParams);
    }

    /**
     * footerView添加ItemView
     *
     * @param footerView
     * @param footerHeight
     */
    public void addFooterView(View footerView, int footerHeight) {
        mFooterHeight = footerHeight;
        mFooterLayout.removeAllViews();
        mFooterLayout.addView(footerView);
        mFooterLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, footerHeight));
    }

    public void setOnTouchUpListener(OnTouchUpListener onTouchUpListener) {
        mOnTouchUpListener = onTouchUpListener;
    }

    public void setScrollTo(int fromY, int toY) {
        smoothScrollTo((float) fromY, (float) toY);
    }

    public void setItemDivider(RecyclerView.ItemDecoration itemDecoration) {
        mZbieRecyclerView.addItemDecoration(itemDecoration);
    }

    public int getTotal() {
        return -mTotalY / 2;
    }

    public void setIsScrollLoad(boolean isScrollLoad) {
        mZbieRecyclerView.isScrollLoad = isScrollLoad;
    }

    public boolean isScrollLoad() {
        return mZbieRecyclerView.isScrollLoad;
    }

    public void setIsScrollRefresh(boolean isScrollRefresh) {
        mZbieRecyclerView.isScrollRefresh = isScrollRefresh;
    }

    public boolean isScrollRefresh() {
        return mZbieRecyclerView.isScrollRefresh;
    }

    public void setRecyclerViewScrollToPosition(int position) {
        mZbieRecyclerView.scrollToPosition(position);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        mHelper.onNestedScrollAccepted(child, target, axes);
    }

    /**
     * parent view intercept child view scroll
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (mTotalY < 0 && mZbieRecyclerView.isOrientation(0) || mTotalY > 0 && mZbieRecyclerView.isOrientation(1)) {
            mIsfling = true;
        }
        if (IsRefresh) {
            if (dy > 0) {
                if (mZbieRecyclerView.isOrientation(0)) {
                    mTotalY += dy;
                    if ((mTotalY / 2) <= 0) {
                        scrollTo(0, mTotalY / 2);
                        consumed[1] = dy;
                    } else {
                        scrollTo(0, 0);
                        consumed[1] = 0;
                    }
                }
                return;
            }
        }
        if (IsLoad) {
            if (dy < 0) {
                if (mZbieRecyclerView.isOrientation(1)) {
                    mTotalY += dy;
                    if ((mTotalY / 2) >= 0) {
                        scrollTo(0, mTotalY / 2);
                        consumed[1] = dy;
                    } else {
                        scrollTo(0, 0);
                        consumed[1] = 0;
                    }
                }
            }
        }
    }

    /**
     * while child view move finish
     * dyUnconsumed < 0 move down
     * dyUnconsumed > 0 move up
     */
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyUnconsumed != 0) {
            mTotalY += dyUnconsumed;
            scrollTo(0, mTotalY / 2);
        }
    }

    @Override
    public void onStopNestedScroll(View child) {
        mHelper.onStopNestedScroll(child);
        if (mOnTouchUpListener != null) {
            mIsfling = false;
            if (getTotal() >= mHeaderHeight) {
                setScrollTo(getTotal(), mHeaderHeight);
                if (!isScrollRefresh()) {
                    setIsScrollRefresh(true);
                    mOnTouchUpListener.OnRefreshing();
                    // 不知道原作者为什么要去掉这两句
                    setIsScrollRefresh(false);
                    setScrollTo(getTotal(), 0);
                }
            } else if (-getTotal() >= mFooterHeight) {
                setScrollTo(getTotal(), -mFooterHeight);
                if (!isScrollLoad()) {
                    setIsScrollLoad(true);
                    mOnTouchUpListener.OnLoading();
                    // 不知道原作者为什么要去掉这两句
                    setIsScrollLoad(false);
                    setScrollTo(getTotal(), 0);
                }
            } else {
                setScrollTo(0, 0);
            }
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return mIsfling;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return mIsfling;
    }

    @Override
    public int getNestedScrollAxes() {
        return mHelper.getNestedScrollAxes();
    }

    private void smoothScrollTo(float fromY, float toY) {
        ValueAnimator animator = ValueAnimator.ofFloat(fromY, toY);
        if (fromY == toY) {
            animator.setDuration(0);
        } else {
            animator.setDuration(300);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                int to = (int) (-(Float) animation.getAnimatedValue());
                scrollTo(0, to);
                mTotalY = to * 2;
            }
        });
        animator.start();
    }

    private class ZbieRecyclerView extends RecyclerView {

        private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
        private GridLayoutManager          mGridLayoutManager;
        private LinearLayoutManager        mLinearLayoutManager;

        private boolean isScrollLoad;
        private boolean isScrollRefresh;

        public ZbieRecyclerView(Context context) {
            super(context);
            initRecyclerView();
        }

        public ZbieRecyclerView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            initRecyclerView();
        }

        public ZbieRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            initRecyclerView();
        }

        private void initRecyclerView() {
            setVerticalFadingEdgeEnabled(false);
            setHorizontalFadingEdgeEnabled(false);
            setVerticalScrollBarEnabled(false);
            setHorizontalScrollBarEnabled(false);
            setOverScrollMode(OVER_SCROLL_NEVER);
            setItemAnimator(new DefaultItemAnimator());
        }

        private void setMiddleLayoutManager(LayoutManager layoutManager) {
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                mStaggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            } else if (layoutManager instanceof GridLayoutManager) {
                mGridLayoutManager = (GridLayoutManager) layoutManager;
            } else if (layoutManager instanceof LinearLayoutManager) {
                mLinearLayoutManager = (LinearLayoutManager) layoutManager;
            }
            setLayoutManager(layoutManager);
            if (!isVertical()) {
                throw new NullPointerException("vertical!");
            }
        }

        /**
         * orientation
         * 0 menas down
         * 1 means up
         */
        private boolean isOrientation(int orientation) {
            if (orientation == 0)
                return isCanPullDown();
            else if (orientation == 1)
                return isCanPullUp();
            return false;
        }

        private boolean isCanPullDown() {
            return !canScrollVertically(-1);
        }

        private boolean isCanPullUp() {
            return !canScrollVertically(1);
        }

        private boolean isVertical() {
            if (mStaggeredGridLayoutManager != null)
                return mStaggeredGridLayoutManager.getOrientation() == StaggeredGridLayoutManager.VERTICAL;
            else if (mGridLayoutManager != null)
                return mGridLayoutManager.getOrientation() == GridLayoutManager.VERTICAL;
            else if (mLinearLayoutManager != null)
                return mLinearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL;
            return false;
        }
    }

    public interface OnTouchUpListener {
        void OnRefreshing();

        void OnLoading();
    }
}
