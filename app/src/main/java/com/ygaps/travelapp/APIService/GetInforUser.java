package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.Response.UserInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;


public interface GetInforUser {
    @GET("/user/info")
    Call<UserInfo> getinfo(@Header("Authorization") String token);
}
