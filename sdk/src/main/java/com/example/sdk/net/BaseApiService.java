package com.example.sdk.net;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface BaseApiService {

    @FormUrlEncoded
    @POST()
    Flowable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> map);

    @POST("user/register")
    @FormUrlEncoded
    Flowable<ResponseBody> getRegisterData(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword );

    @POST("user/login")
    @FormUrlEncoded
    Flowable<ResponseBody> getSigninData(@Field("username") String username, @Field("password") String password );

}
