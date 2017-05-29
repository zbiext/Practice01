package com.zbie.updownpullrecyclerlayout.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zbie.updownpullrecyclerlayout.R;

import java.util.List;

/**
 * Created by 涛 on 2017/4/20 0020.
 * 项目名           ZbieGallery
 * 包名             com.zbie.zbiepullrecyclerlayout.adapter
 * 创建时间         2017/04/20 23:13
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            计算Recycler item集 Differ的适配器
 */
public class DiffAdapter extends RecyclerView.Adapter<DiffAdapter.DiffViewHodler> {

    private List<String>   list;
    private LayoutInflater inflater;

    public DiffAdapter(Context context, List<String> list) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public DiffViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiffViewHodler(inflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(DiffViewHodler holder, int position) {
        holder.textView.setText(list.get(position) + "");
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class DiffViewHodler extends RecyclerView.ViewHolder {

        private TextView textView;

        public DiffViewHodler(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }

    }
}
