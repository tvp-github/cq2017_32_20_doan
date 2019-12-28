package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.AppUtils.ListReview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GetTourReviewService {
    @GET("/tour/get/review-list")
    Call<ListReview> sendData(@Header("Authorization") String token,
                              @Query("tourId") int tourId,
                              @Query("pageIndex") int pageIndex,
                              @Query("pageSize") String pageSize
    );
}
