package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.Response.CreateTours;
import hcmus.android.lighttour.Response.Register;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CreateToursService {
    @POST("/tour/create")
    @FormUrlEncoded
    Call<CreateTours> sendData(@Field("name") String name,
                               @Field("startDate") long starDate,
                               @Field("endDate") long endDate,
                               @Field("sourceLat") float sourceLat,
                               @Field("sourceLong") float sourceLong,
                               @Field("desLat") float desLat,
                               @Field("desLong") float desLong,
                               @Field("isPrivate") boolean isPrivate,
                               @Field("adults") int adults,
                               @Field("childs") int childs,
                               @Field("minCost") int minCost,
                               @Field("maxCost") int maxCost,
                               @Field("avatar") String avatar);
}
