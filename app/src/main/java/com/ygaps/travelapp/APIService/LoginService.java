package com.ygaps.travelapp.APIService;


import com.ygaps.travelapp.Response.Login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @POST("/user/login")
    @FormUrlEncoded
    Call<Login> sendData(@Field("emailPhone") String emailPhone,
                         @Field("password") String password);
}
