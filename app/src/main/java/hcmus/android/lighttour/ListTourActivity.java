package hcmus.android.lighttour;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    Menu menu;
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

        //Gọi Retrofit Service để lấy dữ liệu từ API
        listToursService.sendData(token,10,1,null,null).enqueue(new Callback<ListTours>() {
            @Override
            public void onResponse(Call<ListTours> call, Response<ListTours> response) {
                Log.d("AAA", "onResponse: "+response.code() + response.body().getTours().toString());
                if(response.code()==200){
                    //Nhập vào danh sách dữ liệu
                    listTourData.addAll(response.body().getTours());
                    //Cập nhật tại listView
                    listTourAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ListTours> call, Throwable t) {
                Toast.makeText(ListTourActivity.this, "Unable to load list of tours", Toast.LENGTH_SHORT).show();
            }
        });
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
                intent.putExtra("tour", tour);
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



    }


    private void init() {
        listTourData = new ArrayList<Tour>();
        listTour = findViewById(R.id.listTour);
        imgUserAva = findViewById(R.id.avatarUser);
        btnCreate = findViewById(R.id.btnCreate);
        listToursService = ApiUtils.getListToursAPIService();
        MyApplication myApplication = (MyApplication) getApplication();
        token = myApplication.getToken();
    }
}
