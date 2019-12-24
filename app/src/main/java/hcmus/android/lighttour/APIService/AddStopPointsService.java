package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.AppUtils.AddStopPointsBody;
import hcmus.android.lighttour.AppUtils.Message;
import hcmus.android.lighttour.AppUtils.RequestStoppointBody;
import hcmus.android.lighttour.Response.GetStopPoints;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AddStopPointsService {
    @Headers("Content-Type: application/json")
    @POST("/tour/set-stop-points")
    Call<Message> sendData(@Header("Authorization") String token, @Body AddStopPointsBody body);
}
