package com.baseapplication.app;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;

import com.baseapplication.service.MyNetStateService;
import com.baseapplication.utils.MyLog;
import com.baseapplication.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

//切记不要用 instance = new MyApp() 一类的赋值去获取实例，这样你得到的只是一个普通的 Java 类，不会具备任何 Application 的功能！
public class MyApp extends Application {
    private static final String TAG = "MyApp";
    private static MyApp instance;
    public static List<Activity> activities;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        MyLog.d(TAG,"MyApp onCreate");
        super.onCreate();
        instance = this;
    }

    @Override
    public void onTerminate() {
        MyLog.d(TAG,"MyApp onTerminate");
        super.onTerminate();
    }
    //-----------------------------------用于存放我们所有activity的数组--start--------------------
    //向集合中添加一个activity
    public static void addActivity(Activity activity){
        //如果集合为空  则初始化
        if(activities == null){
            activities = new ArrayList<>();
        }
        //将activity加入到集合中
        activities.add(activity);
    }
    //移除已经销毁的Activity
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    //结束掉所有的Activity
    public static void finishAll(){ //循环集合,  将所有的activity finish
        for(Activity activity : activities){
            if(! activity.isFinishing()){
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    //-----------------------------------用于存放我们所有activity的数组--over--------------------
}