package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.Response.ListTours;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ListToursService {
    @GET("/tour/list")
    Call<ListTours> sendData(@Header("Authorization") String token,
                             @Query("rowPerPage") int rowPerPage,
                             @Query("pageNum") int pageNum,
                             @Query("orderBy") String orderBy,
                             @Query("isDesc") Boolean isDesc
    );
}
