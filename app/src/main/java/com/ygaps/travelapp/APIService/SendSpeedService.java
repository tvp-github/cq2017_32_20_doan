package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.AppUtils.Message;
import com.ygaps.travelapp.AppUtils.SendSpeedBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SendSpeedService {
    @POST("/tour/add/notification-on-road")
    Call<Message> sendData(@Header("Authorization") String token,
                           @Body SendSpeedBody sendSpeedBody);
}
