package com.ygaps.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ygaps.travelapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import com.ygaps.travelapp.APIService.GetListTextMessageService;
import com.ygaps.travelapp.APIService.SendTextNotificationService;
import com.ygaps.travelapp.Adapter.ListTextMessageAdapter;
import com.ygaps.travelapp.Response.ListNotification;
import com.ygaps.travelapp.Response.Message;
import com.ygaps.travelapp.Response.Notification;
import com.ygaps.travelapp.Response.Tour;
import com.ygaps.travelapp.Retrofit.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TextMessageActivity extends AppCompatActivity {

    List<Notification> listNotiData;
    List<Notification> listData;
    ListView listNoti;
    ListTextMessageAdapter listNotiAdapter;
    GetListTextMessageService getListTextMessageService;
    Tour tour;
    EditText edtMessage;
    Button btnSend;
    String token;
    String noti ;
    String tourId ;
    String userId ;
    int tourid;
    SendTextNotificationService sendTextNotificationService;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void init() {
        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);
        listNoti = findViewById(R.id.listMessage);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_message);
        init();

        //tool bar
        Toolbar toolbar = findViewById(R.id.toolbar_text_message);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Text Message");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent=getIntent();
        tour= new Gson().fromJson(intent.getStringExtra("tour"), Tour.class);
        sendTextNotificationService = ApiUtils.getSendTextNotificationService();
        getListTextMessageService = ApiUtils.getGetListTextMessageService();
        MyApplication myApplication = (MyApplication) getApplication();
        token = myApplication.getToken();

        Integer userID = myApplication.getIdUser();


        tourId = tour.getId().toString();
        userId = userID.toString();
        tourid=tour.getId();

        Log.d("AAA", "noti: " + noti);
        Log.d("AAA", "tourId: " + tourid);
        Log.d("AAA", "userId: " + userId);
        getData();
        //Init Adapter, set Adapter to listTour
        listNotiData = new ArrayList<Notification>();
        listNotiAdapter = new ListTextMessageAdapter(TextMessageActivity.this, R.layout.textmessage_item,listNotiData);
        listNoti.setAdapter(listNotiAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noti = validate(edtMessage.getText().toString());
                sendTextNotificationService.sendData(token,tourId,userId,noti).enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        Log.d("AAA","onResponse send: " + response.code());
                        if (response.code()==200)
                            edtMessage.setText("");
                            getData();
                            Toast.makeText(TextMessageActivity.this, "Send Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(TextMessageActivity.this, "Send Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private String validate(String s){
        if (s.length()>0) return s;
        else return null;
    }

    private void getData() {
        getListTextMessageService.sendData(token,tourid,1,"20").enqueue(new Callback<ListNotification>() {
            @Override
            public void onResponse(Call<ListNotification> call, Response<ListNotification> response) {
                Log.d("111","onResponse get list: " + response.code());
                if (response.code()==200){
                    if (response.body().getNotis()!=null){
                        listNotiData.clear();
                        listNotiData.addAll(response.body().getNotis());
                        listNotiAdapter.notifyDataSetChanged();
                        listNotiAdapter = new ListTextMessageAdapter(TextMessageActivity.this, R.layout.textmessage_item,listNotiData);
                        listNoti.setAdapter(listNotiAdapter);
                    }

                }

            }

            @Override
            public void onFailure(Call<ListNotification> call, Throwable t) {

            }
        });
    }
}


