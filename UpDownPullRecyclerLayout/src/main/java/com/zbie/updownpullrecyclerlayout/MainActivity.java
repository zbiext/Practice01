package com.zbie.updownpullrecyclerlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.zbie.updownpullrecyclerlayout.adapter.DiffAdapter;
import com.zbie.updownpullrecyclerlayout.adapter.NumAdapter;
import com.zbie.updownpullrecyclerlayout.callback.ZbieDiffCallBack;
import com.zbie.updownpullrecyclerlayout.view.UpDownPullRecyclerLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 涛 on 2017/05/29.
 * 包名             com.zbie.updownpullrecyclerlayout
 * 创建时间         2017/05/29 17:55
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * 微博：           hy擦擦(http://weibo.com/u/1994927905)
 * Github:         https://github.com/zbiext
 * CSDN:           http://blog.csdn.net/hyxt2015
 * QQ&WX：         1677208059
 * 描述            Android开发Diffutils打造不一样的recyclerview
 * ref:           http://blog.csdn.net/sw950729/article/details/70052693
 */
public class MainActivity extends AppCompatActivity implements UpDownPullRecyclerLayout.OnTouchUpListener {

    private static final String TAG = "MainActivity";

    private UpDownPullRecyclerLayout mRecyclerLayout;
    private View                     mHeaderView;
    private View                     mFooterView;
    private NumAdapter               mNumAdapter;
    private DiffAdapter              mDiffAdapter;

    private List<String> list;
    private List<String> newlist;
    private int j = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mRecyclerLayout = (UpDownPullRecyclerLayout) findViewById(R.id.recycler);
        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(i + 1 + "");
        }
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.header, null);
        mFooterView = LayoutInflater.from(this).inflate(R.layout.footer, null);
        mRecyclerLayout.addHeaderView(mHeaderView, 100);
        mRecyclerLayout.addFooterView(mFooterView, 100);
        mNumAdapter = new NumAdapter(this, list);
        mDiffAdapter = new DiffAdapter(this, list);
        mRecyclerLayout.setZbieRecyclerView(new LinearLayoutManager(this), mNumAdapter);
        mRecyclerLayout.setOnTouchUpListener(this);
    }

    @Override
    public void OnRefreshing() {
        Log.i(TAG, "OnRefreshing: 正在刷新");
        newlist = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            newlist.add(list.get(i) + "");
        }
        newlist.add(3, list.size() + j + ""); //注意添加顶部部顺数第三的位置
        j++;
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ZbieDiffCallBack<String>(list, newlist), true);
        //利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter
        //别忘了将新数据给Adapter
        list = newlist;
        mNumAdapter.setList(list);
        diffResult.dispatchUpdatesTo(mNumAdapter);
    }

    @Override
    public void OnLoading() {
        Log.i(TAG, "OnLoading: 正在加载");
        newlist = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            newlist.add(list.get(i) + "");
        }
        newlist.add(list.size() - 4, list.size() + j + ""); //注意添加底部倒数第三的位置
        j++;
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ZbieDiffCallBack<String>(list, newlist), true);
        //利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter
        //别忘了将新数据给Adapter
        list = newlist;
        mNumAdapter.setList(list);
        diffResult.dispatchUpdatesTo(mNumAdapter);
        mRecyclerLayout.setRecyclerViewScrollToPosition(list.size() - 1); //mRecyclerLayout自行滚动到最后一个item
    }
}
