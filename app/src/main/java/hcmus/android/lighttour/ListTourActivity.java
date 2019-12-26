package hcmus.android.lighttour;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
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
    List<Tour> listData;
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
    ImageButton btnNoti;
    TextView txtTotalTour;
    SearchView search_tour;
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


        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTourActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        //Chuyển màn hình sang explore điểm dừng
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListTourActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });

        //Chuyển màn hình sang tạo tour
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
                Intent intent = new Intent(ListTourActivity.this, HistoryActivity.class);
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

        listTour.setTextFilterEnabled(true);
        search_tour.setSubmitButtonEnabled(true);
        search_tour.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("AAA","change");
                Log.d("AAA","text:" + newText + newText.length());

//                if (newText.length()>0){

                    listTourAdapter.filter(newText);
                    txtTotalTour.setText(listTourAdapter.getCount() + " Results");
                    int totalPage = (listTourAdapter.getCount() / ROWPERPAGE) + (listTourAdapter.getCount()%ROWPERPAGE == 0 ? 0 : 1);
                    btnNextPage.setEnabled(true);
                    btnPrevPage.setEnabled(true);
                    if(currentPage == totalPage || listTourAdapter.getCount()==0) btnNextPage.setEnabled(false);
                    if(currentPage == 1 ) btnPrevPage.setEnabled(false);
               // }
//                else{
//                    listTourAdapter.filter("%");
//                    txtTotalTour.setText(0 + " Results");
//                    btnNextPage.setEnabled(false);
//                    btnPrevPage.setEnabled(false);
//
//                }

                return false;
            }
        });
        search_tour.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("AAA","close");
                Log.d("AAA","count "+ listTourAdapter.getCount());
                Log.d("AAA","count "+ listTourData.size());
                //Cập nhật tại listView
                getData();
//                listTourAdapter.notifyDataSetChanged();
//                listTourAdapter = new ListTourAdapter(ListTourActivity.this, R.layout.list_item,listData);
//                listTour.setAdapter(listTourAdapter);
                txtTotalTour.setText(listTourAdapter.getCount() + " Tours");
                int totalPage = (listTourAdapter.getCount() / ROWPERPAGE) + (listTourAdapter.getCount()%ROWPERPAGE == 0 ? 0 : 1);
                btnNextPage.setEnabled(true);
                btnPrevPage.setEnabled(true);
                if(currentPage == totalPage || listTourAdapter.getCount()==0) btnNextPage.setEnabled(false);
                if(currentPage == 1) btnPrevPage.setEnabled(false);
                return false;
            }
        });
    }


    private void init() {
        listTourData = new ArrayList<Tour>();
        listData = new ArrayList<Tour>();
        listTour = findViewById(R.id.listTour);
        btnNextPage = findViewById(R.id.btnNextPage_listtour);
        btnNoti = findViewById(R.id.btnNoti);
        btnPrevPage = findViewById(R.id.btnPrevPage_listtour);
        imgbtnHistory = findViewById(R.id.btnHistory);
        btnAddTour = (FloatingActionButton) findViewById(R.id.btnAddTour);
        search_tour = findViewById(R.id.search_tour);
        btnExplore = (ImageButton) findViewById(R.id.btnExplore);
        txtTotalTour = findViewById(R.id.txtTotaltour);
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
                    total = response.body().getTotal();
                    txtTotalTour.setText(total + " Tours");
                    listTourData.clear();
                    listTourData.addAll(response.body().getTours());
                    listData.clear();
                    listData.addAll(response.body().getTours());
                    //Cập nhật tại listView
                    listTourAdapter.notifyDataSetChanged();
                    listTourAdapter = new ListTourAdapter(ListTourActivity.this, R.layout.list_item,listTourData);
                    listTour.setAdapter(listTourAdapter);
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

//    @Override
//    public boolean onQueryTextSubmit(String query) {
////        if(listTourData.contains(query)){
////            listTourAdapter.getFilter().filter(query);
////        }else{
////            Toast.makeText(ListTourActivity.this, "No Match found",Toast.LENGTH_LONG).show();
////        }
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
////        Log.d("AAA","change" + newText);
////          listTourAdapter.getFilter().filter( newText);
//          listTourAdapter.filter(newText);
//          txtTotalTour.setText(listTourAdapter.getCount() + " Results");
//        return true;
//
//    }

}
