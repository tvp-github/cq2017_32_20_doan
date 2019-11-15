package hcmus.android.lighttour;

import androidx.appcompat.app.AppCompatActivity;
import hcmus.android.lighttour.APIService.ListToursService;
import hcmus.android.lighttour.Adapter.ListTourAdapter;
import hcmus.android.lighttour.Response.ListTours;
import hcmus.android.lighttour.Response.Tour;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListTourActivity extends AppCompatActivity {
    List<Tour> listTourData;
    ListView listTour;
    ListTourAdapter listTourAdapter;
    ImageView imgUserAva;
    ListToursService listToursService;
    String token;
    ImageButton btnCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_tour);
        init();
        //Gọi Retrofit Service để lấy dữ liệu từ API
        listToursService.sendData(token,10,1,null,null).enqueue(new Callback<ListTours>() {
            @Override
            public void onResponse(Call<ListTours> call, Response<ListTours> response) {
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
        listTourAdapter = new ListTourAdapter(ListTourActivity.this,R.layout.list_item,listTourData);
        listTour.setAdapter(listTourAdapter);
        //Chuyển màn hình sang tạo tour
        btnCreate.setOnClickListener(new View.OnClickListener() {
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
