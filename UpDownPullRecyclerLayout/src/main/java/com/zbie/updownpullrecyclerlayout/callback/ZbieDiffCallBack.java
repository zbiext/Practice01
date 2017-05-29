package com.zbie.updownpullrecyclerlayout.callback;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by 涛 on 2017/5/2 0002.
 * 项目名           Practice01
 * 包名             com.zbie.updownpullrecyclerlayout.callback
 * 创建时间         2017/05/02 23:24
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            继承DiffUtil.Callback
 */
public class ZbieDiffCallBack<T> extends DiffUtil.Callback {

    private List<T> olddatas;
    private List<T> newDatas;

    public ZbieDiffCallBack(List<T> olddatas, List<T> newDatas) {
        this.olddatas = olddatas;
        this.newDatas = newDatas;
    }

    @Override
    public int getOldListSize() {
        if (olddatas != null) {
            return olddatas.size();
        }
        return 0;
    }

    @Override
    public int getNewListSize() {
        if (newDatas != null) {
            return newDatas.size();
        }
        return 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return olddatas.get(oldItemPosition).equals(newDatas.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return olddatas.get(oldItemPosition).equals(newDatas.get(newItemPosition));
    }
}
