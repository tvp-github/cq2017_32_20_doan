package com.ygaps.travelapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ygaps.travelapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.FragmentActivity;
import com.ygaps.travelapp.APIService.AddStopPointsService;
import com.ygaps.travelapp.APIService.GetSearchStopPointService;
import com.ygaps.travelapp.APIService.GetStopPointService;
import com.ygaps.travelapp.APIService.GetTourInfoService;
import com.ygaps.travelapp.Adapter.ListStopPointAdapter;
import com.ygaps.travelapp.AppUtils.AddStopPointsBody;
import com.ygaps.travelapp.AppUtils.Message;
import com.ygaps.travelapp.AppUtils.OneCoord;
import com.ygaps.travelapp.AppUtils.RequestStoppointBody;
import com.ygaps.travelapp.Response.GetStopPoints;
import com.ygaps.travelapp.Response.SearchStopPoint;
import com.ygaps.travelapp.Response.StopPoint;
import com.ygaps.travelapp.Response.Tour;
import com.ygaps.travelapp.Retrofit.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private TextView mTapTextView;
    private TextView mCameraTextView;
    int type;
    String tourId;
    final int RESULT_OK = 001;
    EditText edtSearch;
    Button btnSearch;
    FloatingActionButton floatingActionButton;
    Geocoder geocoder;
    SearchView searchStopPoint;
    GetSearchStopPointService getSearchStopPointService;
    GetStopPointService getStopPointService;
    List<StopPoint> list;
    List<StopPoint> returnList;
    SimpleDateFormat simpleDateFormat;
    private void init(){
        Intent intent = getIntent();
        tourId = intent.getStringExtra("tourId");
        type = intent.getIntExtra("type",0);
        //type 1 : chọn vị trí . type 0 : thêm stoppoint type 2: khi chọn gửi trả về result
        String token;
        tourId = intent.getStringExtra("tourId");
        floatingActionButton = findViewById(R.id.btnListStopPoint);
        if (type ==1 ) floatingActionButton.hide();
        getStopPointService = ApiUtils.getGetStopPointService();
        getSearchStopPointService = ApiUtils.getGetSearchStopPointService();
        list = new ArrayList<StopPoint>();
        returnList = new ArrayList<StopPoint>();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
        searchStopPoint = findViewById(R.id.search_StopPoint);
        MyApplication myApplication = (MyApplication) getApplication();
        token = myApplication.getToken();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        init();
        //show list stoppoint
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayListStopPoint();
            }
        });

        searchStopPoint.setSubmitButtonEnabled(true);

        //Search location by geocoder
