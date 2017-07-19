package com.ly.includelibrary.after.mvp.contract;

import com.ly.includelibrary.after.mvp.model.LoginModel;
import com.ly.includelibrary.before.User;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public interface LoginContract {
    interface Model {
        void loginSuccess(User user);//登录成功后回调的方法，返回User对象

        void loginFailure();//登录失败后回掉的方法
    }

    interface View {

        String getAccount();//获取用户的账号，返回账号

        String getPassword();//获取用户的莫玛，返回密码

        void loginSuccess(User user);//登录的实现，需要传入用户对象

        void showNetworkError();//显示网络异常

        void showVerifyFailed();//信息验证失败,账号或密码有误
    }

    interface Presenter {
        void login();
    }
}
