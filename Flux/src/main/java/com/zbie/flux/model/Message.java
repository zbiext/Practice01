package com.zbie.flux.model;

/**
 * Created by 涛 on 2017/5/3 0003.
 * 项目名           Practice01
 * 包名             com.zbie.flux.model
 * 创建时间         2017/05/03 01:06
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public class Message {

    private String mText;

    public Message() {
    }

    public Message(String text) {
        mText = text;
    }

    public String getMessage() {
        return mText;
    }

    public void setMessage(String text) {
        mText = text;
    }
}
