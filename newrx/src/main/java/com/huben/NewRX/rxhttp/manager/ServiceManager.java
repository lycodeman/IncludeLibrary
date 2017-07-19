package com.huben.NewRX.rxhttp.manager;


import com.huben.NewRX.rxhttp.retrofit.RetrofitHelper;
import com.huben.NewRX.rxhttp.service.ApiService;

/**
 * <p>Description:
 *
 * <p>Created by Devin Sun on 2017/3/29.
 */

public class ServiceManager {

    public static ApiService getApiService() {
        return RetrofitHelper.getRetrofit().create(ApiService.class);
    }


}
