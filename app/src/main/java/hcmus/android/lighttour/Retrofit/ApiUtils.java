package hcmus.android.lighttour.Retrofit;

import hcmus.android.lighttour.APIService.AddStopPointsService;
import hcmus.android.lighttour.APIService.CreateToursService;
import hcmus.android.lighttour.APIService.GetPointStarsService;
import hcmus.android.lighttour.APIService.GetReviewPointStarsService;
import hcmus.android.lighttour.APIService.GetStopPointFeedbackService;
import hcmus.android.lighttour.APIService.GetStopPointService;
import hcmus.android.lighttour.APIService.HistoryTourService;
import hcmus.android.lighttour.APIService.GetTourReviewService;
import hcmus.android.lighttour.APIService.ListToursService;
import hcmus.android.lighttour.APIService.LoginGGService;
import hcmus.android.lighttour.APIService.LoginService;
import hcmus.android.lighttour.APIService.RegisterService;
import hcmus.android.lighttour.APIService.SendFeedbackService;
import hcmus.android.lighttour.APIService.SendReportFeedbackService;
import hcmus.android.lighttour.APIService.SendReportReviewService;
import hcmus.android.lighttour.APIService.SendReviewService;

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

    public static AddStopPointsService getAddStopPointsService() {
        return RetrofitClient.getClient(BASE_URL).create(AddStopPointsService.class);
    }

    public static HistoryTourService getHistoryTourService() {
        return RetrofitClient.getClient(BASE_URL).create(HistoryTourService.class);
    }

    public static GetTourReviewService getGetTourReviewService() {
        return RetrofitClient.getClient(BASE_URL).create(GetTourReviewService.class);
    }

    public static SendReviewService getSendReviewService() {
        return RetrofitClient.getClient(BASE_URL).create(SendReviewService.class);
    }

    public static GetReviewPointStarsService getGetReviewPointStarsService() {
        return RetrofitClient.getClient(BASE_URL).create(GetReviewPointStarsService.class);
    }

    public static SendReportReviewService getSendReportReviewService() {
        return RetrofitClient.getClient(BASE_URL).create(SendReportReviewService.class);
    }

    public static SendReportFeedbackService getSendReportFeedbackService() {
        return RetrofitClient.getClient(BASE_URL).create(SendReportFeedbackService.class);
    }
}