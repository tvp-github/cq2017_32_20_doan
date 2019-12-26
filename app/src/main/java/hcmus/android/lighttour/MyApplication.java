package hcmus.android.lighttour;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyApplication extends Application {
    private String token;
    private int idUser;
    @Override
    public void onCreate() {
        super.onCreate();
        loadData();
    }

    public String getToken() {

        return token;
    }
    public int getIdUser() {

        return idUser;
    }
    //Đọc token từ bộ nhớ
    public void loadData(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.token = sharedPref.getString("token",null);
        this.idUser = sharedPref.getInt("id-user",0);
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
    public void setIdUser(int userId) {
    //Gán token cho Application
        this.idUser = userId;
        //Lưu token xuống bộ nhớ
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id-user",userId);
        editor.commit();
    }


}
