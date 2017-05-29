package com.zbie.flux.dispatcher;

import com.zbie.flux.actions.Action;
import com.zbie.flux.stores.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 涛 on 2017/5/3 0003.
 * 项目名           Practice01
 * 包名             com.zbie.flux.dispatcher
 * 创建时间         2017/05/03 01:04
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            Flux的Dispatcher模块
 */
public class Dispatcher {

    private static Dispatcher mInstance;
    private final List<Store> stores = new ArrayList<>();

    private Dispatcher() {
    }

    public static Dispatcher getInstance() {
        if (mInstance == null) {
            synchronized (Dispatcher.class) {
                if (mInstance == null) {
                    mInstance = new Dispatcher();
                }
            }
        }
        return mInstance;
    }

    public void register(final Store store) {
        if (!stores.contains(store)) {
            stores.add(store);
        }
    }

    public void unregister(final Store store) {
        stores.remove(store);
    }

    public void dispatch(Action action) {
        post(action);
    }

    private void post(final Action action) {
        for (Store store : stores) {
            store.onAction(action);
        }
    }
}
