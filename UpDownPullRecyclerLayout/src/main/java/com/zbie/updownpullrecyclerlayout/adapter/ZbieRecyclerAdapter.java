package com.zbie.updownpullrecyclerlayout.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 涛 on 2017/5/3 0003.
 * 项目名           Practice01
 * 包名             com.zbie.updownpullrecyclerlayout.adapter
 * 创建时间         2017/05/03 00:03
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public abstract class ZbieRecyclerAdapter<T> extends RecyclerView.Adapter<ZbieRecyclerAdapter.ZbieViewHolder> {

    private List<T>        list;
    private LayoutInflater inflater;

    public ZbieRecyclerAdapter(Context context, List<T> list) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ZbieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZbieViewHolder(inflater.inflate(getItemLayoutId(viewType), parent, false));
    }

    public abstract int getItemLayoutId(int viewType);

    @Override
    public void onBindViewHolder(ZbieViewHolder holder, int position) {
        bindData(holder, position, list.get(position));
    }

    public abstract void bindData(ZbieViewHolder holder, int position, T item);

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void add(int positon, T item) {
        list.add(positon, item);
        notifyItemInserted(positon);
    }

    public void delete(int positon) {
        list.remove(positon);
        notifyItemRemoved(positon);
    }

    public static class ZbieViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> mViewSparseArray;

        public ZbieViewHolder(View itemView) {
            super(itemView);
            mViewSparseArray = new SparseArray<>();
        }

        private <T extends View> T findViewById(int viewId) {
            View view = mViewSparseArray.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViewSparseArray.put(viewId, view);
            }
            return (T) view;
        }

        public View getView(int viewId) {
            return findViewById(viewId);
        }

        public TextView getTextView(int viewId) {
            return (TextView) getView(viewId);
        }

        public Button getButton(int viewId) {
            return (Button) getView(viewId);
        }

        public ImageView getImageView(int viewId) {
            return (ImageView) getView(viewId);
        }

        public ZbieViewHolder setText(int viewId, String value) {
            TextView view = findViewById(viewId);
            view.setText(value);
            return this;
        }
    }
}
