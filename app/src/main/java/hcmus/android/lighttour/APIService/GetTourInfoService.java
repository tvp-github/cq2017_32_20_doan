package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.Response.TourInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetTourInfoService {
    @GET("/tour/info")
    Call<TourInfo> sendData(@Query("tourId") int tourId
    );
}
