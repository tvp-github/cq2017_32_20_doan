package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.Response.LoginGG;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginGGService {
    @POST("/user/login")
    @FormUrlEncoded
    Call<LoginGG> sendData(@Query("accessToken") String accessToken,
                           @Field("something") String something);
}
