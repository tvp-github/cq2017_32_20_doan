package hcmus.android.lighttour.Retrofit;

import hcmus.android.lighttour.APIService.CreateToursService;
import hcmus.android.lighttour.APIService.GetPointStarsService;
import hcmus.android.lighttour.APIService.GetStopPointFeedbackService;
import hcmus.android.lighttour.APIService.GetStopPointService;
import hcmus.android.lighttour.APIService.ListToursService;
import hcmus.android.lighttour.APIService.LoginGGService;
import hcmus.android.lighttour.APIService.LoginService;
import hcmus.android.lighttour.APIService.RegisterService;
import hcmus.android.lighttour.APIService.SendFeedbackService;

//Tạo các Service từ RetrofitClient để lấy dữ liệu thông qua các Service này
public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://35.197.153.192:3000/";

    public static LoginService getLoginAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(LoginService.class);
    }
    public static ListToursService getListToursAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(ListToursService.class);
    }
    public static RegisterService getRegisterAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(RegisterService.class);
    }
    public static LoginGGService getLoginGGService() {

        return RetrofitClient.getClient(BASE_URL).create(LoginGGService.class);
    }
    public static CreateToursService getCreateToursService() {

        return RetrofitClient.getClient(BASE_URL).create(CreateToursService.class);
    }
    public static GetStopPointService getGetStopPointService() {

        return RetrofitClient.getClient(BASE_URL).create(GetStopPointService.class);
    }

    public static GetStopPointFeedbackService getGetStopPointFeedbackService() {
        return RetrofitClient.getClient(BASE_URL).create(GetStopPointFeedbackService.class);
    }

    public static GetPointStarsService getGetPointStarsService() {
        return RetrofitClient.getClient(BASE_URL).create(GetPointStarsService.class);
    }

    public static SendFeedbackService getSendFeedbackService() {
        return RetrofitClient.getClient(BASE_URL).create(SendFeedbackService.class);
    }
}