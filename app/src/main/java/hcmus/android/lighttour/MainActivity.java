package hcmus.android.lighttour;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import hcmus.android.lighttour.APIService.LoginGGService;
import hcmus.android.lighttour.APIService.LoginService;
import hcmus.android.lighttour.Response.Login;
import hcmus.android.lighttour.Response.LoginGG;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    static String ClientID = "101991060940-0bt756s94oj8nq63i8nba03mavcd1cj6.apps.googleusercontent.com";
    static String ClientSecret = "zg4LQ_wajUZN7oni20dOJADC";
    private static final int RC_SIGN_IN = 001;
    Button btnLogin;
    EditText edtEmailPhone;
    EditText edtPassword;
    String token;
    Integer userId;
    TextView txtSignup;
    ImageButton imgbtnGGLogin;
    private LoginService loginService;
    private LoginGGService loginGGService;
    GoogleSignInClient mGoogleSignInClient;

    public void init(){
        btnLogin = findViewById(R.id.btnlogin);
        edtEmailPhone = findViewById(R.id.edtlogin_user);
        edtPassword = findViewById(R.id.edtlogin_password);
        txtSignup =findViewById(R.id.txtSignup);
        loginService = ApiUtils.getLoginAPIService();
        loginGGService = ApiUtils.getLoginGGService();
        imgbtnGGLogin = findViewById(R.id.btnsignin_gg);
        //GG login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestServerAuthCode(ClientID)
                .requestIdToken(ClientID)
                .requestScopes(new Scope(Scopes.PROFILE))
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account); Update UI if account not null
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Login with gg success", Toast.LENGTH_SHORT).show();
            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("grant_type", "authorization_code")
                    .add("client_id", ClientID)
                    .add("client_secret", ClientSecret)
                    .add("redirect_uri","")
                    .add("code", account.getServerAuthCode())
                    .build();
            final Request request = new Request.Builder()
                    .url("https://www.googleapis.com/oauth2/v4/token")
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new com.squareup.okhttp.Callback(){

                @Override
                public void onFailure(final Request request, final IOException e) {
                    Log.e("EEE", e.toString());
                }

                @Override
                public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                    try {
                        JSONObject data = new JSONObject(response.body().string());
                        //Save token
                        String accessToken = data.getString("access_token");
                        sendLoginGG(accessToken);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("AAA", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    private void sendLoginGG(String accessToken) {
        loginGGService.sendData(accessToken,null).enqueue(new Callback<LoginGG>() {
            @Override
            public void onResponse(Call<LoginGG> call, Response<LoginGG> response) {
                if(response.code()==200){
                    token = response.body().getToken();
                    //Save token and userId
                    //Go to Home View
                }
            }

            @Override
            public void onFailure(Call<LoginGG> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Cant login with google Account!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        imgbtnGGLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Google Login", Toast.LENGTH_SHORT).show();
                signIn();
            }
        });
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
                    Intent intent = new Intent(MainActivity.this, ListTourActivity.class);
                    startActivity(intent);
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
