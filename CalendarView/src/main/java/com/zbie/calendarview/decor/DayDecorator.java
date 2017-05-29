package com.zbie.calendarview.decor;

import android.support.annotation.NonNull;

import com.zbie.calendarview.widget.DayView;

/**
 * Created by 涛 on 2017/5/5 0005.
 * 项目名           Practice01
 * 包名             com.zbie.calendarview.decor
 * 创建时间         2017/05/05 01:45
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public interface DayDecorator {
    void decorate(@NonNull DayView cell);
}
