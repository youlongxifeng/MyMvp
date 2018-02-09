package com.company.project.android.ui.main;

import android.widget.TextView;

import com.company.project.android.R;
import com.company.project.android.base.BaseActivity;
import com.company.project.android.bean.Gank;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


public class MainActivity extends BaseActivity<MainPresenter>
        implements MainContract.View {
@BindView(R.id.return_value_name)
    TextView mRvNameTv;

    @Override
    public MainPresenter setPresenter() {
        return new MainPresenter();
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
        mPresenter.getGank();
        Map<String,String>map=new HashMap<>();
        mPresenter.login( map);

    }

    @Override
    public void setLogin(String active) {
        mRvNameTv.setText(active);
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
