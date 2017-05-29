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
 * 创建时间         2017/05/03 21:13
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public class ZbieRecyclerAdapter extends RecyclerView.Adapter<ZbieRecyclerAdapter.ZbieRecyclerViewHolder> {

    private LayoutInflater      mLayoutInflater;
    private List<String>        mDatas;
    public  OnItemClickListener mOnItemClickListener;

    public ZbieRecyclerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mDatas = new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++) {
            mDatas.add((char) i + "");
        }
    }

    @Override
    public ZbieRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZbieRecyclerViewHolder(mLayoutInflater.inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(final ZbieRecyclerViewHolder holder, final int position) {
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public static class ZbieRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextView;

        public ZbieRecyclerViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}
