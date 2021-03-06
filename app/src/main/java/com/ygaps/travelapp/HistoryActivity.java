package com.ygaps.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ygaps.travelapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.ygaps.travelapp.APIService.HistoryTourService;
import com.ygaps.travelapp.Adapter.ListTourAdapter;
import com.ygaps.travelapp.Response.ListTours;
import com.ygaps.travelapp.Response.Tour;
import com.ygaps.travelapp.Retrofit.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {
    List<Tour> listTourData;
    ListTourAdapter listTourAdapter;
    Button btnNextPage;
    Button btnPrevPage;
    ListView listTour;
    TextView txtTotalTour;
    ImageButton btnSettings;
    ImageButton btnNoti;
    ImageButton btnExplore;
    ImageButton btnHome;
    LinearLayout lnl_history;
    HistoryTourService historyTourService;
    String token;
    int pageSize = 30;
    int currentPage = 1;
    int total;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        init();
        getData();
        //tool bar
        Toolbar toolbar =findViewById(R.id.toolbar_list_tour);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("History");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        lnl_history.setBackgroundResource(R.drawable.bg_review);

        btnPrevPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage--;
                getData();
                listTour.smoothScrollToPosition(0);
            }
        });
        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage++;
                getData();
                listTour.smoothScrollToPosition(0);
            }
        });
        listTour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HistoryActivity.this, TourInformationActivity.class);
                intent.putExtra("tour", new Gson().toJson(listTourData.get(i)));
                intent.putExtra("fromHistory",true);
                startActivity(intent);
            }
        });

        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        //Chuyển màn hình sang explore điểm dừng
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });
        //Chuyển màn hình sang home
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, ListTourActivity.class);
                startActivity(intent);
            }
        });

        // Chuyển màn hình sang settings
        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(HistoryActivity.this, SettingsActivity.class));
            }

        });
    }

    private void init() {
        listTourData = new ArrayList<Tour>();
        listTour = findViewById(R.id.listTour);
        listTourAdapter = new ListTourAdapter(HistoryActivity.this,R.layout.list_item,listTourData);
        listTour.setAdapter(listTourAdapter);
        btnNextPage = findViewById(R.id.btnNextPage_listtour);
        btnPrevPage = findViewById(R.id.btnPrevPage_listtour);
        historyTourService = ApiUtils.getHistoryTourService();
        btnExplore =findViewById(R.id.btnExplore);
        btnHome = findViewById(R.id.btnHome);
        btnNoti = findViewById(R.id.btnNoti);
        lnl_history = findViewById(R.id.lnl_history);
        btnSettings = findViewById(R.id.btnSettings);
        txtTotalTour = findViewById(R.id.txtTotaltour);
        MyApplication myApplication = (MyApplication) getApplication();
        token = myApplication.getToken();
    }
    private void getData(){
        //Gọi Retrofit Service để lấy dữ liệu từ API
        historyTourService.sendData(token,currentPage,pageSize).enqueue(new Callback<ListTours>() {
            @Override
            public void onResponse(Call<ListTours> call, Response<ListTours> response) {
                //Log.d("AAA", "onResponse: " + response.body().getTours().get(0).getId());
                if(response.code()==200){
                    //Nhập vào danh sách dữ liệu
                    listTourData.clear();
                    List<Tour> listTourtmp;
                    listTourtmp = response.body().getTours();
                    for (Tour T:listTourtmp){
                        if(T.getStatus()!=-1)
                            listTourData.add(T);
                        else
                            Log.d("AAA", "onResponse: "+T.getId());
                    }
                    total = response.body().getTotal();
                    txtTotalTour.setText(total + " Tours");
                    //Cập nhật tại listView
                    listTourAdapter.notifyDataSetChanged();
                    int totalPage = (total / pageSize) + (total%pageSize == 0 ? 0 : 1);
                    btnNextPage.setEnabled(true);
                    btnPrevPage.setEnabled(true);
                    if(currentPage == totalPage) btnNextPage.setEnabled(false);
                    if(currentPage == 1) btnPrevPage.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<ListTours> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, "Unable to load list of tours", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        currentPage = 1;
        getData();
    }
}
