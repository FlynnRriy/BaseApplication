package com.baseapplication.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.baseapplication.R;
import com.baseapplication.app.MyApp;
import com.baseapplication.baseMVP.BaseMvpActivity;
import com.baseapplication.contract.MyContract;
import com.baseapplication.presenter.LoginPresenterImpl;
import com.baseapplication.utils.MyLog;
import com.baseapplication.utils.ToastUtil;

import java.lang.ref.WeakReference;

public class MainActivity extends BaseMvpActivity<MyContract.LoginPresenter> implements MyContract.ILoginView {
    private static final String TAG = "MainActivity";
    private MyHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new MyHandler(this);
    }
    @Override
    protected int getlayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setPresenter() {
        mPresenter = new LoginPresenterImpl();
    }

    @Override
    protected void initData() {
        super.initData();//这行必须有
        mPresenter.login();
    }

    @Override
    public void showLoading() {
        MyLog.d(TAG,"正在加载中...");
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoginSuccess() {
//        handler  xxxx
    }

    @Override
    public void showLoginFailed() {

    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getUserPassword() {
        return null;
    }

    @Override
    public void onBackPressed() {
        if(!checkBackAction()){
            finish();
            MyApp.getInstance().finishAll();
        }
    }

    //双击退出相关
    private boolean mFlag = false;
    private long mTimeout = -1;
    private boolean checkBackAction() {
        long time = 3000L;//判定时间设为3秒
        boolean flag = mFlag;
        mFlag = true;
        boolean timeout = (mTimeout == -1 || (System.currentTimeMillis() - mTimeout) > time);
        if (mFlag && (mFlag != flag || timeout)) {
            mTimeout = System.currentTimeMillis();
            ToastUtil.ShowShortToast("再点击一次回到桌面");
            return true;
        }
        return !mFlag;
    }

    /**
     * 声明一个静态的Handler内部类，并持有外部类的弱引用
     *
     * Activity 调用ILoginView 中的方法可利用handler
     */
    private static class MyHandler extends Handler {
        private WeakReference<MainActivity> mActivty;

        private MyHandler(MainActivity activity) {
            this.mActivty = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = mActivty.get();
            if (activity != null){
//            if(msg.what==HANDLER_CREATEMYGET){
//                activity.xxxx();
//            }
            }
        }
    }

}
