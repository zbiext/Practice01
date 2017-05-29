package com.zbie.a1keylockscreen.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 涛 on 2017/5/1 0001.
 * 项目名           Practice01
 * 包名             com.zbie.a1keylockscreen.receiver
 * 创建时间         2017/05/01 16:41
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            设备管理广播接收者
 */
public class Dar extends DeviceAdminReceiver {

    public static ComponentName getComponentName(Context context){
        return new ComponentName(context, Dar.class);
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
    }
}
