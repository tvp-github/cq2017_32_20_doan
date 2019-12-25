package hcmus.android.lighttour;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import hcmus.android.lighttour.Response.RequestOTP;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button btnViaSms;
    Button btnViaEmail;
    EditText edtEmail;
    Button btnSubmit;
    String type;
    String email;
    SendRequestOTPService sendRequestOTPService;

    private void init() {
        btnViaSms = findViewById(R.id.btnViaSms);
        btnViaEmail = findViewById(R.id.btnViaEmail);
        edtEmail = findViewById(R.id.edtEmail);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    //enable backpress
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pasword);
        init();

        //Khởi tạo toolbar cho activity
        Toolbar toolbar =findViewById(R.id.toolbar_forgot_password);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Forgot password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        btnViaEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnViaSms.setBackgroundResource(R.drawable.bg_review);
                btnViaEmail.setBackgroundResource(R.drawable.style_border);
                edtEmail.setHint("Please enter your email");
                type="email";
            }
        });
        btnViaSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnViaEmail.setBackgroundResource(R.drawable.bg_review);
                btnViaSms.setBackgroundResource(R.drawable.style_border);
                edtEmail.setHint("Please enter your phone number");
                type="phone";
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestOTPService= ApiUtils.getSendRequestOTPService();
                email =validate(edtEmail.getText().toString());
                Log.d("AAA", "type: " + type);
                Log.d("AAA", "email: " + email);
                sendRequestOTPService.sendData(type,email).enqueue(new Callback<RequestOTP>() {
                    @Override
                    public void onResponse(Call<RequestOTP> call, Response<RequestOTP> response) {
                        Log.d("AAA", "onResponse: " + response.code() + response.body().toString());
                        if (response.code()==200) {
                            Toast.makeText(ForgotPasswordActivity.this, "OTP are sended to your email successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgotPasswordActivity.this, VerifyOTPActivity.class);
                            RequestOTP requestOTP=response.body();
                            intent.putExtra("requestOTP",requestOTP);
                            startActivity(intent);
                        }
                        if (response.code()==404){
                            Toast.makeText(ForgotPasswordActivity.this, "EMAIL/PHONE doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestOTP> call, Throwable t) {

                    }
                });
            }
        });


    }

    private String validate(String s){
        if (s.length()>0) return s;
        else return null;
    }



}
