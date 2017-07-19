package com.huben.NewRX.modle.response;

/**
 * Created by Administrator on 2017/6/22/022.
 */

public class ErroResponse {

    /**
     * errorCode : {"httpCode":401,"code":401000,"message":"用户未认证"}
     * message : 密码错误
     * title : 密码错误
     */

    private ErrorCodeBean errorCode;
    private String message;
    private String title;

    public ErrorCodeBean getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCodeBean errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class ErrorCodeBean {
        /**
         * httpCode : 401
         * code : 401000
         * message : 用户未认证
         */

        private int httpCode;
        private int code;
        private String message;

        public int getHttpCode() {
            return httpCode;
        }

        public void setHttpCode(int httpCode) {
            this.httpCode = httpCode;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
