package hcmus.android.lighttour;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import hcmus.android.lighttour.APIService.CreateToursService;
import hcmus.android.lighttour.AppUtils.CreateTourBody;
import hcmus.android.lighttour.AppUtils.Message;
import hcmus.android.lighttour.Response.CreateTours;
import hcmus.android.lighttour.Response.Tour;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTourActivity extends AppCompatActivity {
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    CreateToursService createToursService;
    EditText edtName;
    TextView txtStartDate;
    TextView txtEndDate;
    EditText edtAdults;
    EditText edtChildren;
    EditText edtMinCost;
    EditText edtMaxCost;
    //ImageView imgAvatar;
    CheckBox checkPrivate;
    Button btnCreateTour;
    ImageButton btnStartDate;
    ImageButton btnEndDate;
    //Ánh xạ các view và khởi tạo Service
    Button btnSelectStart;
    Button btnSelectEnd;
    public void init(){
        edtName = findViewById(R.id.edit_tour_name);
        txtStartDate = findViewById(R.id.startDate);
        txtEndDate = findViewById(R.id.endDate);
        edtAdults = findViewById(R.id.edit_adults);
        edtChildren = findViewById(R.id.edit_children);
        edtMinCost = findViewById(R.id.edit_minCost);
        edtMaxCost = findViewById(R.id.edit_maxCost);
        //imgAvatar =findViewById(R.id.img_avatar);
        checkPrivate = findViewById(R.id.check_privateTour);
        btnCreateTour = findViewById(R.id.btn_createTour);
        btnStartDate = findViewById(R.id.btnStartDate);
        btnEndDate = findViewById(R.id.btnEndDate);
        createToursService = ApiUtils.getCreateToursService();
//        btnSelectStart = findViewById(R.id.select1);
//        btnSelectEnd = findViewById(R.id.select2);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_tour);
        init();

        //Khởi tạo toolbar cho activity
        Toolbar toolbar =findViewById(R.id.toolbar_create_tour);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create tour");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    //
//        btnSelectStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CreateTourActivity.this, MapsActivity.class);
//                intent.putExtra("type",1);
//                startActivityForResult(intent,001);
//            }
//        });
//        btnSelectEnd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    //
        //Khi người dùng bấm nút Create Tour
        btnCreateTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hide keyboard
                View myView = CreateTourActivity.this.getCurrentFocus();
                if (myView != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
//                //Ngăn người dùng thay đổi dữ liệu
//                DisableInputField();
//                String name = validate(edtName.getText().toString());
//                Long startDate = Long.parseLong(validate(txtStartDate.getText().toString()));
//                Long endDate = Long.parseLong(validate(txtEndDate.getText().toString()));
//                int adults = Integer.parseInt(validate(edtAdults.getText().toString()));
//                int childs = Integer.parseInt(validate(edtChildren.getText().toString()));
//                int minCost = Integer.parseInt(validate(edtMinCost.getText().toString()));
//                int maxCost = Integer.parseInt(validate(edtMaxCost.getText().toString()));
//                boolean isPrivate = checkPrivate.isChecked();
                //Send to server
                CreateToursService createToursService = ApiUtils.getCreateToursService();
                MyApplication myApplication = (MyApplication) getApplication();
                String token = myApplication.getToken();
                String name = edtName.getText().toString();
                long startDate = ((Calendar)(txtStartDate.getTag())).getTimeInMillis();
                long endDate = ((Calendar)(txtEndDate.getTag())).getTimeInMillis();
                Boolean isPrivate = checkPrivate.isChecked();
                int adults = Integer.parseInt(edtAdults.getText().toString());
                int children = Integer.parseInt(edtChildren.getText().toString());
                int minCost = Integer.parseInt(edtMinCost.getText().toString());
                int maxCost = Integer.parseInt(edtMaxCost.getText().toString());
                Log.d("AAA", "onClick: "+'-'+token+'-'+startDate+'-'+endDate+'-'+isPrivate+'-'+name+'-'+adults+'-'+children+'-'+minCost+'-'+maxCost);
                createToursService.sendData(token,new CreateTourBody(name,startDate,endDate,0,0,0,0,isPrivate,adults,children,minCost,maxCost,null)).enqueue(new Callback<Tour>() {
                    @Override
                    public void onResponse(Call<Tour> call, Response<Tour> response) {
                        Log.d("AAA", "onResponse: "+response.code());
                        if(response.code()==200){
                            goToAddStopPoint(response.body().getId());
                        }
                        else {
                            try {
                                Log.d("AAA", "onResponse: "+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Tour> call, Throwable t) {
                        Log.d("AAA", "onFailure: "+call.request().toString());
                        Toast.makeText(CreateTourActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                    }
                });
               // sendCreateTour(name,startDate,endDate,adults,childs,minCost,maxCost);

            }
        });

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo calendar lưu giá trị nhập vào
                final Calendar calendarStart = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTourActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //cập nhật calendar
                        calendarStart.set(Calendar.YEAR, i);
                        calendarStart.set(Calendar.MONTH, i1);
                        calendarStart.set(Calendar.DATE, i2);
                        //Xuất ra
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        txtStartDate.setText(simpleDateFormat.format(calendarStart.getTime()));
                        txtStartDate.setTag(calendarStart);
                    }
                }, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo calendar lưu giá trị nhập vào
                final Calendar calendarEnd = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTourActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //cập nhật calendar
                        calendarEnd.set(Calendar.YEAR, i);
                        calendarEnd.set(Calendar.MONTH, i1);
                        calendarEnd.set(Calendar.DATE, i2);
                        //Xuất ra
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        txtEndDate.setText(simpleDateFormat.format(calendarEnd.getTime()));
                        txtEndDate.setTag(calendarEnd);
                    }
                }, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 001 && resultCode == 001){
            Toast.makeText(this, ""+ data.getDoubleExtra("lat",0.0f) + " "+ data.getDoubleExtra("long", 0.0f), Toast.LENGTH_SHORT).show();
        }
    }

    //Ngăn việc thay đổi dữ liệu
    private void DisableInputField() {
        edtName.setEnabled(false);
        edtAdults.setEnabled(false);
        edtChildren.setEnabled(false);
        edtMinCost.setEnabled(false);
        edtMaxCost.setEnabled(false);
        checkPrivate.setEnabled(false);
        btnStartDate.setEnabled(false);
        btnEndDate.setEnabled(false);
        btnCreateTour.setEnabled(false);
    }
    private void EnableInputField() {
        edtName.setEnabled(true);
        edtAdults.setEnabled(true);
        edtChildren.setEnabled(true);
        edtMinCost.setEnabled(true);
        edtMaxCost.setEnabled(true);
        checkPrivate.setEnabled(true);
        btnStartDate.setEnabled(true);
        btnEndDate.setEnabled(true);
        btnCreateTour.setEnabled(true);
    }

    private String validate(String s){
        if (s.length()>0) return s;
        else return null;
    }
    private void goToAddStopPoint(int tourId){
        Intent intent;
        intent = new Intent(CreateTourActivity.this, MapsActivity.class);
        intent.putExtra("tourId","4525");
        startActivity(intent);
        finish();
    }
}
