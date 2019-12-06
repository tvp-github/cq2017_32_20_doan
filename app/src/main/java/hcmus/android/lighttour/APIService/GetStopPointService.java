package hcmus.android.lighttour.APIService;
import java.util.List;

import hcmus.android.lighttour.AppUtils.MyBody;
import hcmus.android.lighttour.AppUtils.MyResponse;
import hcmus.android.lighttour.AppUtils.OneCoord;
import hcmus.android.lighttour.Response.Login;
import hcmus.android.lighttour.Response.StopPoint;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetStopPointService {
    @Headers("Content-Type: application/json")
    @POST("/tour/suggested-destination-list")
    Call<MyResponse> sendData(@Header("Authorization") String token, @Body MyBody body);
}
