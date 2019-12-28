package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.AppUtils.CurrentLocationBody;
import com.ygaps.travelapp.Response.UserLocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GetCoordinatesOfMembersService {
    @POST("/tour/current-users-coordinate")
    Call<List<UserLocation>> sendData(@Header("Authorization") String token,
                                      @Body CurrentLocationBody currentLocationBody);
}
