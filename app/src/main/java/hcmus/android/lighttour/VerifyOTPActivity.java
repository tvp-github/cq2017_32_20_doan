package hcmus.android.lighttour;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import hcmus.android.lighttour.APIService.SendRequestOTPService;
import hcmus.android.lighttour.APIService.SendVerifyOTPService;
import hcmus.android.lighttour.Response.Message;
import hcmus.android.lighttour.Response.RequestOTP;
import hcmus.android.lighttour.Response.Tour;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOTPActivity extends AppCompatActivity {

    RequestOTP requestOTP;
    EditText edtOTP;
    EditText edtNewPass;
    EditText edtNewPassAgain;
    Button btnSubmit;
    int userId;
    String verifyCode;
    String newpass;
    String newpassagain;
    SendVerifyOTPService sendVerifyOTPService;
    //enable backpress
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_otp);
        init();
        //Khởi tạo toolbar cho activity
        Toolbar toolbar =findViewById(R.id.toolbar_verify_otp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Verify OTP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent=getIntent();
        requestOTP= (RequestOTP) intent.getSerializableExtra("requestOTP");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerifyOTPService= ApiUtils.getSendVerifyOTPService();
                userId = requestOTP.getUserId();
                verifyCode =validate(edtOTP.getText().toString());
                newpass =validate(edtNewPass.getText().toString());
                newpassagain =validate(edtNewPassAgain.getText().toString());
                Log.d("AAA", "pass: " + newpass);
                Log.d("AAA", "passagain: " + newpassagain);
                Log.d("AAA", "otp: " + verifyCode);
                Log.d("AAA", "userId: " + userId);
                if (newpass.equals(newpassagain)){
                    sendVerifyOTPService.sendData(userId,newpass,verifyCode).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Log.d("AAA", "onResponse: " + response.code());
                            if (response.code()==200){
                                Toast.makeText(VerifyOTPActivity.this, "Change password succesfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(VerifyOTPActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {

                        }
                    });
                }
                else{
                    Toast.makeText(VerifyOTPActivity.this, " Password does not match", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void init() {
        edtNewPass = findViewById(R.id.edtNewPass);
        edtNewPassAgain = findViewById(R.id.edtNewPassAgain);
        edtOTP = findViewById(R.id.edtOtp);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    private String validate(String s){
        if (s.length()>0) return s;
        else return null;
    }

}
