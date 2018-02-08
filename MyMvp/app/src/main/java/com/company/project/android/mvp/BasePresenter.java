package com.company.project.android.mvp;

import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Administrator
 * @name MyMvp
 * @class name：com.company.project.android.mvp
 * @class describe
 * @time 2018/2/8 16:50
 * @change
 * @email：xiezg@doordu.com
 * @class describe
 */

public class BasePresenter<V extends BaseView, M extends BaseModel> {

    protected V mView;
    protected M mModel;



    public void attachView(BaseView view) {
        Log.i("TAG", "=====attachView=" +view.getClass().getName());
    }

    public void detachView() {
        Log.i("TAG", "=====detachView=" );
        if (mView != null) {
            mView = null;
        }
    }

    private CompositeDisposable mCompositeSubscription;

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeDisposable();
        }
        mCompositeSubscription.add(subscription);
    }

    public void unSubscribe() {
        if (mView != null) {
            mView = null;
        }
        if (mCompositeSubscription != null && mCompositeSubscription.isDisposed()) {
            mCompositeSubscription.dispose();
        }
    }

}
