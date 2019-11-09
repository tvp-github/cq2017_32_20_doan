package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.Response.Register;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("/user/register")
    @FormUrlEncoded
    Call<Register> sendData(@Field("password") String password,
                            @Field("fullName") String fullName,
                            @Field("email") String email,
                            @Field("phone") String phone,
                            @Field("address") String address,
                            @Field("dob") String dob,
                            @Field("gender") int gender);
}
