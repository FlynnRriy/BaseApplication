package com.baseapplication.view.fragment;

import com.baseapplication.R;
import com.baseapplication.baseMVP.BaseMvpFragment;
import com.baseapplication.contract.MyContract;
import com.baseapplication.presenter.SecondFragmentPresenterImpl;

public class SecondFragment extends BaseMvpFragment <MyContract.SecondFragmentPresenter> implements MyContract.ISecondFragmentView{
    @Override
    protected int getlayoutId() {
        return R.layout.fragment_main_2;
    }

    @Override
    public void setPresenter() {
        mPresenter = new SecondFragmentPresenterImpl();
    }
    @Override
    protected void initData() {
        super.initData();
        mPresenter.login();//请求数据
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
