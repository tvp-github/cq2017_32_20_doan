package com.ygaps.travelapp.APIService;

import com.ygaps.travelapp.Response.SearchUsers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SearchUserService {
    @GET("/user/search")
    Call<SearchUsers> sendData(@Header("Authorization") String token,
                               @Query("searchKey") String searchKey,
                               @Query("pageIndex") Integer pageIndex,
                               @Query("pageSize") Integer pageSize);

}
