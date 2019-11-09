package hcmus.android.lighttour.Retrofit;

import hcmus.android.lighttour.APIService.ListToursService;
import hcmus.android.lighttour.APIService.LoginService;
import hcmus.android.lighttour.APIService.RegisterService;

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


}