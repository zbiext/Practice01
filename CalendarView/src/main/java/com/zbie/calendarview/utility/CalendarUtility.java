package com.zbie.calendarview.utility;

import android.content.Context;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by 涛 on 2017/5/5 0005.
 * 项目名           Practice01
 * 包名             com.zbie.calendarview.decor
 * 创建时间         2017/05/05 01:46
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public class CalendarUtility {

    public static boolean isSameMonth(Calendar c1, Calendar c2) {
        return !(c1 == null || c2 == null) &&
                (c1.get(Calendar.ERA) == c2.get(Calendar.ERA) &&
                        (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) &&
                        (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)));
    }

    public static boolean isToday(Calendar calendar) {
        return isSameDay(calendar, Calendar.getInstance());
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null)
            throw new IllegalArgumentException("The dates must not be null");
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) &&
                (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)));
    }

    public static Calendar getTodayCalendar(Context context, int firstDayOfWeek) {
        Calendar currentCalendar = Calendar.getInstance(context.getResources().getConfiguration().locale);
        currentCalendar.setFirstDayOfWeek(firstDayOfWeek);
        return currentCalendar;
    }

    public static int getMonthOffset(Calendar currentCalendar, int firstDayOfWeek) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(firstDayOfWeek);
        calendar.setTime(currentCalendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayWeekPosition = calendar.getFirstDayOfWeek();
        int dayPosition          = calendar.get(Calendar.DAY_OF_WEEK);

        if (firstDayWeekPosition == 1) {
            return dayPosition - 1;
        } else {
            if (dayPosition == 1) {
                return 6;
            } else {
                return dayPosition - 2;
            }
        }
    }

    public static int getWeekIndex(int weekIndex, Calendar calendar) {
        int firstDayWeekPosition = calendar.getFirstDayOfWeek();
        if (firstDayWeekPosition == 1) {
            return weekIndex;
        } else {
            if (weekIndex == 1) {
                return 7;
            } else {
                return weekIndex - 1;
            }
        }
    }

    public static String getCurrentMonth(int monthIndex) {
        final Calendar          calendar   = Calendar.getInstance(Locale.getDefault());
        final DateFormatSymbols dateFormat = new DateFormatSymbols(Locale.getDefault());
        calendar.add(Calendar.MONTH, monthIndex);
        return dateFormat.getMonths()[calendar.get(Calendar.MONTH)];
    }
}
