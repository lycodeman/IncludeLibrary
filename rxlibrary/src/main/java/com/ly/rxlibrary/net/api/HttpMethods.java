package com.ly.rxlibrary.net.api;

import com.ly.rxlibrary.constants.Constants;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author xp
 * 网络请求类
 */
public class HttpMethods {

    private static final long DEFAULT_TIMEOUT = 10_000L;

    private Retrofit retrofit;
    private OkHttpClient client;

    /*创建单例HttpMethods*/
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods(Constants.BASE_URL);
    }
    public HttpMethods() {
        new HttpMethods(Constants.BASE_URL);
    }

    public HttpMethods(String BASE_URL) {
        client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
//                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new LoggerInterceptor("===", true))//添加打印插值器，打印请求信息
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())//创建gson转换工厂
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//创建回掉转换工厂
                .build();
    }

    public OkHttpClient getClient() {
        return client;
    }

    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public <T> T createService(Class<T> clazz) {
        return retrofit.create(clazz);
    }

}
