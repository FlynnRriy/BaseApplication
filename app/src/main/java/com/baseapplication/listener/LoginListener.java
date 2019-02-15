package com.baseapplication.listener;

import okhttp3.Response;

public interface LoginListener {
    void loginSuccess(Response response);
    void loginFailed();
}
