package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.Response.Message;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SendTextNotificationService {
    @POST("/tour/notification")
    @FormUrlEncoded
    Call<Message> sendData(@Header("Authorization") String token,
                           @Field("tourId") String tourId,
                           @Field("userId") String userId,
                           @Field("noti") String noti);
}
