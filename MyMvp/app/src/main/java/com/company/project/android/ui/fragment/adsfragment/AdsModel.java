package com.company.project.android.ui.fragment.adsfragment;

import com.company.project.android.api.ApiEngine;
import com.company.project.android.bean.DataResponse;

import io.reactivex.Observable;

/**
 * @author Administrator
 * @name MyMvp
 * @class name：com.company.project.android.ui.fragment.adsfragment
 * @class describe
 * @time 2018/3/12 17:38
 * @change
 * @class describe
 */


public class AdsModel implements AdsContract.Model {
    @Override
    public Observable<DataResponse> getConfigInfo(String device_id, String version, String ads_support, long timetoken) {
        return ApiEngine.getInstance().getApiService().getConfigInfoApi(device_id,version,ads_support,timetoken);
    }


}