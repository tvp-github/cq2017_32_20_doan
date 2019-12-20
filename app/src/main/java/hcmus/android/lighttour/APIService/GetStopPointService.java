package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.AppUtils.RequestStoppointBody;
import hcmus.android.lighttour.Response.GetStopPoints;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GetStopPointService {
    @Headers("Content-Type: application/json")
    @POST("/tour/suggested-destination-list")
    Call<GetStopPoints> sendData(@Header("Authorization") String token, @Body RequestStoppointBody body);
}
