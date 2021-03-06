package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.AppUtils.TokenBody;
import com.ygaps.travelapp.Response.Message;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PutToken {
    @POST("/user/notification/put-token")
    Call<Message> sendData(@Header("Authorization") String token,
                           @Body TokenBody tokenBody
    );
}
