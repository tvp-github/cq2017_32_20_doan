package com.ygaps.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ygaps.travelapp.APIService.SendVerifyOTPService;
import com.ygaps.travelapp.APIService.UpdatePassService;
import com.ygaps.travelapp.AppUtils.Message;
import com.ygaps.travelapp.Response.RequestOTP;
import com.ygaps.travelapp.Retrofit.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePassActivity extends AppCompatActivity {


    EditText edtCurrentPass;
    EditText edtNewPass;
    EditText edtNewPassAgain;
    Button btnSubmit;
    int userId;
    String currentpass;
    String newpass;
    String newpassagain;
    String token;
    UpdatePassService updatePassService;
    //enable backpress
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_pass);
        init();
        //Khởi tạo toolbar cho activity
        Toolbar toolbar =findViewById(R.id.toolbar_update_pass);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        updatePassService= ApiUtils.getUpdatePassService();
        MyApplication myApplication = (MyApplication) getApplication();
        token = myApplication.getToken();
        userId = myApplication.getIdUser();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentpass =validate(edtCurrentPass.getText().toString());
                newpass =validate(edtNewPass.getText().toString());
                newpassagain =validate(edtNewPassAgain.getText().toString());
                Log.d("AAA", "pass: " + newpass);
                Log.d("AAA", "passagain: " + newpassagain);
                Log.d("AAA", "current: " + currentpass);
                Log.d("AAA", "userId: " + userId);
                if (newpass.equals(newpassagain)){
                    updatePassService.sendData(token,userId,currentpass,newpass).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Log.d("AAA", "onResponse: " + response.code());
                            if (response.code()==200){
                                Toast.makeText(UpdatePassActivity.this, "Change password succesfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UpdatePassActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            if (response.code()==400)
                                Toast.makeText(UpdatePassActivity.this, "Current password is wrong", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {

                        }
                    });
                }
                else{
                    Toast.makeText(UpdatePassActivity.this, " Password does not match", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void init() {
        edtNewPass = findViewById(R.id.edtNewPass);
        edtNewPassAgain = findViewById(R.id.edtNewPassAgain);
        edtCurrentPass = findViewById(R.id.edtCurrentPass);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    private String validate(String s){
        if (s.length()>0) return s;
        else return null;
    }

}
