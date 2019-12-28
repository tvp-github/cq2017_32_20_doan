package hcmus.android.lighttour.APIService;

import hcmus.android.lighttour.Response.ListNotification;
import hcmus.android.lighttour.Response.ListTours;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GetListTextMessageService {
    @GET("/tour/notification-list")
    Call<ListNotification> sendData(@Header("Authorization") String token,
                                    @Query("tourId") int tourId,
                                    @Query("pageIndex") int pageIndex,
                                    @Query("pageSize") String pageSize
    );
}
