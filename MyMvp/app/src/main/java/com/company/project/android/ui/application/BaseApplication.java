package com.company.project.android.ui.application;

import android.app.Application;

/**
 * @author Administrator
 * @name MyMvp
 * @class name：com.company.project.android.ui.application
 * @class describe
 * @time 2018/2/8 17:03
 * @change
 * @class describe
 */

public class BaseApplication extends Application {
    private static BaseApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static BaseApplication getContext() {
        return mContext;
    }
}
