package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.Response.ListTours;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface HistoryTourService {
    @GET("/tour/history-user")
    Call<ListTours> sendData(@Header("Authorization") String token,
                             @Query("pageIndex") int pageIndex,
                             @Query("pageSize") int pageSize
    );
}
