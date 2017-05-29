package com.zbie.a1keylockscreen;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.zbie.a1keylockscreen.listener.ScreenLockListener;
import com.zbie.a1keylockscreen.receiver.Dar;

public class MainActivity extends Activity {

    private static final String TAG                           = "MainActivity";
    private static final int    REQUEST_CODE_ADD_DEVICE_ADMIN = 10001;

    private DevicePolicyManager mDevicePolicyManager;
    private long                mStart;

    private ScreenLockListener      mL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        mDevicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);

        mL = new ScreenLockListener(this);
        mL.begin(new ScreenLockListener.ScreenStateListener() {
            @Override
            public void onUserPresent() {
                Log.d(TAG, "onUserPresent: ");
            }

            @Override
            public void onScreenOn() {
                Log.d(TAG, "onScreenOn: ");
            }

            @Override
            public void onScreenOff() {
                Log.d(TAG, "onScreenOff: ");
                if (!MainActivity.this.isDestroyed() || MainActivity.this.isFinishing()) {
                    ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                    am.killBackgroundProcesses(MainActivity.this.getPackageName());
                    //mL.unregisterListener();
                }
            }
        });


        if (mDevicePolicyManager.isAdminActive(Dar.getComponentName(this))) {
            //PowerManager                pm       = (PowerManager) getSystemService(Context.POWER_SERVICE);
            //final PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "TAG");
            //wakeLock.acquire();
            ////然后定时
            //new Handler().postDelayed(new Runnable(){
            //    public void run(){
            //        wakeLock.release();//
            //    }
            //}, 20);//延时20ms灭屏

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDevicePolicyManager.lockNow();
                }
            }, 360);
            finish();
            //System.exit(0); //粗暴的方式退出应用
            Log.d(TAG, "onCreate: ture" + this.isFinishing());
            mStart = System.currentTimeMillis();
        } else {
            startAddDeviceAdminAty();
            Log.d(TAG, "onCreate: false" + this.isFinishing());
        }
        Log.d(TAG, "onCreate: 222" + this.isFinishing());
        if (!this.isFinishing()) {
            finish();
        }
        Log.d(TAG, "onCreate: 333" + this.isFinishing());
    }

    private void startAddDeviceAdminAty() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, Dar.getComponentName(this));
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.explanation));
        startActivityForResult(intent, REQUEST_CODE_ADD_DEVICE_ADMIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            mDevicePolicyManager.lockNow();
        } else {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() { //系统销毁一个activity需要一点时间,米4 miui7.3 耗时 9s
        super.onDestroy();
        Log.d(TAG, "onDestroy: 444" + this.isFinishing());
        if (!this.isFinishing()) {
            finish();
        }
        Log.d(TAG, "onDestroy: 555" + this.isFinishing());
        Log.d(TAG, "onDestroy: 耗时 = " + (System.currentTimeMillis() - mStart) + "毫秒");

        //mL.unregisterListener();//其实 可以不需要 ScreenLockListener

        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (km.inKeyguardRestrictedInputMode()) {
            Log.d(TAG, "onDestroy: 已锁");
            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            am.killBackgroundProcesses(MainActivity.this.getPackageName());
            /*--------------- Reflect to force stop ---------------*/
            //
            //    ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            //    Method method = Class.forName("android.app.ActivityManager").getMethod("forceStopPackage", String.class);
            //    method.invoke(am, pkgName);
            //
            // You should add this system permission:
            //     android.permission.FORCE_STOP_PACKAGES
            //
            /*--------------- Reflect to force stop ---------------*/
        } else {
            Log.d(TAG, "onDestroy: 未锁");
        }
    }
}
