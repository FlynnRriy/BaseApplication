package com.baseapplication.presenter;

import android.os.Handler;
import android.os.Looper;

import com.baseapplication.contract.MyContract;
import com.baseapplication.listener.LoginListener;
import com.baseapplication.model.FirstFragmentModelImpl;
import com.baseapplication.utils.MyLog;

import okhttp3.Response;

public class FirstFragmentPresenterImpl extends MyContract.FirstFragmentPresenter{
    private static final String TAG = "FirstFragmentPresenterImpl";
    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void login() {
        final MyContract.IFirstFragmentView mView = getView();
        if (mView == null) {
            MyLog.e(TAG,"请求失败 mView==null");
            return;
        }
        mView.showLoading();
        mIModel.requestLogin("xxx", "json", new LoginListener() {
            @Override
            public void loginSuccess(Response response) {
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

            }
        });

    }

    @Override
    protected MyContract.IFirstFragmentModel getModel() {
        return new FirstFragmentModelImpl();
    }

    @Override
    public void afterAttach() {

    }
}
