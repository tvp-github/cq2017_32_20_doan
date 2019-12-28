package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.AppUtils.Message;
import com.ygaps.travelapp.AppUtils.UpdateTourBody;
import com.ygaps.travelapp.Response.Tour;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UpdatePassService {
    @POST("/user/update-password")
    Call<Message> sendData(@Header("Authorization") String token,
                           @Query("userId") int userId,
                           @Query("currentPassword") String currentPassword,
                           @Query("newPassword") String newPassword);
}
