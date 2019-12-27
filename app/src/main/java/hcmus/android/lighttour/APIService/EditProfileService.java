package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.AppUtils.EditProfileBody;
import hcmus.android.lighttour.Response.EditInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface EditProfileService {
    @POST("/user/edit-info")
    Call<EditInfo>sendInfo(@Header("Authorization") String token,
                           @Body EditProfileBody editProfileBody);
}
