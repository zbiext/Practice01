package com.zbie.flux.actions;

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
 * 描述            TODO
 */
public class Action<T> {

    private final String type;
    private final T      data;

    Action(String type, T data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public T getData() {
        return data;
    }
}