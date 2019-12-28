package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.AppUtils.CreateTourBody;
import com.ygaps.travelapp.Response.Tour;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CreateToursService {
    @Headers("Content-Type: application/json")
    @POST("/tour/create")
    Call<Tour> sendData(@Header("Authorization") String token, @Body CreateTourBody createTourBody);
}
