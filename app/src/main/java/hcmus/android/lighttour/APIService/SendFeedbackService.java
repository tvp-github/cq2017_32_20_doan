package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.AppUtils.ListFeedback;
import hcmus.android.lighttour.AppUtils.SendFeedbackBody;
import hcmus.android.lighttour.Response.Feedback;
import hcmus.android.lighttour.Response.Message;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SendFeedbackService {
    @Headers("Content-Type: application/json")
    @POST("/tour/add/feedback-service")
    Call<Message> sendData(@Header("Authorization") String token,
                           @Body SendFeedbackBody sendFeedbackBody
    );
}
