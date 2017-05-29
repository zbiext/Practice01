package com.zbie.updownpullrecyclerlayout.interaces;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by 涛 on 2017/4/21 0021.
 * 项目名           ZbieGallery
 * 包名             com.zbie.zbiepullrecyclerlayout.interaces
 * 创建时间         2017/04/21 01:03
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            辅助解决事件冲突
 *                http://www.jianshu.com/p/9c59b5c81737
 *                http://www.jianshu.com/p/f267fdbda794 {@link CoordinatorLayout}
 *                // TODO: 2017/04/21 01:15:04 面试被问过 CoordinatorLayout
 */
public interface NestedScrollingParent {

    /**
     * 有嵌套滑动到来了，问下该父View是否接受嵌套滑动
     *
     * @param child            嵌套滑动对应的父类的子类(因为嵌套滑动对于的父View不一定是一级就能找到的，可能挑了两级父View的父View，child的辈分>=target)
     * @param target           具体嵌套滑动的那个子类
     * @param nestedScrollAxes 支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @return 是否接受该嵌套滑动
     */
    boolean onStartNestedScroll(View child, View target, int nestedScrollAxes);

    /**
     * 该父View接受了嵌套滑动的请求该函数调用。onStartNestedScroll返回true该函数会被调用。
     * 参数和onStartNestedScroll一样
     */
    void onNestedScrollAccepted(View child, View target, int nestedScrollAxes);

    /**
     * 停止嵌套滑动
     *
     * @param target 具体嵌套滑动的那个子类
     */
    void onStopNestedScroll(View target);

    /**
     * 嵌套滑动的子View在滑动之后报告过来的滑动情况
     *
     * @param target       具体嵌套滑动的那个子类
     * @param dxConsumed   水平方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dyConsumed   垂直方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dxUnconsumed 水平方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     * @param dyUnconsumed 垂直方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     */
    void onNestedScroll(
            View target,
            int dxConsumed, int dyConsumed,
            int dxUnconsumed, int dyUnconsumed);

    /**
     * 在嵌套滑动的子View未滑动之前告诉过来的准备滑动的情况
     *
     * @param target   具体嵌套滑动的那个子类
     * @param dx       水平方向嵌套滑动的子View想要变化的距离
     * @param dy       垂直方向嵌套滑动的子View想要变化的距离
     * @param consumed 这个参数要我们在实现这个函数的时候指定，回头告诉子View当前父View消耗的距离
     *                 consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离 好让子view做出相应的调整
     */
    void onNestedPreScroll(View target, int dx, int dy, int[] consumed);

    /**
     * 嵌套滑动的子View在fling之后报告过来的fling情况
     *
     * @param target    具体嵌套滑动的那个子类
     * @param velocityX 水平方向速度
     * @param velocityY 垂直方向速度
     * @param consumed  子view是否fling了
     * @return true 父View是否消耗了fling
     */
    boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed);

    /**
     * 在嵌套滑动的子View未fling之前告诉过来的准备fling的情况
     *
     * @param target    具体嵌套滑动的那个子类
     * @param velocityX 水平方向速度
     * @param velocityY 垂直方向速度
     * @return true 父View是否消耗了fling
     */
    public boolean onNestedPreFling(View target, float velocityX, float velocityY);

    /**
     * 获取嵌套滑动的轴
     *
     * @see ViewCompat#SCROLL_AXIS_HORIZONTAL 垂直
     * @see ViewCompat#SCROLL_AXIS_VERTICAL 水平
     * @see ViewCompat#SCROLL_AXIS_NONE 都支持
     */
    public int getNestedScrollAxes();
}
