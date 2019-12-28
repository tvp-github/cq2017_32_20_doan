package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.AppUtils.SendFeedbackBody;
import com.ygaps.travelapp.Response.Message;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SendFeedbackService {
    @Headers("Content-Type: application/json")
    @POST("/tour/add/feedback-service")
    Call<Message> sendData(@Header("Authorization") String token,
                           @Body SendFeedbackBody sendFeedbackBody
    );
}
