package com.ygaps.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.ygaps.travelapp.APIService.GetInviteService;
import com.ygaps.travelapp.Adapter.InviteAdapter;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.Response.ListTours;
import com.ygaps.travelapp.Response.Tour;
import com.ygaps.travelapp.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {


    ImageButton btnHistory;
    ImageButton btnSetting;
    ImageButton btnExplore;
    ImageButton btnHome;
    LinearLayout lnl_noti;

    ListView listInvite;
    Button btnNext, btnPrev;
    InviteAdapter inviteAdapter;
    List<Tour> inviteTours;
    GetInviteService getInviteService;
    String token;
    int currentPage = 1;
    int ROWPERPAGE = 30;


    public class CustomComparator implements Comparator<Tour> {
        @Override
        public int compare(Tour o1, Tour o2) {
            if (o1.getCreatedOn() < o2 .getCreatedOn())
                return 1;
            if (o1.getCreatedOn() > o2 .getCreatedOn())
                return -1;
            return 0;
        }
    }
    private void getData(){
        btnNext.setEnabled(true);
        btnPrev.setEnabled(true);
        getInviteService.sendData(token, currentPage, ROWPERPAGE).enqueue(new Callback<ListTours>() {
            @Override
            public void onResponse(Call<ListTours> call, Response<ListTours> response) {
                if(response.code() == 200)
                {
                    inviteTours.clear();
                    inviteTours.addAll(response.body().getTours());
                    Collections.sort(inviteTours, new CustomComparator() );
                    inviteAdapter.notifyDataSetChanged();
                    listInvite.smoothScrollToPosition(0);
                    int total = response.body().getTotal();
                    int totalPage = (total / ROWPERPAGE) + (total%ROWPERPAGE == 0 ? 0 : 1);
                    if(currentPage == totalPage) btnNext.setEnabled(false);
                    if(currentPage == 1) btnPrev.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<ListTours> call, Throwable t) {
                Toast.makeText(NotificationActivity.this, "Unalble to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void init(){
        inviteTours = new ArrayList<>();
        inviteAdapter = new InviteAdapter(this, R.layout.notification_item,inviteTours);
        listInvite = findViewById(R.id.listInvite);
        listInvite.setAdapter(inviteAdapter);
        btnNext = findViewById(R.id.btnNextPage_listInvite);
        btnPrev = findViewById(R.id.btnPrevPage_listInvite);
        getInviteService = ApiUtils.getGetInviteService();
        MyApplication myApplication = (MyApplication) getApplication();
        token = myApplication.getToken();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_list);

        //Gọi khởi tạo toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_notification);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Notification");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        getData();
        btnExplore =findViewById(R.id.btnExplore);
        btnHome = findViewById(R.id.btnHome);
        btnSetting = findViewById(R.id.btnSettings);
        btnHistory = findViewById(R.id.btnHistory);
        lnl_noti = findViewById(R.id.lnl_noti);
        lnl_noti.setBackgroundResource(R.drawable.bg_review);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        //Chuyển màn hình sang explore điểm dừng
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });
        //Chuyển màn hình sang explore điểm dừng
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationActivity.this, ListTourActivity.class);
                startActivity(intent);
            }
        });

        // Chuyển màn hình sang history
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(NotificationActivity.this, HistoryActivity.class));
            }

        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage++;
                getData();
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage--;
                getData();
            }
        });
    }
}
