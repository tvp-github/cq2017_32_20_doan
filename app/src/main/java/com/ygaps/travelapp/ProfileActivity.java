package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.Response.UserInfo;
import com.ygaps.travelapp.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView name;
    TextView gender;
    TextView email;
    TextView phone;
    TextView address;
    TextView birthday;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        //Khởi tạo toolbar
        Toolbar toolbar =findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Chuyển sang màn hình edit profile
        edit = findViewById(R.id.btn_edit_inPro);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }

        });


        //Set name
        SharedPreferences sharedPreferences_name = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_name = sharedPreferences_name.getString("token", null);
        Call<UserInfo> call_name = ApiUtils.getUser().getinfo(accessToken_name);
        name = findViewById(R.id.namePro);
        call_name.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                name.setText(response.body().getFullName());
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });


        //Set gender
        SharedPreferences sharedPreferences_gender = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_gender = sharedPreferences_gender.getString("token", null);
        Call<UserInfo> call_gender = ApiUtils.getUser().getinfo(accessToken_gender);
        gender = findViewById(R.id.genderPro);
        call_gender.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if(response.body().getGender() != null){
                    if (response.body().getGender() == 0){
                        gender.setText("Female");
                    }
                    else{
                        gender.setText("Male");
                    }
                }
                else
                    gender.setText("Empty");
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

        //Set email
        SharedPreferences sharedPreferences_email = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_email = sharedPreferences_email.getString("token", null);
        Call<UserInfo> call_email = ApiUtils.getUser().getinfo(accessToken_email);
        email = findViewById(R.id.emailPro);
        call_email.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                email.setText(response.body().getEmail());
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

        //Set phone
        SharedPreferences sharedPreferences_phone = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_phone = sharedPreferences_phone.getString("token", null);
        Call<UserInfo> call_phone = ApiUtils.getUser().getinfo(accessToken_phone);
        phone = findViewById(R.id.phonePro);
        call_phone.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                phone.setText(response.body().getPhone());
                }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

        //Set address
        SharedPreferences sharedPreferences_address = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_address = sharedPreferences_address.getString("token", null);
        Call<UserInfo> call_address = ApiUtils.getUser().getinfo(accessToken_address);
        address = findViewById(R.id.addressPro);
        call_address.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                Object addr = response.body().getAddress();
                if(addr == null){
                    address.setText("Empty");
                }
                else {
                    address.setText(String.valueOf(addr));
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

        //Set birthday
        SharedPreferences sharedPreferences_birthday = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_birthday = sharedPreferences_birthday.getString("token", null);
        Call<UserInfo> call_birthday = ApiUtils.getUser().getinfo(accessToken_birthday);
        birthday = findViewById(R.id.dobPro);
        call_birthday.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                Object dob = response.body().getDob();
                if(dob == null){
                    birthday.setText("Empty");
                }
                else {
                    birthday.setText(firstTen(String.valueOf(dob)));
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

    }
    //Hàm lấy 10 ký tự đầu tiên
    public String firstTen(String str) {

        if(str.length() == 10){
            return str;
        }
        else{
            return str.substring(0,10);
        }
    }
}


