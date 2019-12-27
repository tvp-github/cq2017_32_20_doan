package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.AppUtils.InviteBody;
import hcmus.android.lighttour.AppUtils.Message;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InviteService {
    @POST("/tour/add/member")
    Call<Message> sendData(@Header("Authorization") String token,
                           @Body InviteBody inviteBody);
}
