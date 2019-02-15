package com.baseapplication.model;

import com.baseapplication.baseMVP.BaseModel;
import com.baseapplication.contract.MyContract;
import com.baseapplication.listener.LoginListener;
import com.baseapplication.manager.MyOkHttpManager;
import com.baseapplication.utils.MyLog;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginModelImpl extends BaseModel implements MyContract.ILoginModel {
    private static final String TAG = "LoginModelImpl";
    int serversLoadTimes = 0;
    @Override
    public void requestLogin(String host, String json, final LoginListener loginListener) {
        MyOkHttpManager.getInstance().login(host, json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(e.getCause().equals(SocketTimeoutException.class) && serversLoadTimes<MyOkHttpManager.MAXLOADTIMES)//如果超时并未超过指定次数，则重新连接
                {
                    serversLoadTimes++;
                    MyLog.d(TAG,"requestLogin请求超时，重新发起请求第"+serversLoadTimes+"次请求");
                    MyOkHttpManager.getRegetClient().newCall(call.request()).enqueue(this);
                }else {
                    MyLog.d(TAG,"requestLogin请求失败");
                    e.printStackTrace();
                    loginListener.loginFailed();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                serversLoadTimes = 0;
                loginListener.loginSuccess(response);
            }
        });

    }
}