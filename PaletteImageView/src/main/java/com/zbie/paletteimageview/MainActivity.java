package com.zbie.paletteimageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.zbie.paletteimageview.view.PaletteImageView;

/**
 * Created by 涛 on 2017/5/5 0005.
 * 项目名           Practice01
 * 包名             com.zbie.paletteimageview
 * 创建时间         2017/05/05 02:06
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public class MainActivity extends AppCompatActivity {

    private SeekBar          mSeekBar;
    private PaletteImageView paletteImageView1, paletteImageView2, paletteImageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);
        paletteImageView1 = (PaletteImageView) findViewById(R.id.palette1);
        paletteImageView2 = (PaletteImageView) findViewById(R.id.palette2);
        paletteImageView3 = (PaletteImageView) findViewById(R.id.palette3);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                paletteImageView1.setCornerRadius(progress);
                paletteImageView2.setCornerRadius(progress);
                paletteImageView3.setCornerRadius(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
