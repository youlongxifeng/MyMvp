package com.company.project.android.api;

import android.util.Log;

import com.company.project.android.api.interceptor.NetworkInterceptor;
import com.company.project.android.common.Constant;
import com.company.project.android.ui.application.BaseApplication;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Administrator
 * @name MyMvp
 * @class name：com.company.project.android.api
 * @class describe
 * @time 2018/2/8 17:08
 * @change
 * @class describe
 */

public class ApiEngine {
    private volatile static ApiEngine apiEngine;
    private Retrofit retrofit;

    private ApiEngine() {
        HttpLoggingInterceptor.Logger mLog=   new HttpLoggingInterceptor.Logger(){
            @Override
            public void log(String message) {
                Log.i("TAG","message=="+message);
            }
        };

        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(mLog);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //缓存
        int size = 1024 * 1024 * 100;
        File cacheFile = new File(BaseApplication.getContext().getCacheDir(), "OkHttpCache");
        Cache cache = new Cache(cacheFile, size);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                .addNetworkInterceptor(new NetworkInterceptor())
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public static ApiEngine getInstance() {
        if (apiEngine == null) {
            synchronized (ApiEngine.class) {
                if (apiEngine == null) {
                    apiEngine = new ApiEngine();
                }
            }
        }
        return apiEngine;
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}
