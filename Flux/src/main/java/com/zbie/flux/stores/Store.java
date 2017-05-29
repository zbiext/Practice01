package com.zbie.flux.stores;

import com.squareup.otto.Bus;
import com.zbie.flux.actions.Action;

/**
 * Created by 涛 on 2017/5/3 0003.
 * 项目名           Practice01
 * 包名             com.zbie.flux.stores
 * 创建时间         2017/05/03 01:07
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            Flux的Store模块
 */
public abstract class Store {

    private static final Bus bus = new Bus();

    protected Store() {
    }

    public void register(final Object view) {
        this.bus.register(view);
    }

    public void unregister(final Object view) {
        this.bus.unregister(view);
    }

    void emitStoreChange() {
        this.bus.post(changeEvent());
    }

    public abstract StoreChangeEvent changeEvent();

    public abstract void onAction(Action action);

    public class StoreChangeEvent {
    }
}
