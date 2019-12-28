package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.Response.RequestOTP;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SendRequestOTPService {
    @POST("/user/request-otp-recovery")
    @FormUrlEncoded
    Call<RequestOTP> sendData(@Field("type") String type,
                              @Field("value") String value);
}
