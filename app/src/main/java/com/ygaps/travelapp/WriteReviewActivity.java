package com.ygaps.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ygaps.travelapp.R;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import com.ygaps.travelapp.APIService.SendReviewService;
import com.ygaps.travelapp.AppUtils.SendReviewBody;
import com.ygaps.travelapp.Response.Message;
import com.ygaps.travelapp.Response.Tour;
import com.ygaps.travelapp.Retrofit.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteReviewActivity extends AppCompatActivity {

    RatingBar mRatingBar;
    TextView mRatingScale;
    EditText mReview;
    Button mSendReview;;
    Tour tour;
    String token;
    SendReviewService sendReviewService;

    //enable backpress
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_review);

        //Khởi tạo toolbar cho activity
        Toolbar toolbar =findViewById(R.id.toolbar_write_review);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Write review");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        mRatingBar = (RatingBar) findViewById(R.id.rating_bar);
        mRatingScale = (TextView) findViewById(R.id.tvRatingScale);
        mReview = (EditText) findViewById(R.id.edtFeedback);
        mSendReview = (Button) findViewById(R.id.btnSubmit);

        Log.d("111", "tour info " );
        Intent intent=getIntent();
        tour= new Gson().fromJson(intent.getStringExtra("tour"),Tour.class);
        Log.d("111", "tour info " + tour.toString());
        MyApplication myApplication = (MyApplication) getApplication();
        token = myApplication.getToken();
        sendReviewService= ApiUtils.getSendReviewService();
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

        mSendReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mReview.getText().toString().isEmpty()) {
                    Toast.makeText(WriteReviewActivity.this, "Please fill in review text box", Toast.LENGTH_LONG).show();
                } else {
                    DisableInputField();
                    int tourId=tour.getId();
                    String review=validate(mReview.getText().toString());
                    int point= (int) mRatingBar.getRating();
                    Log.d("AAA", "onResponse: "+tourId +" " + point + review );
                    sendReview(token,tourId,point,review);
//                    mFeedback.setText("");
//                    mRatingBar.setRating(0);

                    Toast.makeText(WriteReviewActivity.this, "Thank you for sharing your review", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }

            private void sendReview(String token, int tourId, final int point, String review) {
                sendReviewService.sendData(token, new SendReviewBody(tourId,point,review)).enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        Log.d("AAA", "onResponse: "+response.code());
                        if (response.code()==200){
                            Log.d("AAA", "total: "+point);
                            Toast.makeText(WriteReviewActivity.this,"Send Review successfully", Toast.LENGTH_SHORT).show();
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
                mReview.setEnabled(false);
                mRatingBar.setEnabled(false);
                mSendReview.setEnabled(false);
            }

            private void EnableInputField() {
                mReview.setEnabled(true);
                mRatingBar.setEnabled(true);
                mSendReview.setEnabled(true);
            }

            private String validate(String s){
                if (s.length()>0) return s;
                else return null;
            }
        });

    }
}
