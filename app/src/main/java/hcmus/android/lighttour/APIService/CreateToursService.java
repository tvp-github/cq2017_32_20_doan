package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.AppUtils.CreateTourBody;
import hcmus.android.lighttour.Response.Tour;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CreateToursService {
    @Headers("Content-Type: application/json")
    @POST("/tour/create")
    Call<Tour> sendData(@Header("Authorization") String token, @Body CreateTourBody createTourBody);
}
