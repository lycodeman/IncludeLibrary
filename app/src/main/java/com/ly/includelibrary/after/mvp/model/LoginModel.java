package com.ly.includelibrary.after.mvp.model;

import com.ly.includelibrary.after.mvp.contract.LoginContract;
import com.ly.includelibrary.before.User;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class LoginModel {

    /**
     * 处理登录业务并返回结果
     */
    public void login(String name, String password,LoginContract.Model model) {
        //一般登录都是请求服务器，验证
        //这里就简单一点，大家别介意
        if ("liwen".equals(name) && "123456".equals(password)) {
            model.loginSuccess(new User(name, password));//登录成功,给他返回用户对象
        } else {
            model.loginFailure();//登录失败
        }

    }

}
