package hcmus.android.lighttour;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import hcmus.android.lighttour.APIService.SendFeedbackService;
import hcmus.android.lighttour.AppUtils.SendFeedbackBody;
import hcmus.android.lighttour.Response.Feedback;
import hcmus.android.lighttour.Response.Message;
import hcmus.android.lighttour.Response.StopPoint;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteReviewActivity extends AppCompatActivity {

    RatingBar mRatingBar;
    TextView mRatingScale;
    EditText mFeedback;
    Button mSendFeedback;;
    StopPoint stopPoint;
    String token;
    SendFeedbackService sendFeedbackService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_review);

        //Khởi tạo toolbar cho activity
        Toolbar toolbar =findViewById(R.id.toolbar_write_review);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Write review");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mRatingBar = (RatingBar) findViewById(R.id.rating_bar);
        mRatingScale = (TextView) findViewById(R.id.tvRatingScale);
        mFeedback = (EditText) findViewById(R.id.edtFeedback);
        mSendFeedback = (Button) findViewById(R.id.btnSubmit);

        Intent intent=getIntent();
        stopPoint= (StopPoint) intent.getSerializableExtra("stopPoint");
        MyApplication myApplication = (MyApplication) getApplication();
        token = myApplication.getToken();
        sendFeedbackService= ApiUtils.getSendFeedbackService();
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mRatingScale.setText("Very bad");
                        break;
                    case 2:
                        mRatingScale.setText("Need some improvement");
                        break;
                    case 3:
                        mRatingScale.setText("Good");
                        break;
                    case 4:
                        mRatingScale.setText("Great");
                        break;
                    case 5:
                        mRatingScale.setText("Awesome. I love it");
                        break;
                    default:
                        mRatingScale.setText("");
                }
            }
        });

        mSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFeedback.getText().toString().isEmpty()) {
                    Toast.makeText(WriteReviewActivity.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
                } else {
                    DisableInputField();
                    int serviceID=stopPoint.getId();
                    String feedback=validate(mFeedback.getText().toString());
                    int point= (int) mRatingBar.getRating();
                    sendFeedback(token,serviceID,feedback,point);
//                    mFeedback.setText("");
//                    mRatingBar.setRating(0);
                    Toast.makeText(WriteReviewActivity.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }

            private void sendFeedback(String token, int serviceID, String feedback, final int point) {
                sendFeedbackService.sendData(token, new SendFeedbackBody(serviceID,feedback,point)).enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        Log.d("AAA", "onResponse: "+response.code());
                        if (response.code()==200){
                            Log.d("AAA", "total: "+point);
                            Toast.makeText(WriteReviewActivity.this,"Send Feedback successfully", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(WriteReviewActivity.this, PointInformationActivity.class);
//                            startActivity(intent);
                            finish();
                        }
                        if(response.code()==500) {
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
                                Toast.makeText(WriteReviewActivity.this, msg, Toast.LENGTH_SHORT).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                            EnableInputField();
                        }
//                        if(response.code()==500){
//                            Toast.makeText(WriteReviewActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                            String msg = null;
//                            try {
//                                msg = new JSONObject(response.errorBody().string()).getString("message");
//                                Toast.makeText(WriteReviewActivity.this, msg, Toast.LENGTH_SHORT).show();
//                            } catch (JSONException | IOException e) {
//                                e.printStackTrace();
//                            }
//                            EnableInputField();
//                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(WriteReviewActivity.this, "Unable to connect to server", Toast.LENGTH_SHORT).show();
                        EnableInputField();
                    }
                });
            }

            private void DisableInputField() {
                mFeedback.setEnabled(false);
                mRatingBar.setEnabled(false);
                mSendFeedback.setEnabled(false);
            }

            private void EnableInputField() {
                mFeedback.setEnabled(true);
                mRatingBar.setEnabled(true);
                mSendFeedback.setEnabled(true);
            }

            private String validate(String s){
                if (s.length()>0) return s;
                else return null;
            }
        });

    }
}
