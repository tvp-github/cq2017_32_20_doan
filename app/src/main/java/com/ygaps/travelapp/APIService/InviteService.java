package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.AppUtils.InviteBody;
import com.ygaps.travelapp.AppUtils.Message;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InviteService {
    @POST("/tour/add/member")
    Call<Message> sendData(@Header("Authorization") String token,
                           @Body InviteBody inviteBody);
}
