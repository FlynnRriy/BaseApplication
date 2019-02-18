package com.baseapplication.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.baseapplication.R;
import com.baseapplication.baseMVP.BaseMvpFragment;
import com.baseapplication.contract.MyContract;
import com.baseapplication.presenter.FirstFragmentPresenterImpl;
import com.baseapplication.utils.MyLog;

/**
 * 加入防止每次重复加载数据mIsInitData
 * */
public class FirstFragment extends BaseMvpFragment <MyContract.FirstFragmentPresenter> implements MyContract.IFirstFragmentView{
    private static final String TAG = "FirstFragment";
    private boolean mIsInitData = false ;//是否下载数据,下载好时为true

    TextView tv_fragment1;

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_main1;
    }

    @Override
    public void setPresenter() {
        mPresenter = new FirstFragmentPresenterImpl();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();//在这里初始化ui
    }

    private void initView(){
        final SecondFragment secondFragment = new SecondFragment();
        tv_fragment1 =getActivity().findViewById(R.id.tv_fragment1);
        tv_fragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fl_main_fragment_container,secondFragment)
//                        .addToBackStack(null)//返回的话，会先返回first。此方法现实中一般用不到
                        .commit();

            }
        });
    }

    @Override
    protected void initData() {//每次oncreate 时候会调用
        super.initData();
        if(getUserVisibleHint()&&!mIsInitData){
            mPresenter.login();//请求数据
            mIsInitData = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//每次显示时会调用
        super.setUserVisibleHint(isVisibleToUser);
        MyLog.d(TAG,"asdasdasd");
        if(isVisibleToUser){
            mPresenter.login();//请求数据
            mIsInitData = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsInitData = false;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoginSuccess() {
        mIsInitData = true;
    }

    @Override
    public void showLoginFailed() {
        mIsInitData = false;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getUserPassword() {
        return null;
    }
}
