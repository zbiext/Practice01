package com.zbie.updownpullrecyclerlayout.interaces;

/**
 * Created by 涛 on 2017/4/21 0021.
 * 项目名           ZbieGallery
 * 包名             com.zbie.zbiepullrecyclerlayout.interaces
 * 创建时间         2017/04/21 01:04
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            辅助解决事件冲突
 */
public interface NestedScrollingChild {

    /**
     * 实现该结构的View要调用
     * setNestedScrollingEnabled(true)才可以使用嵌套滚动
     *
     * @param enabled true表示view使用嵌套滚动,false表示禁用.
     */
    void setNestedScrollingEnabled(boolean enabled);

    /**
     * 判断当前view能否使用嵌套滚动
     *
     * @return
     */
    boolean isNestedScrollingEnabled();

    /**
     * view开始滚动了,一般是在ACTION_DOWN中调用
     *
     * @param axes 表示滚动的方向如:
     *             ViewCompat.SCROLL_AXIS_VERTICAL(垂直方向滚动)
     *             ViewCompat.SCROLL_AXIS_HORIZONTAL(水平方向滚动)
     * @return true表示本次滚动父布局支持嵌套滚动, false不支持
     */
    boolean startNestedScroll(int axes);

    /**
     * 在事件结束比如ACTION_UP或者ACTION_CANCLE中调用stopNestedScroll,告诉父布局滚动结束.
     */
    void stopNestedScroll();

    boolean hasNestedScrollingParent();

    /**
     * 把view消费滚动距离之后,把剩下的滑动距离再次传给父布局.
     *
     * @param dxConsumed     表示view消费了x方向的距离长度
     * @param dyConsumed     表示view消费了y方向的距离长度
     * @param dxUnconsumed   表示滚动产生的x滚动距离还剩下多少没有消费
     * @param dyUnconsumed   表示滚动产生的y滚动距离还剩下多少没有消费
     * @param offsetInWindow 表示剩下的距离dxUnconsumed和dyUnconsumed使得view在父布局中的位置偏移了多少
     * @return
     */
    boolean dispatchNestedScroll(
            int dxConsumed, int dyConsumed,
            int dxUnconsumed, int dyUnconsumed,
            int[] offsetInWindow);

    /**
     * 在view消费滚动距离之前把总得滑动距离传给父布局.
     *
     * @param dx             表示view本次x方向的滚动的总距离长度
     * @param dy             表示view本次y方向的滚动的总距离长度
     * @param consumed       表示父布局消费的距离,consumed[0]表示x方向,consumed[1]表示y方向
     * @param offsetInWindow 表示剩下的距离dxUnconsumed和dyUnconsumed使得view在父布局中的位置偏移了多少
     * @return
     */
    boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow);

    /**
     * TODO: 2017/04/21 01:10:44  待分析
     *
     * @param velocityX
     * @param velocityY
     * @param consumed
     * @return
     */
    boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed);

    /**
     * TODO: 2017/04/21 01:10:44  待分析
     *
     * @param velocityX
     * @param velocityY
     * @return
     */
    boolean dispatchNestedPreFling(float velocityX, float velocityY);
}
