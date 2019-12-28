package com.ygaps.travelapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication {
    private String token;
    private int idUser;
    private String fcmToken;
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
        fcmToken = sharedPref.getString("fcmToken",null);
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
    public void setFcmToken(String fcmToken){
        //Gán token cho Application
        this.fcmToken = fcmToken;
        //Lưu token xuống bộ nhớ
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("fcmToken",fcmToken);
        editor.commit();
    }
    public String getFcmToken() {

        return fcmToken;
    }
}
