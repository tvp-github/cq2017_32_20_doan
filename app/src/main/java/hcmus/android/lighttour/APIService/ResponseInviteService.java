package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.AppUtils.Message;
import hcmus.android.lighttour.AppUtils.ResponseInviteBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ResponseInviteService {
    @POST("http://35.197.153.192:3000/tour/response/invitation")
    Call<Message> sendData(@Header("Authorization") String token,
                           @Body ResponseInviteBody responseInviteBody);
}
