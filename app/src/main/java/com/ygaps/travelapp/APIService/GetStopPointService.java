package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.AppUtils.RequestStoppointBody;
import com.ygaps.travelapp.Response.GetStopPoints;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GetStopPointService {
    @Headers("Content-Type: application/json")
    @POST("/tour/suggested-destination-list")
    Call<GetStopPoints> sendData(@Header("Authorization") String token, @Body RequestStoppointBody body);
}
