package com.baseapplication.manager;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MyOkHttpManager {
    private static final String TAG = "MyOkHttpManager";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static MyOkHttpManager mInstance;
    private OkHttpClient mOkHttpClient;
    private static OkHttpClient mRegetClient;//申请失败时 ，重复申请的client

    public static int MAXLOADTIMES = 3;//重复申请次数

    private MyOkHttpManager() {
        //设置超时，取代mOkHttpClient = new OkHttpClient();
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }
    public static MyOkHttpManager getInstance() {
        if (mInstance == null){
            synchronized (MyOkHttpManager.class) {
                if (mInstance == null) {
                    mInstance = new MyOkHttpManager();
                }
            }
        }
        return mInstance;
    }
    //另一个请求对象
    public  static OkHttpClient getRegetClient(){
        if(mRegetClient == null){
            mRegetClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();
        }
        return mRegetClient;
    }

    ///////////////////////////////////////////以下是具体接口///////////////////////////////////////
    // 登陆
    public void login(String url, String json,okhttp3.Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        mOkHttpClient.newCall(request).enqueue(callback);
    }



}
