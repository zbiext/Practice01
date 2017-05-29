package com.zbie.androidnewwidgetsdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zbie.androidnewwidgetsdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 涛 on 2017/5/3 0003.
 * 项目名           Practice01
 * 包名             com.zbie.androidnewwidgetsdemo.adapter
 * 创建时间         2017/05/03 21:50
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public class ZbieStaggeredViewAdapter extends RecyclerView.Adapter<ZbieStaggeredViewAdapter.ZbieViewHolder> {

    private LayoutInflater      mLayoutInflater;
    private List<String>        mDatas;
    private List<Integer>       mHeights;
    private OnItemClickListener mOnItemClickListener;

    public ZbieStaggeredViewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mDatas = new ArrayList<>();
        mHeights = new ArrayList<>();

        for (int i = 'A'; i <= 'z'; i++) {
            mDatas.add((char) i + "");
        }

        for (int i = 0; i < mDatas.size(); i++) {
            mHeights.add((int) (Math.random() * 300) + 200);
        }
    }

    @Override
    public ZbieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZbieViewHolder(mLayoutInflater.inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(final ZbieViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position);
                    return true;
                }
            });
        }

        ViewGroup.LayoutParams mLayoutParams = holder.mTextView.getLayoutParams();
        mLayoutParams.height = mHeights.get(position);
        holder.mTextView.setLayoutParams(mLayoutParams);

        holder.mTextView.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    public List<String> getDatas() {
        return mDatas;
    }

    public List<Integer> getHeights() {
        return mHeights;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static class ZbieViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public ZbieViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}
