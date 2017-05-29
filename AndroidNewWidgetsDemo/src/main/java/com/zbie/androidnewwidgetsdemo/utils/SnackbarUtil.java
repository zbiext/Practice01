package com.zbie.androidnewwidgetsdemo.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.zbie.androidnewwidgetsdemo.R;

/**
 * Created by 涛 on 2017/5/3 0003.
 * 项目名           Practice01
 * 包名             com.zbie.androidnewwidgetsdemo.utils
 * 创建时间         2017/05/03 20:57
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            TODO
 */

public class SnackbarUtil {

    //android-support-design兼容包中新添加的一个类似Toast的控件。
    //make()中的第一个参数，可以写当前界面中的任意一个view对象。
    private static Snackbar mSnackbar;

    public static void show(View view, String msg, int flag) {
        if (flag == 0) { //短时显示
            mSnackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        } else { //长时显示
            mSnackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        }
        mSnackbar.show();
        //Snackbar中有一个可点击的文字，这里设置点击所触发的操作。
        mSnackbar.setAction(R.string.close, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar在点击“关闭”后消失
                mSnackbar.dismiss();
            }
        });
    }
}
