package com.zbie.updownpullrecyclerlayout.callback;

import java.util.List;

/**
 * Created by 涛 on 2017/5/5 0005.
 * 项目名           Practice01
 * 包名             com.zbie.updownpullrecyclerlayout.callback
 * 创建时间         2017/05/05 01:25
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public class LoadDiffCallBack extends ZbieDiffCallBack<String> {


    public LoadDiffCallBack(List<String> olddatas, List<String> newDatas) {
        super(olddatas, newDatas);
    }

    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //Bean   oldItem    = oldList.get(oldItemPosition);
        //Bean   newItem    = newList.get(newItemPosition);
        //Bundle diffBundle = new Bundle();
        //if (!newItem.header.equals(oldItem.header)) {
        //    diffBundle.putString(KEY_HEEDER, newItem.header);
        //}
        //if (!newItem.footer.equals(oldItem.footer)) {
        //    diffBundle.putString(KEY_FOOTER, newItem.footer);
        //}
        //if (diffBundle.size() == 0)
        //return diffBundle;
        return null;
    }
}