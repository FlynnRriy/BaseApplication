package com.baseapplication.presenter;

import android.os.Handler;
import android.os.Looper;

import com.baseapplication.contract.MyContract;
import com.baseapplication.listener.LoginListener;
import com.baseapplication.model.SecondFragmentModelImpl;
import com.baseapplication.utils.MyLog;

import okhttp3.Response;

public class SecondFragmentPresenterImpl extends MyContract.SecondFragmentPresenter {
    private static final String TAG = "SecondFragmentPresenter";
    Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    public void login() {
        final MyContract.ISecondFragmentView mView = getView();
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
    protected MyContract.ISecondFragmentModel getModel() {
        return new SecondFragmentModelImpl();
    }

    @Override
    public void afterAttach() {

    }
}
