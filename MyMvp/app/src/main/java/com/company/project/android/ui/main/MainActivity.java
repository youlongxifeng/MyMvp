package com.company.project.android.ui.main;

import com.company.project.android.R;
import com.company.project.android.base.BaseActivity;
import com.company.project.android.bean.Gank;

public class MainActivity extends BaseActivity<MainPresenter>
        implements MainContract.View {


    @Override
    public MainPresenter setPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public int getlayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initDate() {

    }

    @Override
    public void setLogin(String active) {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void onSucceed(Gank data) {

    }

    @Override
    public void onFail(String err) {

    }

    @Override
    public void hideDialog() {

    }
}
