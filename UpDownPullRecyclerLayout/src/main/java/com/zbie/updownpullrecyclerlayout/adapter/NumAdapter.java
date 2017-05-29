package com.zbie.updownpullrecyclerlayout.adapter;

import android.content.Context;

import com.zbie.updownpullrecyclerlayout.R;

import java.util.List;

/**
 * Created by 涛 on 2017/5/3 0003.
 * 项目名           Practice01
 * 包名             com.zbie.updownpullrecyclerlayout.adapter
 * 创建时间         2017/05/03 00:07
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public class NumAdapter extends ZbieRecyclerAdapter<String> {

    private List<String> list;

    public NumAdapter(Context context, List<String> list) {
        super(context, list);
        this.list = list;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item;
    }

    @Override
    public void bindData(ZbieViewHolder holder, int position, String item) {
        holder.getTextView(R.id.text).setText(list.get(position) + "");
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