//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    List<Address> fromLocationName = geocoder.getFromLocationName(edtSearch.getText().toString(), 1, 8.5, 102.1833333, 23.283333, 109.45);
//                    LatLng location = new LatLng(fromLocationName.get(0).getLatitude(),fromLocationName.get(0).getLongitude());
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    public void displayListStopPoint(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.list_stop_points,null);
        ListView lstStopPoints = layout.findViewById(R.id.listStopPoint);
        final ListStopPointAdapter adapter = new ListStopPointAdapter(MapsActivity.this, R.layout.list_stop_points_item,returnList);
        lstStopPoints.setAdapter(adapter);
        lstStopPoints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int li, long l) {
                displayStopPointDialog(returnList.get(li),false);
            }
        });
        lstStopPoints.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int li, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle("Delete this stop point from list?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        returnList.remove(li);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No",null);
                builder.create().show();
                return true;
            }
        });

        AlertDialog.Builder view = new AlertDialog.Builder(this);
        view.setTitle("List Stop Points");
        view.setView(layout);
        view.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        view.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendData(1);
            }
        });
        AlertDialog dialog = view.create();
        dialog.show();
    }

    private void updateData(){
        GetTourInfoService getTourInfoService = ApiUtils.getGetTourInfoService();
        String token = ((MyApplication) getApplication()).getToken();
        getTourInfoService.sendData(token, Integer.parseInt(tourId) ).enqueue(new Callback<Tour>() {
            @Override
            public void onResponse(Call<Tour> call, Response<Tour> response) {
                List<StopPoint> listStopPoint = response.body().getStopPoints();
                if(response.code() == 200){
                    Log.d("AAA", "onResponse: "+ new Gson().toJson(listStopPoint));
                    for(int i = 0 ; i<returnList.size(); i++)
                    {
                        for (int j = 0 ; j<listStopPoint.size(); j++){
                            if (returnList.get(i).getServiceId().equals(listStopPoint.get(j). getServiceId()))
                            {
                                returnList.get(i).setId(listStopPoint.get(j).getId());
                                returnList.get(i).setServiceId(null);
                                break;
                            }
                        }
                    }
                    sendData(2);
                }
            }

            @Override
            public void onFailure(Call<Tour> call, Throwable t) {

            }
        });
    }
    //
    private void sendData(final int mode) {
        if(type == 2){
            Intent intent = new Intent();
            intent.putExtra("stoppoints",""+new Gson().toJson(returnList).toString());
            setResult(RESULT_OK, intent);
            finish();
            return;
        }
        AddStopPointsService addStopPointsService = ApiUtils.getAddStopPointsService();
        String token = ((MyApplication) getApplication()).getToken();
        Log.d("AAA", "onClick: " + new Gson().toJson(returnList) + "\n" + tourId);
        addStopPointsService.sendData(token, new AddStopPointsBody(tourId, returnList, null)).enqueue(new Callback<Message>() {
            @Override
            public synchronized void onResponse(Call<Message> call, Response<Message> response) {
                if (response.code() == 200) {

                    if (mode == 1){
                        updateData();
                        Toast.makeText(MapsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        returnList.clear();
                        if (type == 0) {
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                } else {
                    try {
                        Log.d("AAA", "onResponse: " + tourId);
                        Toast.makeText(MapsActivity.this, "" + response.code() + '-' + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<Message> call, Throwable t) {
            }
        });
    }

    public void displayStopPointDialog(final StopPoint stopPoint, final boolean addStopPoint) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.create_stop_point, null);
        //Ánh xạ các view từ layout
        final Spinner spinnerService = alertLayout.findViewById(R.id.spinner_serviceType);
        final Spinner spinnerProvice = alertLayout.findViewById(R.id.spinner_provice);
        final EditText edtName = alertLayout.findViewById(R.id.edit_stopPointname);
        final EditText edtAddress = alertLayout.findViewById(R.id.edit_address);
        final EditText edtMinCost = alertLayout.findViewById(R.id.edit_minCoststop);
        final EditText edtMaxCost = alertLayout.findViewById(R.id.edit_maxCoststop);
        final TextView txtDateArrive = alertLayout.findViewById(R.id.text_dateArrive);
        final TextView txtDateLeave = alertLayout.findViewById(R.id.text_dateLeave);
        final EditText edtTimeArrive = alertLayout.findViewById(R.id.text_timeArrive);
        final EditText edtTimeLeave = alertLayout.findViewById(R.id.text_timeLeave);
        ImageButton btnDateArrive = alertLayout.findViewById(R.id.btn_calendar_arrive);
        ImageButton btnDateLeave = alertLayout.findViewById(R.id.btn_calendar_leave);
        Calendar calendarLeave;
        Calendar calendarArrive;
        calendarArrive = Calendar.getInstance();
        calendarLeave = Calendar.getInstance();
        if(stopPoint.getArrivalAt()!=0 )
        {
            calendarArrive.setTimeInMillis(stopPoint.getArrivalAt());
            calendarLeave.setTimeInMillis(stopPoint.getLeaveAt());
        }
        txtDateArrive.setText(simpleDateFormat.format(calendarArrive.getTime()));
        txtDateArrive.setTag(calendarArrive);
        txtDateLeave.setText(simpleDateFormat.format(calendarLeave.getTime()));
        txtDateLeave.setTag(calendarLeave);
        int arrHour = calendarArrive.get(Calendar.HOUR_OF_DAY);
        int arrMinute = calendarArrive.get(Calendar.MINUTE);
        int leavHour = calendarLeave.get(Calendar.HOUR_OF_DAY);;
        int leavMinute = calendarLeave.get(Calendar.MINUTE);

        edtTimeArrive.setText((arrHour <10 ?"0": "")  + arrHour + ":" + (arrMinute <10 ?"0": "") +  arrMinute);
        edtTimeLeave.setText((leavHour <10 ?"0": "")  + leavHour + ":" + (leavMinute <10 ?"0": "") +  leavMinute);
        btnDateArrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = (Calendar) txtDateArrive.getTag();
                DatePickerDialog datePickerDialog = new DatePickerDialog(MapsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //cập nhật calendar
                        calendar.set(Calendar.YEAR,i);
                        calendar.set(Calendar.MONTH,i1);
                        calendar.set(Calendar.DATE,i2);
                        //Xuất ra
                        txtDateArrive.setText(simpleDateFormat.format(calendar.getTime()));
                        txtDateArrive.setTag(calendar);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
                datePickerDialog.show();

            }
        });
        btnDateLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = (Calendar) txtDateLeave.getTag();
                DatePickerDialog datePickerDialog = new DatePickerDialog(MapsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //cập nhật calendar
                        calendar.set(Calendar.YEAR,i);
                        calendar.set(Calendar.MONTH,i1);
                        calendar.set(Calendar.DATE,i2);
                        //Xuất ra

                        txtDateLeave.setText(simpleDateFormat.format(calendar.getTime()));
                        txtDateLeave.setTag(calendar);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
        //Đổ data cho spinner
        String[] service = {"Restaurant","Hotel","Rest station","Other"};
        String[] province = {"Hồ Chí Minh","Hà Nội","Đà Nẵng","Bình Dương","Đồng Nai","Khánh Hòa","Hải Phòng",
                "Long An","Quảng Nam","Bà Rịa Vũng Tàu","Đắk Lắk","Cần Thơ","Bình Thuận","Lâm Đồng","Thừa Thiên Huế",
                "Kiên Giang","Bắc Ninh","Quảng Ninh","Thanh Hóa","Nghệ An","Hải Dương","Gia Lai","Bình Phước","Hưng Yên",
                "Bình Định","Tiền Giang","Thái Bình","Bắc Giang","Hòa Bình","An Giang","Vĩnh Phúc","Tây Ninh","Thái Nguyên",
                "Lào Cai","Nam Định","Quảng Ngãi","Bến Tre","Đắk Nông","Cà Mau","Vĩnh Long","Ninh Bình","Phú Thọ","Ninh Thuận",
                "Phú Yên","Hà Nam","Hà Tĩnh","Đồng Tháp","Sóc Trăng","Kon Tum","Quảng Bình","Quảng Trị","Trà Vinh","Hậu Giang",
                "Sơn La","Bạc Liêu","Yên Bái","Tuyên Quang","Điện Biên","Lai Châu","Lạng Sơn","Hà Giang","Bắc Kạn","Cao Bằng"};
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,service);
        ArrayAdapter<String> proviceAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,province);
        spinnerService.setAdapter(serviceAdapter);
        spinnerProvice.setAdapter(proviceAdapter);
        //Đổ dữ liệu ra widget
        spinnerProvice.setSelection(Math.max(stopPoint.getProvinceId()-1,0));
        spinnerService.setSelection(Math.max(stopPoint.getServiceTypeId()-1,0));
        edtName.setText(stopPoint.getName());
        edtAddress.setText(stopPoint.getAddress());
        edtMinCost.setText(stopPoint.getMinCost());
        edtMaxCost.setText(stopPoint.getMaxCost());
        //Tạo khung thông báo
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        if(addStopPoint)
            alert.setTitle("Create stop point");
        else
            alert.setTitle("Add new stop point");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton(addStopPoint? "Create": "Add", null);
        final AlertDialog dialog = alert.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnAdd = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar arrivalAt = (Calendar)(txtDateArrive.getTag());
                        Calendar leaveAt = (Calendar)(txtDateLeave.getTag());
                        if(validate(edtName.getText().toString(),edtMinCost.getText().toString(),edtMaxCost.getText().toString())
                                &&validateHour(edtTimeArrive.getText().toString(),edtTimeLeave.getText().toString())) {
                            int hour, minute;
                            String[] time;
                            //get Arrival Time
                            time = edtTimeArrive.getText().toString().split(":");
                            hour = Integer.parseInt(time[0]);
                            minute = Integer.parseInt(time[1]);
                            //set arrivalTime
                            arrivalAt.set(Calendar.HOUR_OF_DAY, hour);
                            arrivalAt.set(Calendar.MINUTE, minute);
                            //getLeaveTime
                            time = edtTimeLeave.getText().toString().split(":");
                            hour = Integer.parseInt(time[0]);
                            minute = Integer.parseInt(time[1]);
                            //setLeaveTime
                            leaveAt.set(Calendar.HOUR_OF_DAY, hour);
                            leaveAt.set(Calendar.MINUTE, minute);
                            //set Time date
                            stopPoint.setArrivalAt(arrivalAt.getTimeInMillis());
                            stopPoint.setLeaveAt(leaveAt.getTimeInMillis());

                            stopPoint.setName(edtName.getText().toString());
                            stopPoint.setAddress(edtAddress.getText().toString());
                            stopPoint.setServiceTypeId(spinnerService.getSelectedItemPosition() + 1);
                            stopPoint.setProvinceId(spinnerProvice.getSelectedItemPosition() + 1);

                            stopPoint.setMinCost(edtMinCost.getText().toString());
                            stopPoint.setMaxCost(edtMaxCost.getText().toString());

                            if (addStopPoint) {
                                stopPoint.setId(null);
                                //Arrive and leave Time
                                LatLng markerPosition = new LatLng(Double.parseDouble(stopPoint.getLat()), Double.parseDouble(stopPoint.getLong()));
                                int resId;
                                switch (stopPoint.getServiceTypeId()) {
                                    case 1:
                                        resId = R.drawable.restaurant;
                                        break;
                                    case 2:
                                        resId = R.drawable.hotel;
                                        break;
                                    case 3:
                                        resId = R.drawable.rest_station;
                                        break;
                                    default:
                                        resId = R.drawable.other;
                                }
                                Marker current = mMap.addMarker(new MarkerOptions().position(markerPosition).title(stopPoint.getName()).icon(BitmapDescriptorFactory.fromResource(resId)));
                                current.setTag(stopPoint);
                                list.add(stopPoint);
                                returnList.add(stopPoint);

                            } else {
                                if (!returnList.contains(stopPoint)) {
                                    stopPoint.setServiceId(stopPoint.getId());
                                    stopPoint.setId(null);
                                    returnList.add(stopPoint);
                                }
                            }
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(MapsActivity.this, "Invalid input value", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        dialog.show();
    }

    private boolean validateHour(String ...str){
        for (int i = 0 ; i<str.length; i++) {
            if (!str[i].matches("^([2][0-3]|[0-1][0-9]|[0-9]):([0-5][0-9]|[0-9])$"))
                return false;
        }
        return true;
    }
    private boolean validate(String ...str) {
        for (int i = 0 ; i< str.length ; i++ )
            if (str[i].length() == 0)
                return false;
        return true;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng hcmus = new LatLng(10.7628247, 106.6813572);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 15.0f));
        if(type == 1)
        {
            mMap.setOnMapClickListener(this);
        }else
        {
            mMap.setOnMarkerClickListener(this);
            mMap.setOnMapLongClickListener(this);
        }
        mMap.setOnCameraIdleListener(this);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        displayStopPointDialog(new StopPoint(latLng),true);
    }

    @Override
    public void onCameraIdle() {
        LatLng latLng = mMap.getCameraPosition().target;
        MyApplication myApplication = (MyApplication) getApplication();
        final String token = myApplication.getToken();
        getStopPointService.sendData(token, new RequestStoppointBody(true,new OneCoord(latLng.latitude, latLng.longitude))).enqueue(new Callback<GetStopPoints>() {
            @Override
            public void onResponse(Call<GetStopPoints> call, Response<GetStopPoints> response) {
                if (response.code() == 200){
                    List<StopPoint> otherlist = response.body().getStopPoints();
                    for (int i = 0; i<otherlist.size();i++){
                            StopPoint stopPoint = otherlist.get(i);
                            if(!list.contains(stopPoint)) {
                                LatLng markerPosition = new LatLng(Double.parseDouble(stopPoint.getLat()), Double.parseDouble(stopPoint.getLong()));
                                int resId;
                                switch (stopPoint.getServiceTypeId()){
                                    case 1: resId = R.drawable.restaurant;
                                    break;
                                    case 2: resId = R.drawable.hotel;
                                    break;
                                    case 3: resId = R.drawable.rest_station;
                                    break;
                                    default: resId = R.drawable.other;
                                }
                                Marker current = mMap.addMarker(new MarkerOptions().position(markerPosition).title(stopPoint.getName()).icon(BitmapDescriptorFactory.fromResource(resId)));
                                current.setTag(stopPoint);
                                list.add(stopPoint);
                            }
                        }
                }
                else{
                    try {
                        Log.d("FFF", "onResponse: "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetStopPoints> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "Unable to get data", Toast.LENGTH_SHORT).show();
            }
        });

        searchStopPoint.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchStopPointService.sendData(token,newText, 1,1000).enqueue(new Callback<SearchStopPoint>() {
                    @Override
                    public void onResponse(Call<SearchStopPoint> call, Response<SearchStopPoint> response) {
                        Log.d("111","onResponse: "+response.code());
                        if (response.code() == 200){
                            mMap.clear();

                            LatLng latLng = mMap.getCameraPosition().target;
                            Log.d("111","total: "+response.body().getTotal());
                            Log.d("111","body: "+response.body().toString());
                            List<StopPoint> otherlist = response.body().getStopPoints();
                            Log.d("AAA","total list: "+otherlist.size());
                            for (int i = 0; i<otherlist.size();i++){
                                StopPoint stopPoint = otherlist.get(i);
                                Log.d("AAA","total list: "+stopPoint.getId());
                                LatLng markerPosition = new LatLng(Double.parseDouble(stopPoint.getLat()), Double.parseDouble(stopPoint.getLong()));
                                int resId;
                                switch (stopPoint.getServiceTypeId()){
                                    case 1: resId = R.drawable.restaurant;
                                        break;
                                    case 2: resId = R.drawable.hotel;
                                        break;
                                    case 3: resId = R.drawable.rest_station;
                                        break;
                                    default: resId = R.drawable.other;
                                }
                                Marker current = mMap.addMarker(new MarkerOptions().position(markerPosition).title(stopPoint.getName()).icon(BitmapDescriptorFactory.fromResource(resId)));
                                current.setTag(stopPoint);
                                list.add(stopPoint);

                            }
                        }
                        else{
                            try {
                                Log.d("FFF", "onResponse: "+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchStopPoint> call, Throwable t) {

                    }
                });
                return false;
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        StopPoint markerStopPoint = (StopPoint) marker.getTag();
        displayStopPointDialog(markerStopPoint, false);
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(type == 1) {
            Intent result = new Intent();
            result.putExtra("lat", latLng.latitude);
            result.putExtra("long", latLng.longitude);
            setResult(RESULT_OK,result);
            MapsActivity.this.finish();
        }
    }
}
