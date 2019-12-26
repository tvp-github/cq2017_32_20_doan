package hcmus.android.lighttour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import hcmus.android.lighttour.Response.UserInfo;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    EditText  eName;
    TextView  eDob;
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

        //Set name
        SharedPreferences sharedPreferences_eName = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_eName = sharedPreferences_eName.getString("token", null);
        Call<UserInfo> call_eName = ApiUtils.getUser().getinfo(accessToken_eName);
        eName = findViewById(R.id.fullName);
        call_eName.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.body().getFullName() != null)
                eName.setText(response.body().getFullName());
                else
                    eName.setText("");
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

        //Set birthay
        SharedPreferences sharedPreferences_eDob = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_eDob = sharedPreferences_eDob.getString("token", null);
        Call<UserInfo> call_eDob = ApiUtils.getUser().getinfo(accessToken_eDob);
        eDob = findViewById(R.id.dob);
        call_eDob.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                //String edob = firstTen(String.valueOf(response.body().getDob()));
                Object edob = response.body().getDob();
                if (edob != null)
                eDob.setText(firstTen(String.valueOf(edob)));
                else
                    eDob.setText("Choose your date of birth");
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

        //Set email
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

        //Set phone number
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

        //Set address
        SharedPreferences sharedPreferences_eAddress = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_eAdress = sharedPreferences_eAddress.getString("token", null);
        Call<UserInfo> call_eAddress = ApiUtils.getUser().getinfo(accessToken_eAdress);
        eAddress = findViewById(R.id.address);
        call_eAddress.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.body().getAddress() != null)
                eAddress.setText(String.valueOf(response.body().getAddress()));
                else
                    eAddress.setText("");
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
                if (response.body().getGender() != null){
                    if (response.body().getGender() == 0) {
                        eFemale.setChecked(true);
                    } else {
                        eMale.setChecked(true);
                    }
            }
                else{
                    eFemale.setChecked(false);
                    eMale.setChecked(false);
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


        eDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo calendar lưu giá trị nhập vào
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //cập nhật calendar
                        calendar.set(Calendar.YEAR,i);
                        calendar.set(Calendar.MONTH,i1);
                        calendar.set(Calendar.DATE,i2);
                        //Xuất ra
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        eDob.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
                datePickerDialog.show();
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


