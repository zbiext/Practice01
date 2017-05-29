package com.zbie.flux.actions;

import com.zbie.flux.dispatcher.Dispatcher;

/**
 * Created by 涛 on 2017/5/3 0003.
 * 项目名           Practice01
 * 包名             com.zbie.flux.actions
 * 创建时间         2017/05/03 01:02
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            Flux的ActionCreator模块
 */
public class ActionsCreator {

    private static ActionsCreator instance;
    final          Dispatcher     dispatcher;

    public ActionsCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public static ActionsCreator get(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new ActionsCreator(dispatcher);
        }
        return instance;
    }

    public void sendMessage(String message) {
        dispatcher.dispatch(new MessageAction(MessageAction.ACTION_NEW_MESSAGE, message));
    }
}
