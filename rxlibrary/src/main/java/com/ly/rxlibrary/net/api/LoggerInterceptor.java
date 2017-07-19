package com.ly.rxlibrary.net.api;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.ly.rxlibrary.base.application.BaseApplication;
import com.ly.rxlibrary.modle.response.ErroResponse;
import com.ly.rxlibrary.utils.log.LUtils;
import com.ly.rxlibrary.utils.log.LogUtils;
import com.ly.rxlibrary.utils.log.LogUtils;

import java.io.IOException;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 日志打印拦截器
 * Created by zhy on 16/3/1.
 */
public class LoggerInterceptor implements Interceptor {

    public static final String TAG = "=====>>>";
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private Response logForResponse(Response response) {
        try {
            LUtils.e("------------------RESPONSE  START-------------------");
            LogUtils.e(response.toString());
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            LogUtils.e("code : " + clone.code());
            if (!TextUtils.isEmpty(clone.message()))
                LogUtils.e("message : " + clone.message());
            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        LogUtils.e("responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            LogUtils.e("responseBody's content : " + resp);
                            body = ResponseBody.create(mediaType, resp);
                            if (resp.contains("errorCode")&&resp.contains("message")){
/*---------------------------------------此处需定制--------------------------------------------*/
                                ErroResponse erroResponse=
                                        new Gson().fromJson(resp, ErroResponse.class);
                                throw new ApiException(erroResponse.getErrorCode().getCode(),erroResponse.getMessage());
                            }
                            return response.newBuilder().body(body).build();
                        } else {
                            LogUtils.e("responseBody's content : maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }
        } catch (Exception e) {
            LUtils.e("------------------RESPONSE  ERRO-------------------");
            LogUtils.e(e.getMessage());
        }finally {
            LUtils.e("------------------RESPONSE  FINALLY-------------------");
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            LUtils.e("----------------------REQUEST   START----------------------------");
            LogUtils.e("method : " + request.method());
            LogUtils.e("url : " + url);
            if (headers != null && headers.size() > 0) {
                LogUtils.e("headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    LogUtils.e("requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        LogUtils.e("requestBody's content : " + bodyToString(request));
                    } else {
                        LogUtils.e("requestBody's content : maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            LUtils.e("----------------------REQUEST   END----------------------------");
        } catch (Exception e) {
            LUtils.e("----------------------REQUEST   ERRO----------------------------",e);
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")||
                    mediaType.subtype().equals("x-www-form-urlencoded")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
