package com.baseapplication.model;

import com.baseapplication.baseMVP.BaseModel;
import com.baseapplication.contract.MyContract;
import com.baseapplication.listener.LoginListener;

public class SecondFragmentModelImpl extends BaseModel implements MyContract.ISecondFragmentModel {
    @Override
    public void requestLogin(String host, String json, LoginListener loginListener) {
        //调用okhttp
        loginListener.loginSuccess(null);
    }
}
