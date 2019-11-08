package hcmus.android.lighttour;

import androidx.appcompat.app.AppCompatActivity;
import hcmus.android.lighttour.APIService.LoginService;
import hcmus.android.lighttour.Response.Login;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtEmailPhone;
    EditText edtPassword;
    String token;
    Integer userId;
    TextView txtSignup;
    private LoginService loginService;
    public void init(){
        btnLogin = findViewById(R.id.btnlogin);
        edtEmailPhone = findViewById(R.id.edtlogin_user);
        edtPassword = findViewById(R.id.edtlogin_password);
        txtSignup =findViewById(R.id.txtSignup);
        loginService = ApiUtils.getLoginAPIService();
    }
    public void DisableInputField(){
        edtEmailPhone.setEnabled(false);
        edtPassword.setEnabled(false);
    }
    public void EnableInputField(){
        edtEmailPhone.setEnabled(true);
        edtPassword.setEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hide keyboard
                View myView = MainActivity.this.getCurrentFocus();
                if (myView != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                DisableInputField();
                String emailPhone = edtEmailPhone.getText().toString();
                String password = edtPassword.getText().toString();
                sendLogin(emailPhone,password);
            }
        });
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to sign up view
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    public void sendLogin(String emailPhone, String password) {
        loginService.sendData(emailPhone,password).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                //Login success
                if(response.code()==200) {
                    token = response.body().getToken();
                    userId = response.body().getUserId();
                    //Save token and userId
                    //Go to Home View
                }
                if(response.code()==400){
                    //Toast error
                    String msg = null;
                    try {
                        msg = new JSONObject(response.errorBody().string()).getString("message");
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EnableInputField();
                }

                if(response.code()==404){
                    //Toast Error
                    String msg = null;
                    try {
                        msg = new JSONObject(response.errorBody().string()).getString("message");
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EnableInputField();
                }

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Unable to connect to server", Toast.LENGTH_SHORT).show();
                EnableInputField();
            }
        });
    }
}
