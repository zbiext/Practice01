package com.zbie.androidstatusbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 涛 on 2017/05/05.
 * 包名             com.zbie.androidstatusbar
 * 创建时间         2017/05/05 0:40
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            Android 状态栏的分析和实践 http://www.jianshu.com/p/c11b33cc3dcf
 */
public class MainActivity extends AppCompatActivity {

    private static Snackbar mSnackbar;

    private TextView mTv, mTv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BarUtils.setColor(this, Color.parseColor("#ff0000"), 255);
        //BarUtils.setColor(this, Color.WHITE, 254);
        //BarUtils.setTranslucent(this, 255);

        mTv = (TextView) findViewById(R.id.tv_enter);
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(mTv1, "haha", 0);
            }
        });

        mTv1 = (TextView) findViewById(R.id.tv_enter1);
        mTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TitleTranslucentBarActivity.class));
            }
        });
    }

    public static void show(View view, String msg, int flag) {
        if (flag == 0) { // 短时显示
            mSnackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        } else { // 长时显示
            mSnackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        }
        mSnackbar.show();
        // Snackbar中有一个可点击的文字，这里设置点击所触发的操作。
        mSnackbar.setAction(R.string.close, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Snackbar在点击“关闭”后消失
                mSnackbar.dismiss();
            }
        });
    }
}
