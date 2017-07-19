package com.ly.rxlibrary.modle.request;

/**
 * Created by Administrator on 2017/6/16/016.
 */

public class LoginRequest {
    /**
     * loginName : string
     * password : string
     */

    private String loginName;
    private String password;

    public LoginRequest(String loginName, String password) {
        this.loginName = loginName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
