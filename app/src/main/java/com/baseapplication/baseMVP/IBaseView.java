package com.baseapplication.baseMVP;

public interface IBaseView {
    void showToast(String msg);//BaseMvpActivity实现了
    void setPresenter();//要由具体的Activity实现
}
