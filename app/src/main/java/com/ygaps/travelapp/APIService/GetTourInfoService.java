package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.Response.Tour;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GetTourInfoService {
    @GET("/tour/info")
    Call<Tour> sendData(@Header("Authorization") String token,
                        @Query("tourId") int tourId
    );
}
