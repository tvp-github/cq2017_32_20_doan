package com.ygaps.travelapp.Retrofit;

import com.ygaps.travelapp.APIService.AddStopPointsService;
import com.ygaps.travelapp.APIService.CreateToursService;
import com.ygaps.travelapp.APIService.EditProfileService;
import com.ygaps.travelapp.APIService.GetCoordinatesOfMembersService;
import com.ygaps.travelapp.APIService.GetInforUser;
import com.ygaps.travelapp.APIService.GetInviteService;
import com.ygaps.travelapp.APIService.GetListTextMessageService;
import com.ygaps.travelapp.APIService.GetNotificationOnRoadService;
import com.ygaps.travelapp.APIService.GetPointStarsService;
import com.ygaps.travelapp.APIService.GetReviewPointStarsService;
import com.ygaps.travelapp.APIService.GetSearchStopPointService;
import com.ygaps.travelapp.APIService.GetStopPointFeedbackService;
import com.ygaps.travelapp.APIService.GetStopPointService;
import com.ygaps.travelapp.APIService.GetTourInfoService;
import com.ygaps.travelapp.APIService.HistoryTourService;
import com.ygaps.travelapp.APIService.GetTourReviewService;
import com.ygaps.travelapp.APIService.InviteService;
import com.ygaps.travelapp.APIService.ListToursService;
import com.ygaps.travelapp.APIService.LoginGGService;
import com.ygaps.travelapp.APIService.LoginService;
import com.ygaps.travelapp.APIService.PutToken;
import com.ygaps.travelapp.APIService.RegisterService;
import com.ygaps.travelapp.APIService.ResponseInviteService;
import com.ygaps.travelapp.APIService.SearchUserService;
import com.ygaps.travelapp.APIService.SendFeedbackService;
import com.ygaps.travelapp.APIService.SendReportFeedbackService;
import com.ygaps.travelapp.APIService.SendReportReviewService;
import com.ygaps.travelapp.APIService.SendRequestOTPService;
import com.ygaps.travelapp.APIService.SendReviewService;
import com.ygaps.travelapp.APIService.SendSpeedService;
import com.ygaps.travelapp.APIService.SendTextNotificationService;
import com.ygaps.travelapp.APIService.UpdatePassService;
import com.ygaps.travelapp.APIService.UpdateTourService;
import com.ygaps.travelapp.APIService.SendVerifyOTPService;

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
    public static GetTourInfoService getGetTourInfoService(){
        return RetrofitClient.getClient(BASE_URL).create(GetTourInfoService.class);
    }

    public static UpdateTourService getUpdateTourService() {
        return RetrofitClient.getClient(BASE_URL).create(UpdateTourService.class);
    }
    public static SendRequestOTPService getSendRequestOTPService() {
        return RetrofitClient.getClient(BASE_URL).create(SendRequestOTPService.class);
    }

    public static SendVerifyOTPService getSendVerifyOTPService() {
        return RetrofitClient.getClient(BASE_URL).create(SendVerifyOTPService.class);
    }

    public static GetSearchStopPointService getGetSearchStopPointService() {
        return RetrofitClient.getClient(BASE_URL).create(GetSearchStopPointService.class);
    }

    public static GetInforUser getUser() {
        return RetrofitClient.getClient(BASE_URL).create(GetInforUser.class);
    }

    public static SearchUserService getSearchUserService(){
        return RetrofitClient.getClient(BASE_URL).create(SearchUserService.class);
    }

    public static InviteService getInviteUserSevice(){
        return RetrofitClient.getClient(BASE_URL).create(InviteService.class);
    }

    public static ResponseInviteService getResponseInviteSevice(){
        return RetrofitClient.getClient(BASE_URL).create(ResponseInviteService.class);
    }

    public static GetInviteService getGetInviteService(){
        return RetrofitClient.getClient(BASE_URL).create(GetInviteService.class);
    }

    public static EditProfileService editProfileService(){
        return RetrofitClient.getClient(BASE_URL).create(EditProfileService.class);
    }

    public static SendTextNotificationService getSendTextNotificationService() {
        return RetrofitClient.getClient(BASE_URL).create(SendTextNotificationService.class);
    }

    public static GetListTextMessageService getGetListTextMessageService() {
        return RetrofitClient.getClient(BASE_URL).create(GetListTextMessageService.class);
    }

    public static PutToken getPutTokenService() {

        return RetrofitClient.getClient(BASE_URL).create(PutToken.class);
    }

    public static GetCoordinatesOfMembersService getGetCoordinatesOfMembersService() {

        return RetrofitClient.getClient(BASE_URL).create(GetCoordinatesOfMembersService.class);
    }

    public static SendSpeedService getSendSpeedService() {
        return RetrofitClient.getClient(BASE_URL).create(SendSpeedService.class);
    }

    public static GetNotificationOnRoadService getNotificationOnRoadService() {
        return RetrofitClient.getClient(BASE_URL).create(GetNotificationOnRoadService.class);
	}

    public static UpdatePassService getUpdatePassService() {
        return RetrofitClient.getClient(BASE_URL).create(UpdatePassService.class);
    }
}