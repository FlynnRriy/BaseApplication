package com.baseapplication.presenter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.baseapplication.contract.MyContract;
import com.baseapplication.model.LoginModelImpl;
import com.baseapplication.network.listener.LoginListener;
import com.baseapplication.utils.MyLog;


public class LoginPresenterImpl extends MyContract.LoginPresenter {
    private static final String TAG = "LoginPresenter";
    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void login() {
        final MyContract.ILoginView mView = getView();
        if (mView == null) {
            return;
        }
        mView.showLoading();

        String userName = mIViewRef.get().getUserName();
        String userPassword =mIViewRef.get().getUserPassword();

        mIModel.requestLogin(userName, userPassword, new LoginListener() {
            @Override
            public void loginSuccess() {
                if(mView != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.hideLoading();
                            mView.showLoginSuccess();
//                            mView.showToast("this is new mvp activity");
                        }
                    });
                }
            }
            @Override
            public void loginFailed() {
                if (mView != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mView.hideLoading();
                            mView.showLoginFailed();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected LoginModelImpl getModel() {
        return new LoginModelImpl();
    }

    /**
     * IView和IModel绑定完成立即执行
     * <p>
     * 实现类实现绑定完成后的逻辑,例如数据初始化等
     */
    @Override
    public void afterAttach() {
    }
}