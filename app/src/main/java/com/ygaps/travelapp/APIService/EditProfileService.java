package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.AppUtils.EditProfileBody;
import com.ygaps.travelapp.Response.EditInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface EditProfileService {
    @POST("/user/edit-info")
    Call<EditInfo>sendInfo(@Header("Authorization") String token,
                           @Body EditProfileBody editProfileBody);
}
