package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.Response.SearchStopPoint;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GetSearchStopPointService {
    @GET("/tour/search/service")
    Call<SearchStopPoint> sendData(@Header("Authorization") String token,
                                   @Query("searchKey") String searchKey,
                                   @Query("pageIndex") int pageIndex,
                                   @Query("pageSize") int pageSize
    );
}
