package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.AppUtils.Message;
import com.ygaps.travelapp.AppUtils.ResponseInviteBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ResponseInviteService {
    @POST("http://35.197.153.192:3000/tour/response/invitation")
    Call<Message> sendData(@Header("Authorization") String token,
                           @Body ResponseInviteBody responseInviteBody);
}
