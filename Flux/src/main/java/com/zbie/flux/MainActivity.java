package com.zbie.flux;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.zbie.flux.actions.ActionsCreator;
import com.zbie.flux.dispatcher.Dispatcher;
import com.zbie.flux.stores.MessageStore;
import com.zbie.flux.stores.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 涛 on 2017/05/03.
 * 包名             com.zbie.flux
 * 创建时间         2017/05/03 0:56
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            1.mainthread2subthread 使用Handler
 *                2. android flux开发模式
 *                ref: http://www.jianshu.com/p/5aa9cbde299f
 *                https://github.com/androidflux/flux
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final String UPPER_NUM = "upper";
    private EditText  etNum;
    private CalThread calThread;

    //region Flux Demo
    private EditText vMessageEditor;
    private Button   vMessageButton;
    private TextView vMessageView;

    private Dispatcher     dispatcher;
    private ActionsCreator actionsCreator;
    private MessageStore   store;
    //endregion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDependencies();
        setupView();

        calThread = new CalThread();
        //启动新线程
        calThread.start();

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                //super.run();
                Toast.makeText(MainActivity.this, "haha111", Toast.LENGTH_SHORT).show();
                //MainActivity.this.runOnUiThread(new Runnable() {
                //    @Override
                //    public void run() {
                //        Toast.makeText(MainActivity.this, "haha", Toast.LENGTH_SHORT).show();
                //    }
                //});
                Looper.loop();
            }
        }.start();
    }

    private void setupView() {
        etNum = (EditText) findViewById(R.id.etNum);

        //region Flux Demo
        vMessageEditor = (EditText) findViewById(R.id.message_editor);
        vMessageView = (TextView) findViewById(R.id.message_view);
        vMessageButton = (Button) findViewById(R.id.message_button);
        vMessageButton.setOnClickListener(this);
        //endregion
    }

    //为按钮的点击事件提供事件处理函数
    public void cal(View source) {
        //创建消息
        Message msg = new Message();
        msg.what = 0x123;
        Bundle bundle = new Bundle();
        bundle.putInt(UPPER_NUM, Integer.parseInt(etNum.getText().toString()));
        msg.setData(bundle);
        //向新线程中的Handler发送消息
        calThread.mHandler.sendMessage(msg);
    }

    //region Flux Demo
    private void initDependencies() {
        dispatcher = Dispatcher.getInstance();
        actionsCreator = ActionsCreator.get(dispatcher);
        store = new MessageStore();
        dispatcher.register(store);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.message_button) {
            if (vMessageEditor.getText() != null) {
                actionsCreator.sendMessage(vMessageEditor.getText().toString());
                vMessageEditor.setText(null);
            }
        }
    }

    private void render(MessageStore store) {
        vMessageView.setText(store.getMessage());
    }

    @Override
    protected void onResume() {
        super.onResume();
        store.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        store.unregister(this);
    }

    @Subscribe
    public void onStoreChange(Store.StoreChangeEvent event) {
        render(store);
    }
    //endregion

    // 定义一个线程类
    private class CalThread extends Thread {

        public Handler mHandler;

        public void run() {
            Looper.prepare();// 循环消息队列
            mHandler = new Handler() {
                // 定义处理消息的方法
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x123) {
                        int           upper = msg.getData().getInt(UPPER_NUM);
                        List<Integer> nums  = new ArrayList<Integer>();
                        // 计算从2开始、到upper的所有质数
                        outer:
                        for (int i = 2; i <= upper; i++) {
                            // 用i处于从2开始、到i的平方根的所有数
                            for (int j = 2; j <= Math.sqrt(i); j++) {
                                // 如果可以整除，表明这个数不是质数
                                if (i != 2 && i % j == 0) {
                                    continue outer;
                                }
                            }
                            nums.add(i);
                        }
                        // 使用Toast显示统计出来的所有质数
                        Toast.makeText(MainActivity.this, nums.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            };
            Looper.loop();// 知道消息队列循环结束
        }
    }
}
