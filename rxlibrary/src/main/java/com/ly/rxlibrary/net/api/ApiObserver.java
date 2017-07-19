package com.ly.rxlibrary.net.api;

import android.util.Log;
import android.widget.Toast;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.ly.rxlibrary.base.application.BaseApplication;
import com.ly.rxlibrary.utils.ContextUtils;
import com.ly.rxlibrary.utils.log.LUtils;

import org.json.JSONException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.EndConsumerHelper;
import io.reactivex.observers.DisposableObserver;


/**
 * @author xp
 *         Create on 16/1/2.
 *         感谢原作者的分享，在此基础上使用rxjava2.0封装
 *         使用DisposableObserver，是自动解除绑定
 *         无需手动解除
 */
public abstract class ApiObserver<T> extends DisposableObserver<T> {
    public ApiObserver() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDisposable(this);
    }

    /**
     * 只要链式调用中抛出了异常都会走这个回调
     */
    @Override
    public void onError(Throwable e) {
        LUtils.e(e.getMessage());
        Log.e("","----------------数据亲求异常解析-------------------");
        if (e instanceof ApiException){
            if (e.getMessage().equals("密码错误")){
                Toast.makeText(ContextUtils.getContext(), "服务器端错误", Toast.LENGTH_SHORT).show();
            }else if (e.getMessage().equals("token过期")){
                Toast.makeText(ContextUtils.getContext(), "认证失败", Toast.LENGTH_SHORT).show();
                //此处添加跳转到登陆界面
//            SPUtils.put("firstLogin", false);
//            AppManager.jumpAndFinish(LoginActivity.class);//跳转到登陆界面

            }else if (e.getMessage().contains("4")){
                Toast.makeText(ContextUtils.getContext(), "参数错误", Toast.LENGTH_SHORT).show();
            }else if (e.getMessage().contains("5")){
                Toast.makeText(ContextUtils.getContext(), "主机错误", Toast.LENGTH_SHORT).show();
            }
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            Toast.makeText(ContextUtils.getContext(), "无法连接服务器", Toast.LENGTH_SHORT).show();
        } else if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
            Toast.makeText(ContextUtils.getContext(), "连接超时，请稍后再试！", Toast.LENGTH_SHORT).show();
        } else if (e instanceof JsonSyntaxException || e instanceof JsonIOException ||
                e instanceof JsonParseException || e instanceof JSONException) {
            Toast.makeText(ContextUtils.getContext(), "解析错误", Toast.LENGTH_SHORT).show();
        } else if (e.getMessage().contains("40")) {
            Toast.makeText(ContextUtils.getContext(), "参数错误", Toast.LENGTH_SHORT).show();
        } else if (e instanceof NullPointerException) {
            Toast.makeText(ContextUtils.getContext(), "数据为空", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ContextUtils.getContext(), "未知错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void getDisposable(Disposable disposable);

}
