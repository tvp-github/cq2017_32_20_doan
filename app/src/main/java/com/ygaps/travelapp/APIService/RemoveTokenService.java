package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.AppUtils.Message;
import com.ygaps.travelapp.AppUtils.TokenBody;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RemoveTokenService {
    @POST("/user/notification/remove-token")
    Call<Message> sendData(@Header("Authorization") String token,
                           @Body TokenBody tokenBody);
}
