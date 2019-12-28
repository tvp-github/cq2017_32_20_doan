package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.Response.ListNotification;
import com.ygaps.travelapp.Response.ListOnRoad;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GetNotificationOnRoadService {
    @GET("/tour/get/noti-on-road")
    Call<ListOnRoad> sendData(@Header("Authorization") String token,
                              @Query("tourId") int tourId,
                              @Query("pageIndex") int pageIndex,
                              @Query("pageSize") int pageSize);
}
