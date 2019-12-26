package hcmus.android.lighttour;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import hcmus.android.lighttour.APIService.ListToursService;
import hcmus.android.lighttour.Adapter.ListTourAdapter;
import hcmus.android.lighttour.Response.ListTours;
import hcmus.android.lighttour.Response.Tour;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTourActivity extends AppCompatActivity {
    List<Tour> listTourData;
    ListView listTour;
    ListTourAdapter listTourAdapter;
    ImageView imgUserAva;
    ListToursService listToursService;
    String token;
    ImageButton btnCreate;
    ImageButton btnExplore;
    FloatingActionButton btnAddTour;
    Button btnPrevPage;
    Button btnNextPage;
    Menu menu;
    ImageButton imgbtnHistory;
    ImageButton btnSettings;
    int ROWPERPAGE = 30;
    int currentPage = 1;
    int total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_tour);
        init();
        //Gọi khởi tạo toolbar
        Toolbar toolbar =findViewById(R.id.toolbar_list_tour);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Travel Assistant");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getData();

        //Init Adapter, set Adapter to listTour
        listTourAdapter = new ListTourAdapter(ListTourActivity.this, R.layout.list_item,listTourData);
        listTour.setAdapter(listTourAdapter);
        //Log.d("AAA", "onResponse: "+(listTour.getItemAtPosition(1).toString()));
        listTour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Tour tour=(Tour) listTourData.get(position);
               Log.d("AAA", "onResponse: "+ position);
                Log.d("AAA", "onResponse: "+ tour.getId());
               Log.d("AAA", "onResponse: "+((Tour)  listTourData.get(position)).toString());
                Intent intent = new Intent(ListTourActivity.this, TourInformationActivity.class);
                intent.putExtra("tour", new Gson().toJson(tour));
                startActivity(intent);
            }
        });
        //Chuyển màn hình sang tạo tour
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListTourActivity.this, CreateTourActivity.class);
                startActivity(intent);
            }
        });

        btnExplore = (ImageButton) findViewById(R.id.btnExplore);
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListTourActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });

        //Chuyển màn hình sang tạo tour
        btnAddTour = (FloatingActionButton) findViewById(R.id.btnAddTour);

        btnAddTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListTourActivity.this, CreateTourActivity.class);
                startActivity(intent);
            }
        });

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
        imgbtnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListTourActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });

        // Chuyển màn hình sang settings
        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
               startActivity(new Intent(ListTourActivity.this, SettingsActivity.class));
           }
        });
    }


    private void init() {
        listTourData = new ArrayList<Tour>();
        listTour = findViewById(R.id.listTour);
        imgUserAva = findViewById(R.id.avatarUser);
        btnCreate = findViewById(R.id.btnCreate);
        btnNextPage = findViewById(R.id.btnNextPage_listtour);
        btnPrevPage = findViewById(R.id.btnPrevPage_listtour);
        imgbtnHistory = findViewById(R.id.btnHistory);
        listToursService = ApiUtils.getListToursAPIService();
        MyApplication myApplication = (MyApplication) getApplication();
        token = myApplication.getToken();
    }
    private void getData(){
        //Gọi Retrofit Service để lấy dữ liệu từ API
        listToursService.sendData(token,ROWPERPAGE,currentPage,null,true).enqueue(new Callback<ListTours>() {
            @Override
            public void onResponse(Call<ListTours> call, Response<ListTours> response) {
                Log.d("AAA", "onResponse: " + response.body().getTours().get(0).getId());
                if(response.code()==200){
                    //Nhập vào danh sách dữ liệu
                    listTourData.clear();
                    listTourData.addAll(response.body().getTours());
                    total = response.body().getTotal();
                    //Cập nhật tại listView
                    listTourAdapter.notifyDataSetChanged();
                    int totalPage = (total / ROWPERPAGE) + (total%ROWPERPAGE == 0 ? 0 : 1);
                    btnNextPage.setEnabled(true);
                    btnPrevPage.setEnabled(true);
                    if(currentPage == totalPage) btnNextPage.setEnabled(false);
                    if(currentPage == 1) btnPrevPage.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<ListTours> call, Throwable t) {
                Toast.makeText(ListTourActivity.this, "Unable to load list of tours", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
