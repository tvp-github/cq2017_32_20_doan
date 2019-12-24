package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.AppUtils.ListPointStars;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GetReviewPointStarsService {
    @GET("/tour/get/review-point-stats")
    Call<ListPointStars> sendData(@Header("Authorization") String token,
                                  @Query("tourId") int serviceId
    );
}
