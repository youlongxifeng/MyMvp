package com.company.project.android.ui.main;

/**
 * @author Administrator
 * @name MyMvp
 * @class name：com.company.project.android.ui.main
 * @class describe
 * @time 2018/2/8 16:56
 * @change
 * @class describe
 */

public class MainModel implements MainContract.Model {

    @Override
    public String loginSuccess() {
        return "成功了";
    }
}
