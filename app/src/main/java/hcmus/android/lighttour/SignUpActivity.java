package hcmus.android.lighttour;

import androidx.appcompat.app.AppCompatActivity;
import hcmus.android.lighttour.APIService.RegisterService;
import hcmus.android.lighttour.R;
import hcmus.android.lighttour.Response.Register;
import hcmus.android.lighttour.Response.RegisterError;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {
    RegisterService registerService;
    EditText edtFullname;
    EditText edtEmail;
    EditText edtPhone;
    EditText edtAdress;
    EditText edtBirthday;
    EditText edtPassword;
    EditText edtGender;
    Button btnRegister;
    public void init(){
        edtFullname = findViewById(R.id.edtsignup_fullname);
        edtEmail = findViewById(R.id.edtsignup_email);
        edtPhone = findViewById(R.id.edtsignup_phonenumber);
        edtAdress = findViewById(R.id.edtsignup_address);
        edtBirthday = findViewById(R.id.edtsignup_dob);
        edtPassword = findViewById(R.id.edtsignup_password);
        edtGender = findViewById(R.id.edtsignup_gender);
        btnRegister = findViewById(R.id.btnregister);
        registerService = ApiUtils.getRegisterAPIService();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        init();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hide keyboard
                View myView = SignUpActivity.this.getCurrentFocus();
                if (myView != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                DisableInputField();

                String password = edtPassword.getText().toString();
                String fullName = edtFullname.getText().toString();
                String email = edtEmail.getText().toString();
                String phone = edtPhone.getText().toString();
                String address = edtAdress.getText().toString();
                String dob = edtBirthday.getText().toString();
                int gender = Integer.parseInt(String.valueOf(edtGender.getText().toString().equals("Male")));
                sendRegister(password,fullName,email,phone,address,dob,gender);
            }
        });
    }

    private void DisableInputField() {
        edtFullname.setEnabled(false);
        edtEmail.setEnabled(false);
        edtPhone.setEnabled(false);
        edtAdress.setEnabled(false);
        edtBirthday.setEnabled(false);
        edtPassword.setEnabled(false);
        edtGender.setEnabled(false);
    }
    private void EnableInputField() {
        edtFullname.setEnabled(true);
        edtEmail.setEnabled(true);
        edtPhone.setEnabled(true);
        edtAdress.setEnabled(true);
        edtBirthday.setEnabled(true);
        edtPassword.setEnabled(true);
        edtGender.setEnabled(true);
    }
    public void sendRegister(String password, String fullName, String email, String phone, String address, String dob, int gender) {
        registerService.sendData(password,fullName,email,phone,address,dob,gender).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if(response.code()==200) {
                    Toast.makeText(SignUpActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                if(response.code()==400) {
                    //Internal server Error -> Server error on getting tour list
                    String msg = null;
                    JSONObject message = null;
                    try {
                        message = new JSONObject(response.errorBody().string());
                        JSONArray errorArray = new JSONArray(message.getJSONArray("message"));
                        for (int i = 0 ; i < errorArray.length() ; i++){
                            msg = msg + errorArray.getJSONObject(i).getString("msg") + "\n";
                        }
                        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EnableInputField();
                }
                if(response.code()==504){
                    String msg = null;
                    try {
                        msg = new JSONObject(response.errorBody().string()).getString("message");
                        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EnableInputField();
                }

            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Log.e("AAA", "Unable to submit post to API.");
            }
        });
    }

}
