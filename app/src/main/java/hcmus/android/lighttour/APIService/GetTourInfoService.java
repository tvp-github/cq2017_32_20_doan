package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.Response.Tour;
import hcmus.android.lighttour.Response.TourInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetTourInfoService {
    @GET("/tour/info")
    Call<Tour> sendData(@Header("Authorization") String token,
            @Query("tourId") int tourId
    );
}
