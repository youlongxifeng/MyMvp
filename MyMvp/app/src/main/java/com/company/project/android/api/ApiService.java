package com.company.project.android.api;

import com.company.project.android.bean.Gank;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Administrator
 * @name MyMvp
 * @class name：com.company.project.android.api
 * @class describe
 * @time 2018/2/8 17:14
 * @change
 * @class describe
 */

public interface ApiService {


    //http://gank.io/api/data/Android/10/1
    @GET("api/data/Android/10/{page}")
    Observable<Gank> getGank(@Path("page") String page);
}
