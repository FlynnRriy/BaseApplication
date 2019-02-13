package com.baseapplication.model;

import com.baseapplication.baseMVP.BaseModel;
import com.baseapplication.contract.MyContract;
import com.baseapplication.network.listener.LoginListener;

public class LoginModelImpl extends BaseModel implements MyContract.ILoginModel {
    @Override
    public void requestLogin(String userName, String userPassword, LoginListener loginListener) {
//        LoginRequest.login(userName, userPassword, new LoginRequest.LoginListener() {
//            @Override
//            public void onSuccess() {
//                loginListener.loginSuccess();
//            }
//
//            @Override
//            public void onFailed() {
//                loginListener.loginFailed();
//            }
//        });
    }
}