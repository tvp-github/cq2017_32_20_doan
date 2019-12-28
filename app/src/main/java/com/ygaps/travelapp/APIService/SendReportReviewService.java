package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.Response.Message;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SendReportReviewService {
    @POST("/tour/report/review")
    @FormUrlEncoded
    Call<Message> sendData(@Header("Authorization") String token,
                           @Field("reviewId") int reviewId);
}
