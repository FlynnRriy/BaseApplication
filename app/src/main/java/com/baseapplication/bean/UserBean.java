package com.baseapplication.bean;

/**
 * 用户
 * */
public class UserBean extends BaseBean{
    long expire_at;
    String  token;

    public long getExpire_at() {
        return expire_at;
    }

    public void setExpire_at(long expire_at) {
        this.expire_at = expire_at;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
