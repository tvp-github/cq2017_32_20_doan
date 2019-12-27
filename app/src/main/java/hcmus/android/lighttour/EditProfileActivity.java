package hcmus.android.lighttour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import hcmus.android.lighttour.APIService.EditProfileService;
import hcmus.android.lighttour.AppUtils.EditProfileBody;
import hcmus.android.lighttour.Response.EditInfo;
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
    Button btn_save;
    EditProfileService editProfileService;

    public void init(){
        eName = findViewById(R.id.fullName);
        eDob = findViewById(R.id.dob);
        eEmail = findViewById(R.id.email);
        ePhone = findViewById(R.id.phone);
        eAddress = findViewById(R.id.address);
        eMale = findViewById(R.id.isMale);
        eFemale = findViewById(R.id.isFemale);
        btn_save = findViewById(R.id.btnSave);
        editProfileService = ApiUtils.editProfileService();
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //accessToken = sharedPreferences.getString("token", null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        init();

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

        //Set email
        SharedPreferences sharedPreferences_eEmail = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken_eEmail = sharedPreferences_eEmail.getString("token", null);
        Call<UserInfo> call_eMail = ApiUtils.getUser().getinfo(accessToken_eEmail);
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


        //Khi người dùng bấm nút Save
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hide keyboard
                View myView = EditProfileActivity.this.getCurrentFocus();
                if (myView != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                MyApplication myApplication = (MyApplication) getApplication();
                String token = myApplication.getToken();
                String fullName = validate(eName.getText().toString());
                String email = validate(eEmail.getText().toString());
                String phone = validate(ePhone.getText().toString());
                //String address = validate(eAddress.getText().toString());
                String dob = validate(eDob.getText().toString());
                int gender;
                if(eMale.isChecked() || eFemale.isChecked()){
                    if (eMale.isChecked())
                        gender = 1;
                    else
                        gender = 0;
                }
                else{
                    gender = 0;
                    eFemale.setChecked(true);
                }

                editProfileService.sendInfo(token, new EditProfileBody(fullName, email, phone, gender, dob)).enqueue(new Callback<EditInfo>() {
                    @Override
                    public void onResponse(Call<EditInfo> call, Response<EditInfo> response) {
                        if(response.code() == 200){
                            Toast.makeText(EditProfileActivity.this, "Edit successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditProfileActivity.this, SplashActivity.class);
                            startActivity(intent);
                        }
                        if(response.code() == 400) {
                            try {
                                //Log.d("AAA", "onResponse: " + response.errorBody().string());
                                Log.d("Error","onResponse" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<EditInfo> call, Throwable t) {
                        Log.d("AAA", "onFailure: " + call.request().toString());
                        Toast.makeText(EditProfileActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                    }
                });
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

    private String validate(String s){
        if (s.length()>0) return s;
        else return null;
    }



}


