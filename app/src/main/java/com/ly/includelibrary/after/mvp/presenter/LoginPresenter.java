package com.ly.includelibrary.after.mvp.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.ly.includelibrary.after.activity.MvpActivity;
import com.ly.includelibrary.after.mvp.contract.LoginContract;
import com.ly.includelibrary.after.mvp.model.LoginModel;
import com.ly.includelibrary.before.User;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class LoginPresenter implements LoginContract.Presenter {

    /**
     * 登录业务实现者，数据处理的操作者
     */
    private LoginModel mLoginModel;

    /**
     * 在构造方法中实例化model对象
     */
    public LoginPresenter() {
        mLoginModel = new LoginModel();

    }


    //视图接口对象
    private LoginContract.View mLoginView;

    /**
     * 绑定View 的方法
     *
     * @param loginView
     */
    public void bind(MvpActivity loginView) {
        mLoginView = loginView;
    }

    /**
     * 登录业务
     */
    public void login() {
        String account = mLoginView.getAccount();
        String password = mLoginView.getPassword();
        Log.e("TAG", "account:" + account + ",password" + password);
        if (checkParameter(account, password)) {
            doSomePrepare();
            //登录 ，需要处理数据，所有要在model中执行
            mLoginModel.login(account, password, new LoginContract.Model() {
                @Override
                public void loginSuccess(User user) {
                    mLoginView.loginSuccess(user);   //在给视图页面返回User对象
                }

                @Override
                public void loginFailure() {
                    mLoginView.showVerifyFailed();//在给视图页面返回验证失败的结果
                }
            });
        }
    }


    /**
     * 做一些准备
     */
    private void doSomePrepare() {
        //这里可以设置按钮不可点击！否则一直点击登录也是不好
    }

    /**
     * 检测参数是否是否为空~~~
     *
     * @param account
     * @param password
     * @return
     */
    private boolean checkParameter(String account, String password) {
        if (TextUtils.isEmpty(account) | TextUtils.isEmpty(password)) {
            mLoginView.showVerifyFailed();//提示错误
            return false;
        } else if (!checkNetwork()) {
            mLoginView.showNetworkError();//提示网络错误
            return false;
        }
        return true;
    }

    /**
     * 检测网络是否可用
     */
    public boolean checkNetwork() {
        return true;//先显示可以联网，实际中要用代码判断
    }

}
