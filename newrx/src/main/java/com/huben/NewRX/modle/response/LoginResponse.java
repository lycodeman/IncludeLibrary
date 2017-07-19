package com.huben.NewRX.modle.response;

/**
 * Created by Administrator on 2017/6/15/015.
 */

public class LoginResponse {


    /**
     * token : dec98e7bd47f3815ed4b4773563cea84
     * principal : null
     * credentials : dec98e7bd47f3815ed4b4773563cea84
     */

    private String token;
    private Object principal;
    private String credentials;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getPrincipal() {
        return principal;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }
}
