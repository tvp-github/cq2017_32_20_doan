package hcmus.android.lighttour.APIService;

import java.util.Calendar;

import hcmus.android.lighttour.AppUtils.UpdateTourBody;
import hcmus.android.lighttour.Response.Tour;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UpdateTourService {
    @POST("/tour/update-tour")
    Call<Tour> sendData(@Header("Authorization") String token,
                        @Body UpdateTourBody updateTourBody);
}
