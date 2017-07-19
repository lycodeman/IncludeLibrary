package com.ly.rx2lib.rxhttp.manager;


import com.ly.rx2lib.rxhttp.retrofit.RetrofitHelper;
import com.ly.rx2lib.rxhttp.service.ApiService;

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
