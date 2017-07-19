package com.huben.NewRX.rxhttp.rxjava.observer;

import android.support.annotation.CallSuper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.huben.NewRX.rxhttp.result.HttpResponseException;
import com.huben.NewRX.utils.ContextUtils;
import com.huben.NewRX.utils.Toast.ToastUtils;
import com.huben.NewRX.utils.log.LUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * <p>Description:
 * <p>
 * <p>Created by Devin Sun on 2017/3/29.
 */

public abstract class BaseObserver<T> implements Observer<T> {


    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        LUtils.e(e.getMessage());
        Log.e("","----------------数据亲求异常解析-------------------");
        if (e instanceof HttpResponseException){
            HttpResponseException responseException=new HttpResponseException("自定义异常出错",007);
            if (e.getMessage().equals("密码错误")){
                responseException = new HttpResponseException("密码错误", ((HttpResponseException) e).getStatus());
            }else if (e.getMessage().equals("token过期")){
                responseException = new HttpResponseException("认证失败", ((HttpResponseException) e).getStatus());
                //此处添加跳转到登陆界面
//            SPUtils.put("firstLogin", false);
//            AppManager.jumpAndFinish(LoginActivity.class);//跳转到登陆界面
            }else if (e.getMessage().contains("4")){
                responseException = new HttpResponseException("参数错误", ((HttpResponseException) e).getStatus());
            }else if (e.getMessage().contains("5")){
                responseException = new HttpResponseException("主机错误", ((HttpResponseException) e).getStatus());
            }
            onFailed(responseException);
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            ToastUtils.show("无法连接服务器");
        } else if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
            ToastUtils.show("连接超时，请稍后再试！");
        } else if (e instanceof JsonSyntaxException || e instanceof JsonIOException ||
                e instanceof JsonParseException || e instanceof JSONException) {
            ToastUtils.show("解析失败");
        } else if (e.getMessage().contains("40")) {
            ToastUtils.show("参数错误");
        } else if (e instanceof NullPointerException) {
            ToastUtils.show("数据为空");
        } else {
            Toast.makeText(ContextUtils.getContext(), "未知错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onComplete() {
    }

    protected abstract void onSuccess(T t);

    @CallSuper
    protected void onFailed(HttpResponseException responseException) {
        ToastUtils.show(responseException.getMessage() + "(" + responseException.getStatus() + ")");
    }
}
