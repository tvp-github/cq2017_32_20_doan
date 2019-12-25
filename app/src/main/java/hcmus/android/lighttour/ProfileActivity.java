package hcmus.android.lighttour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import hcmus.android.lighttour.Response.UserInfo;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView userName;
    TextView name;

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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken = sharedPreferences.getString("token", null);
        Call<UserInfo> call = ApiUtils.getUser().getinfo(accessToken);
        //Set Username
        userName = findViewById(R.id.userName_pro);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                userName.setText(response.body().getFullName());
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });
/*
        //Set Name
        name = findViewById(R.id.namePro);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                userName.setText(response.body().getFullName());
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });*/
    }
}


