package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.AppUtils.SendReviewBody;
import com.ygaps.travelapp.Response.Message;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SendReviewService {
    @Headers("Content-Type: application/json")
    @POST("/tour/add/review")
    Call<Message> sendData(@Header("Authorization") String token,
                           @Body SendReviewBody sendReviewBody
    );
}
