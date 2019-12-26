package hcmus.android.lighttour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import hcmus.android.lighttour.Response.UserInfo;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    EditText  eName;
    EditText  eDob;
    EditText eEmail;
    EditText  ePhone;
    EditText eAddress;
    CheckBox eMale;
    CheckBox eFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        //Khởi tạo toolbar
        Toolbar toolbar =findViewById(R.id.toolbar_editProfile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profile");
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Set hint name
        SharedPreferences sharedPreferences_eName = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_eName = sharedPreferences_eName.getString("token", null);
        Call<UserInfo> call_eName = ApiUtils.getUser().getinfo(accessToken_eName);
        eName = findViewById(R.id.fullName);
        call_eName.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                eName.setText(response.body().getFullName());
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

        //Set hint birthay
        SharedPreferences sharedPreferences_eDob = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_eDob = sharedPreferences_eDob.getString("token", null);
        Call<UserInfo> call_eDob = ApiUtils.getUser().getinfo(accessToken_eDob);
        eDob = findViewById(R.id.dob);
        call_eDob.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                String edob = firstEleven(String.valueOf(response.body().getDob()));
                eDob.setText(edob);
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

        //Set hint email
        SharedPreferences sharedPreferences_eEmail = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_eEmail = sharedPreferences_eEmail.getString("token", null);
        Call<UserInfo> call_eMail = ApiUtils.getUser().getinfo(accessToken_eEmail);
        eEmail = findViewById(R.id.email);
        call_eMail.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                eEmail.setText(response.body().getEmail());
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

        //Set hint phone number
        SharedPreferences sharedPreferences_ePhone = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_ePhone = sharedPreferences_ePhone.getString("token", null);
        Call<UserInfo> call_ePhone = ApiUtils.getUser().getinfo(accessToken_ePhone);
        ePhone = findViewById(R.id.phone);
        call_ePhone.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                ePhone.setText(response.body().getPhone());
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

        //Set hint address
        SharedPreferences sharedPreferences_eAddress = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_eAdress = sharedPreferences_eAddress.getString("token", null);
        Call<UserInfo> call_eAddress = ApiUtils.getUser().getinfo(accessToken_eAdress);
        eAddress = findViewById(R.id.address);
        call_eAddress.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                eAddress.setText(String.valueOf(response.body().getAddress()));
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

        //Set gender
        SharedPreferences sharedPreferences_eGender = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_eGender = sharedPreferences_eGender.getString("token", null);
        Call<UserInfo> call_eGender = ApiUtils.getUser().getinfo(accessToken_eGender);
        eMale = findViewById(R.id.isMale);
        eFemale = findViewById(R.id.isFemale);
        call_eGender.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.body().getGender() == 0){
                    eFemale.setChecked(true);
                }
                else{
                    eMale.setChecked(true);
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

        // Chỉ chọn 1 trong 2 "Male" hoặc "Female"
        eMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    eFemale.setChecked(false);
                }
            }
        });

        eFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    eMale.setChecked(false);
            }
        });

    }

    //Hàm lấy 10 ký tự đầu tiên
    public String firstEleven(String str) {

        if(str.length() == 10){
            return str;
        }
        else{
            return str.substring(0,10);
        }
    }

}


