package com.company.project.android.ui.main;

import android.util.Log;

import com.company.project.android.bean.Gank;
import com.company.project.android.mvp.BaseView;
import com.company.project.android.mvp.rx.RxSchedulers;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * @author Administrator
 * @name MyMvp
 * @class name：com.company.project.android.ui.main
 * @class describe
 * @time 2018/2/8 16:57
 * @change
 * @class describe
 */


public class MainPresenter extends MainContract.Presenter {


    public MainPresenter(MainContract.View view) {
        this.mView = view;
        this.mModel = new MainModel();
    }


    @Override
    public void attachView(BaseView view) {
        Log.i("TAG", "=====attachView=" + view.getClass().getName());
    }

    @Override
    public void detachView() {
        Log.i("TAG", "=====detachView=");
    }


    @Override
    public String login(Map maps) {
      /*  String name = mModel.loginSuccess();
        */
        Log.i("TAG", "=====active=" + Thread.currentThread().getName());
        String name = mModel.loginSuccess();
        Log.i("TAG", "=====active=" + name + "  =" + (mView != null));
        if (mView != null) {
            mView.setLogin(name);
        }
        return "";
    }

    private Observable<Boolean> getObservable() {
        return Observable.just(true);
    }


    private CompositeDisposable mDisposables = new CompositeDisposable();

    @Override
    public void getGank() {
        DisposableObserver<Gank> mObserver = getDisposableObserver();

        // getObservable()//被观察者
       /* Disposable disposable=mModel.getGank()
              .compose(RxSchedulers.<Gank>switchObservableThread())
               .subscribe(mObserver,  new RxException<Throwable> );*/
        //  .subscribe(mObserver);
        mModel.getGank()
              .compose(RxSchedulers.<Gank>switchObservableThread())
              .subscribe(mObserver);
        addSubscribe(mObserver);

    }

    private DisposableObserver<Gank> getDisposableObserver() {
        return new DisposableObserver<Gank>() {
            @Override
            public void onNext(Gank aBoolean) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
