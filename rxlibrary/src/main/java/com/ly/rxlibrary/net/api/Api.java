package com.ly.rxlibrary.net.api;


import com.ly.rxlibrary.constants.Constants;
import com.ly.rxlibrary.modle.request.LoginRequest;
import com.ly.rxlibrary.modle.response.LoginResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/6/28 0028.
 */

public interface Api {

    // 登陆
    @POST(Constants.GROUND_LOGIN )
    Observable<LoginResponse> login(@Body LoginRequest request);

    /* @FormUrlEncoded
    @POST("/cash/getBankName")
    Observable<BaseEntity<BankCardCommon>> getBankName(@FieldMap Map<String, String> params);*/

    /*form "api_version=1" --form "app_id=xxxxxx" --form "app_key=xxxxxx"
    --form "symbolType=1"  --form "bundleId=com.demo.test"
    --form "productVersion=1.0" --form "channel=xxx"
     --form "fileName=mapping.txt" --form "file=@mapping.txt" --verbose*/
    // 上传
    @FormUrlEncoded
    @POST("https://api.bugly.qq.com/openapi/file/upload/symbol")
    Observable<LoginResponse> postBugly(@FieldMap Map<String, String> params);

//
//    // 获取驾驶员和车辆信息
//    @GET(Constans.USER )
//    Observable<UserResponse> getUser(@Query("page") int page, @Header("Authorization") String aouthrization);
//
//    // 地勤任务详情
//    @GET(Constans.FIND_DETAILS +"{id}")
//    Observable<FindDetailsResponse> getFindDetails(@Path("id") int page, @Header("Authorization") String aouthrization);
//
//    // 地勤异常选择驾驶员
//    @GET(Constans.FIND_DRIVER )
//    Observable<FindDriverResponse> getFindDriver(@Header("Authorization") String aouthrization);
//
//    //地勤完成订单提交PUT /api/v1/order/ground_succeed
//    @PUT(Constans.GROUND_SUCCEED)
//    Observable<Void> commitOrder(@Query("ids") int ids, @Header("Authorization") String aouthrization);
//
//    //地勤异常订单提交api/v1/order/modify_Order_status/103?cause=%2222222%22
//    @PUT(Constans.MODIFY_ORDER_STATUS+"{id}")
//    Observable<Void> commitErroOrder(@Path("id") int id, @Query("cause") String cause, @Header("Authorization") String aouthrization);//地勤异常订单提交api/v1/order/modify_Order_status/103?cause=%2222222%22
//
//    //确定调配PUT /api/v1/order/update_order_dricer/{id}
//    @PUT(Constans.CONFIRM_DISPATH + "{id}")
//    Observable<Void> confirmDispath(@Path("id") long id, @Query("userId") long userId, @Header("Authorization") String aouthrization);
//
//    //PUT /api/v1/user/mine，编辑个人信息
//    @PUT(Constans.PERSONAL_GET_INFO)
//    Observable<Void> setUserInfo(@Header("Authorization") String authorization, @Body User_Info_Rrquest userInfoRrquest);
//
//    //修改密码
//    //http://192.168.1.109:8088/tiger/api/v1/user/updatepwd
//    @POST(Constans.PERSONAL_UPDATEPWD)
//    Observable<Void> modifyPassWord(@Body Model_Schema modelSchema, @Header("Authorization") String authorization);
//
//    //PUT /api/v1/user/mine,获取个人信息
//    @GET( Constans.PERSONAL_GET_INFO)
//    Observable<UserInfo> getInfo(@Header("Authorization") String authorization);
//
//    //上传图片
//    @Multipart
//    @POST(Constans.UP_LAOD_IMAG)
//    Observable<LoadImgResponse> upLoadImg(@Part MultipartBody.Part no, @Header("Authorization") String authorization);


}
