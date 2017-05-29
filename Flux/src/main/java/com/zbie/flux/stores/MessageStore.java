package com.zbie.flux.stores;

import com.squareup.otto.Subscribe;
import com.zbie.flux.actions.Action;
import com.zbie.flux.actions.MessageAction;
import com.zbie.flux.model.Message;

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
 * 描述            MessageStore类主要用来维护MainActivity的UI状态
 */
public class MessageStore extends Store {

    private static MessageStore singleton;
    private Message mMessage = new Message();

    public MessageStore() {
        super();
    }

    public String getMessage() {
        return mMessage.getMessage();
    }

    @Override
    @Subscribe
    public void onAction(Action action) {
        switch (action.getType()) {
            case MessageAction.ACTION_NEW_MESSAGE:
                mMessage.setMessage((String) action.getData());
                break;
            default:
        }
        emitStoreChange();
    }


    @Override
    public StoreChangeEvent changeEvent() {
        return new StoreChangeEvent();
    }
}