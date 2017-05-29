package com.zbie.androidnewwidgetsdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zbie.androidnewwidgetsdemo.R;
import com.zbie.androidnewwidgetsdemo.adapter.ZbieRecyclerAdapter;
import com.zbie.androidnewwidgetsdemo.adapter.ZbieStaggeredViewAdapter;
import com.zbie.androidnewwidgetsdemo.utils.SnackbarUtil;

/**
 * Created by 涛 on 2017/5/3 0003.
 * 项目名           Practice01
 * 包名             com.zbie.androidnewwidgetsdemo.fragment
 * 创建时间         2017/05/03 21:12
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public class ZbieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ZbieRecyclerAdapter.OnItemClickListener, ZbieStaggeredViewAdapter.OnItemClickListener {

    private static final int VERTICAL_LIST   = 0;
    private static final int HORIZONTAL_LIST = 1;
    private static final int VERTICAL_GRID   = 2;
    private static final int HORIZONTAL_GRID = 3;
    private static final int STAGGERED_GRID  = 4;

    private static final int SPAN_COUNT = 2;

    private View                       mView;
    private SwipeRefreshLayout         mSwipeRefreshLayout;
    private RecyclerView               mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private int                      mFlag;
    private ZbieRecyclerAdapter      mZbieRecyclerAdapter;
    private ZbieStaggeredViewAdapter mZbieStaggeredViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);

        mFlag = getArguments().getInt("flag");
        configRecyclerView();

        // 刷新时，指示器旋转后变化的颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.main_blue_light, R.color.main_blue_dark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void configRecyclerView() {
        switch (mFlag) {
            case VERTICAL_LIST:
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL_LIST:
                mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                break;
            case VERTICAL_GRID:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL_GRID:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.HORIZONTAL, false);
                break;
            case STAGGERED_GRID:
                mLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
                break;
        }

        if (mFlag != STAGGERED_GRID) {
            mZbieRecyclerAdapter = new ZbieRecyclerAdapter(getActivity());
            mZbieRecyclerAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mZbieRecyclerAdapter);
        } else {
            mZbieStaggeredViewAdapter = new ZbieStaggeredViewAdapter(getActivity());
            mZbieStaggeredViewAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mZbieStaggeredViewAdapter);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onRefresh() {
        //刷新时模拟数据的变化
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                int temp = (int) (Math.random() * 10);
                if (mFlag != STAGGERED_GRID) {
                    mZbieRecyclerAdapter.getDatas().add(0, "new" + temp);
                    mZbieRecyclerAdapter.notifyDataSetChanged();
                } else {
                    mZbieStaggeredViewAdapter.getDatas().add(0, "new" + temp);
                    mZbieStaggeredViewAdapter.getHeights().add(0, (int) (Math.random() * 300) + 200);
                    mZbieStaggeredViewAdapter.notifyDataSetChanged();
                }
            }
        }, 1000);
    }

    @Override
    public void onItemClick(View view, int position) {
        SnackbarUtil.show(mRecyclerView, getString(R.string.item_clicked), 0);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        SnackbarUtil.show(mRecyclerView, getString(R.string.item_clicked), 0);
    }
}
