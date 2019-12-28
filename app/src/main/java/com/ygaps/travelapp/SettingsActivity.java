package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.Response.UserInfo;
import com.ygaps.travelapp.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {
    Spinner spinner;
    Button logout;
    Button editProfile;
    TextView userName;
    ImageButton btnHistory;
    ImageButton btnNoti;
    ImageButton btnExplore;
    ImageButton btnHome;
    LinearLayout lnl_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //Khởi tạo toolbar
        Toolbar toolbar =findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        btnExplore =findViewById(R.id.btnExplore);
        btnHome = findViewById(R.id.btnHome);
        btnNoti = findViewById(R.id.btnNoti);
        btnHistory = findViewById(R.id.btnHistory);
        lnl_setting = findViewById(R.id.lnl_setting);
        lnl_setting.setBackgroundResource(R.drawable.bg_review);

        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        //Chuyển màn hình sang explore điểm dừng
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });
        //Chuyển màn hình sang explore điểm dừng
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, ListTourActivity.class);
                startActivity(intent);
            }
        });

        // Chuyển màn hình sang history
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, HistoryActivity.class));
            }

        });

        //Language

        spinner = findViewById(R.id.switchLang);
        final String[] gen = {"Vietnamese", "English", "Japanese", "Thai"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,gen);
        spinner.setAdapter(arrayAdapter);

        //Log out
        logout = findViewById(R.id.btn_logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("token").apply();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        // Chuyển sang màn hình edit profile
        editProfile = findViewById(R.id.btn_editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, EditProfileActivity.class));
            }

        });

        //Set Username
        userName = findViewById(R.id.userName);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken = sharedPreferences.getString("token", null);
        Call<UserInfo> call = ApiUtils.getUser().getinfo(accessToken);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                userName.setText(response.body().getFullName());
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

    }

}