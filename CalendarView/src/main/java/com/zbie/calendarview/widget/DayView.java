package com.zbie.calendarview.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zbie.calendarview.decor.DayDecorator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by 涛 on 2017/5/5 0005.
 * 项目名           Practice01
 * 包名             com.zbie.calendarview.widget
 * 创建时间         2017/05/05 01:47
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public class DayView extends TextView {

    private List<DayDecorator> mDayDecoratorList;
    private Date               mDate;

    public DayView(Context context) {
        this(context, null, 0);
    }

    public DayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            if (isInEditMode()) {
                return;
            }
        }
    }

    public void bind(Date date, List<DayDecorator> decorators) {
        this.mDayDecoratorList = decorators;
        this.mDate = date;

        final SimpleDateFormat df  = new SimpleDateFormat("d", Locale.getDefault());
        int                    day = Integer.parseInt(df.format(date));
        setText(String.valueOf(day));
    }

    public void decorate() {
        //Set custom mDayDecoratorList
        if (null != mDayDecoratorList) {
            for (DayDecorator decorator : mDayDecoratorList) {
                decorator.decorate(this);
            }
        }
    }

    public Date getDate() {
        return mDate;
    }
}
