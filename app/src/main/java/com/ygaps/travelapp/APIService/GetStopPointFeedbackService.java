package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.AppUtils.ListFeedback;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GetStopPointFeedbackService {
    @GET("/tour/get/feedback-service")
    Call<ListFeedback> sendData(@Header("Authorization") String token,
                                @Query("serviceId") int serviceId,
                                @Query("pageIndex") int pageIndex,
                                @Query("pageSize") String pageSize
    );
}
