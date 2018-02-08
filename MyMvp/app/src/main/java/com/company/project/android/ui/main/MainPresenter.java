package com.company.project.android.ui.main;

import android.util.Log;

import com.company.project.android.mvp.BaseView;

import java.util.Map;

/**
 * @author Administrator
 * @name MyMvp
 * @class name：com.company.project.android.ui.main
 * @class describe
 * @time 2018/2/8 16:57
 * @change
 * @class describe
 */


public class MainPresenter extends MainContract.Presenter   {


    public MainPresenter(MainContract.View view) {
        this.mView= view;
        this.mModel = new MainModel();
    }



    @Override
    public void attachView(BaseView view) {
        Log.i("TAG", "=====attachView=" +view.getClass().getName());
    }

    @Override
    public void detachView() {
        Log.i("TAG", "=====detachView=" );
    }


    @Override
    public String login(Map maps) {
      /*  String name = mModel.loginSuccess();
        */
        Log.i("TAG", "=====active=" + Thread.currentThread().getName());
        String name = mModel.loginSuccess();
        Log.i("TAG", "=====active=" + name+ "  ="+(mView != null));
        if (mView != null) {
            mView.setLogin(name);
        }
        return "";
    }


}
