package com.ly.rx2lib.rxhttp.service;



import com.huben.NewRX.modle.response.LogisticsInfo;
import com.huben.NewRX.rxhttp.result.HttpResponseResult;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * <p>Description:
 * <p>
 * <p>Created by Devin Sun on 2017/3/25.
 */

public interface ApiService {



    String BASE_URL = "http://182.92.98.18";

    String CHANEL = "test" + "/";


    /**
     * 12306
     *
     * @return
     */
    @GET("https://kyfw.12306.cn/otn/")
    Observable<String> huoche();


    /**
     * testGet1
     *
     * @return
     */
    @GET("https://publicobject.com/helloworld.txt")
    Observable<String> testGet1();

    /**
     *testGet2
     * @return
     */
    @GET("/muses-rest/java/rest/hotkeywordsearch/"+CHANEL)
    Observable<HttpResponseResult<List<String>>> testGet2();


    /**
     *  post test
     *
     * @param map
     * @return
     */
    @POST("/muses-rest/java/rest/viewlogistics/"+CHANEL)
    Observable<HttpResponseResult<LogisticsInfo>> getLogisticsInfo(@Body Map<String, Object> map);


}
