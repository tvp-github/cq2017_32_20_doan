package hcmus.android.lighttour;

import androidx.appcompat.app.AppCompatActivity;
import hcmus.android.lighttour.APIService.RegisterService;
import hcmus.android.lighttour.Response.Register;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
                String password = validate(edtPassword.getText().toString());
                String fullName = validate(edtFullname.getText().toString());
                String email = validate(edtEmail.getText().toString());
                String phone = validate(edtPhone.getText().toString());
                String address = validate(edtAdress.getText().toString());
                String dob = validate(edtBirthday.getText().toString());

                int gender;
                String strGender;
                strGender = edtGender.getText().toString();
                if(strGender.equals("Male"))
                    gender = 1;
                else gender = 0;

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
                    try {
                        String msg = "";
                        JSONObject data = new JSONObject(response.errorBody().string());
                        JSONArray errorArray = data.getJSONArray("message");
                        for (int i = 0 ; i < errorArray.length() ; i++){
                            if(i==0)
                                msg = errorArray.getJSONObject(i).getString("msg");
                            else
                                msg = msg + "\n"+ errorArray.getJSONObject(i).getString("msg");
                        }
                        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    EnableInputField();
                }
                if(response.code()==503){
                    Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    String msg = null;
                    try {
                        msg = new JSONObject(response.errorBody().string()).getString("message");
                        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    EnableInputField();
                }

            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Unable to connect to server", Toast.LENGTH_SHORT).show();
                EnableInputField();
            }
        });
    }
    private String validate(String s){
        if (s.length()>0) return s;
        else return null;
    }
}
