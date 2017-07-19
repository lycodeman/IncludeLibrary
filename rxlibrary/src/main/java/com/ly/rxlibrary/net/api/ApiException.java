package com.ly.rxlibrary.net.api;

/**
 * @author xp
 *
 */
public class ApiException extends IllegalArgumentException {

    private int erroCode;
    private String erroMessege;

    public ApiException(int code, String msg) {
        super(msg);
        this.erroCode = code;
    }

    public int getCode() {
        return erroCode;
    }
    public String getMessege() {
        return erroMessege;
    }
}
