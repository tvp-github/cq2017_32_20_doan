package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.AppUtils.AddStopPointsBody;
import com.ygaps.travelapp.AppUtils.Message;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AddStopPointsService {
    @Headers("Content-Type: application/json")
    @POST("/tour/set-stop-points")
    Call<Message> sendData(@Header("Authorization") String token, @Body AddStopPointsBody body);
}
