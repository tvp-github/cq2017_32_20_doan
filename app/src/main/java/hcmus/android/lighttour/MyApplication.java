package hcmus.android.lighttour;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyApplication extends Application {
    private String token;

    @Override
    public void onCreate() {
        super.onCreate();
        loadToken();
    }

    public String getToken() {

        return token;
    }
    //Đọc token từ bộ nhớ
    public void loadToken(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPref.getString("token",null);
    }
    public void setToken(String token) {
    //Gán token cho Application
        this.token = token;
        //Lưu token xuống bộ nhớ
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token",token);
        editor.commit();
    }

}
