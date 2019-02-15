package com.baseapplication.contract;

import com.baseapplication.baseMVP.BasePresenter;
import com.baseapplication.baseMVP.IBaseModel;
import com.baseapplication.baseMVP.IBaseView;
import com.baseapplication.listener.LoginListener;

//通过一个协议接口MyContract来定义ILoginView和ILoginModel，
// 这样也确定了LoginPresenter的IModel和IView的具体类型。
// 由于Presenter、IView和IModel都定义在MyContract，
// 这样一来，presenter方法、view接口方法、model接口方法全部都一目了然。
// 后面如果有功能拓展的话，直接按照这个模式往后面添加就可以了。
//---------------------

public interface MyContract {
    abstract class LoginPresenter extends BasePresenter<ILoginModel, ILoginView> {
        //activity获取数据要调用的方法
        public abstract void login();
    }

    //定义modelImpl要实现的方法
    interface ILoginModel extends IBaseModel {
        void requestLogin(String host, String json, LoginListener loginListener);
    }

    //数据请求和activity的交互（activity和LoginPresenterImpl共同的方法要在此定义）
    interface ILoginView extends IBaseView {
        void showLoading();//去请求
        void hideLoading();//拿到结果
        void showLoginSuccess();
        void showLoginFailed();
        String getUserName();
        String getUserPassword();
    }
}
