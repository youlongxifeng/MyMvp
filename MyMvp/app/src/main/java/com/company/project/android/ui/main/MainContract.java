package com.company.project.android.ui.main;

import com.company.project.android.mvp.BaseModel;
import com.company.project.android.mvp.BasePresenter;
import com.company.project.android.mvp.BaseView;

import java.util.Map;

/**
 * @author Administrator
 * @name MyMvp
 * @class name：com.company.project.android.ui.main
 * @class describe
 * @time 2018/2/8 16:56
 * @change
 * @class describe
 */

public interface MainContract {
    interface View extends BaseView {

        void setLogin(String active);


    }

    interface Model extends BaseModel {

        String loginSuccess();//Success, failure


    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract String login(Map<String, String> maps);


    }
}
