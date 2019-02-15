package com.baseapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.baseapplication.utils.MyLog;
import com.baseapplication.utils.NetStateUtil;

/**
 * 网络状态变化
 * */
public class MyNetStateService extends Service {
    private static final String TAG = "MyNetStateService";
    private int startId;
    private IBinder myBinder;

    public MyNetStateService() {
    }
    @Override
    public void onCreate() {
        MyLog.d(TAG, "onCreate");
        myBinder = new MyNetStateServiceBinder();
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;//每次回调onStartCommand()方法时，参数“startId”的值都是递增的，startId用于唯一标识每次对Service发起的处理请求
        MyLog.d(TAG, "onStartCommand---startId: " + startId);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int control = (Integer) bundle.getSerializable("Key");
            if (control != 0) {
                switch (control) {
                    case 1:
                        break;
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        MyLog.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        MyLog.d(TAG, "onBind");
        return myBinder;
    }

    public class MyNetStateServiceBinder extends Binder {
        public MyNetStateService getService() {
            return MyNetStateService.this;
        }
    }

    public int getNetState() {
        return NetStateUtil.getNetworkState(this);
    }
}
