package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.Response.Message;
import hcmus.android.lighttour.Response.RequestOTP;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SendVerifyOTPService {
    @POST("/user/verify-otp-recovery")
    @FormUrlEncoded
    Call<Message> sendData(@Field("userId") Integer userId,
                           @Field("newPassword") String newPassword,
                           @Field("verifyCode") String verifyCode);
}
