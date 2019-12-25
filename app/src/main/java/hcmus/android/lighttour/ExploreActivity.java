package hcmus.android.lighttour;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hcmus.android.lighttour.APIService.GetSearchStopPointService;
import hcmus.android.lighttour.APIService.GetStopPointService;
import hcmus.android.lighttour.AppUtils.OneCoord;
import hcmus.android.lighttour.AppUtils.RequestStoppointBody;
import hcmus.android.lighttour.Response.GetStopPoints;
import hcmus.android.lighttour.Response.SearchStopPoint;
import hcmus.android.lighttour.Response.StopPoint;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener {

        private GoogleMap mMap;
        private TextView mTapTextView;
        private TextView mCameraTextView;
        int type;
        final int RESULT_OK = 001;
        EditText edtSearch;
        Button btnSearch;
        Geocoder geocoder;
        GetStopPointService getStopPointService;
        GetSearchStopPointService getSearchStopPointService;
        List<StopPoint> list;
        List<StopPoint> returnList;
        SearchView searchStopPoint;
        String token;
        @Override
        public void onBackPressed() {
                super.onBackPressed();
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_maps);
                Intent intent = getIntent();
                type = intent.getIntExtra("type",-1);
//                edtSearch = findViewById(R.id.searchLocation);
//                btnSearch = findViewById(R.id.btnSearchLocation);
                getStopPointService = ApiUtils.getGetStopPointService();
                list = new ArrayList<StopPoint>();
                returnList = new ArrayList<StopPoint>();
                searchStopPoint = findViewById(R.id.search_StopPoint);
                getSearchStopPointService = ApiUtils.getGetSearchStopPointService();
                MyApplication myApplication = (MyApplication) getApplication();
                token = myApplication.getToken();
                searchStopPoint.setSubmitButtonEnabled(true);

//                geocoder = new Geocoder(ExploreActivity.this, Locale.getDefault());
//                btnSearch.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                                try {
//                                        List<Address> fromLocationName = geocoder.getFromLocationName(edtSearch.getText().toString(), 1, 8.5, 102.1833333, 23.283333, 109.45);
//                                        LatLng location = new LatLng(fromLocationName.get(0).getLatitude(),fromLocationName.get(0).getLongitude());
//                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f));
//                                } catch (IOException e) {
//                                        e.printStackTrace();
//                                }
//
//                        }
//                });
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);

        }


        @Override
        public void onMapClick(LatLng point) {
                if(type == 1) {
                        Intent result = new Intent();
                        result.putExtra("lat", point.latitude);
                        result.putExtra("long", point.longitude);
                        setResult(RESULT_OK,result);
                        ExploreActivity.this.finish();
                }
        }


        public void displayCreateStopPointDialog(LatLng latLng) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.create_stop_point, null);
                Spinner spinnerService = alertLayout.findViewById(R.id.spinner_serviceType);
                Spinner spinnerProvice = alertLayout.findViewById(R.id.spinner_provice);
                String[] service = {"Restaurant","Hotel","Rest station","Other"};
                String[] provice = {"Hồ Chí Minh","Hà Nội","Đà Nẵng","Bình Dương","Đồng Nai","Khánh Hòa","Hải Phòng","Long An","Quảng Nam","Bà Rịa Vũng Tàu","Đắk Lắk","Cần Thơ","Bình Thuận","Lâm Đồng","Thừa Thiên Huế","Kiên Giang","Bắc Ninh","Quảng Ninh","Thanh Hóa","Nghệ An","Hải Dương","Gia Lai","Bình Phước","Hưng Yên","Bình Định","Tiền Giang","Thái Bình","Bắc Giang","Hòa Bình","An Giang","Vĩnh Phúc","Tây Ninh","Thái Nguyên","Lào Cai","Nam Định","Quảng Ngãi","Bến Tre","Đắk Nông","Cà Mau","Vĩnh Long","Ninh Bình","Phú Thọ","Ninh Thuận","Phú Yên","Hà Nam","Hà Tĩnh","Đồng Tháp","Sóc Trăng","Kon Tum","Quảng Bình","Quảng Trị","Trà Vinh","Hậu Giang","Sơn La","Bạc Liêu","Yên Bái","Tuyên Quang","Điện Biên","Lai Châu","Lạng Sơn","Hà Giang","Bắc Kạn","Cao Bằng"};
                ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,service);
                ArrayAdapter<String> proviceAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,provice);
                spinnerService.setAdapter(serviceAdapter);
                spinnerProvice.setAdapter(proviceAdapter);
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Create stop point");
                alert.setView(alertLayout);
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                        }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
        }
        public void displayAddStopPointDialog(LatLng latLng) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.create_stop_point, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Create stop point");
                alert.setView(alertLayout);
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                        }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
        }

        public void displayPointInformationDialog() {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.point_information, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
//                alert.setTitle("Create stop point");
                alert.setView(alertLayout);
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                        }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
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
                LatLng hochiminh = new LatLng(10.7628247, 106.6813572);
//        Marker hcmus = mMap.addMarker(new MarkerOptions().position(hochiminh).title("Marker in Ho Chi Minh"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hochiminh, 15.0f));
                mMap.setOnMarkerClickListener(this);
                mMap.setOnMapClickListener(this);
                mMap.setOnMapLongClickListener(this);
                mMap.setOnCameraIdleListener(this);
        }

//        @Override
//        public void onMapLongClick(LatLng latLng) {
//                displayCreateStopPointDialog(latLng);
//        }

        @Override
        public void onMapLongClick(LatLng latLng) {
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
                Intent intent = new Intent(ExploreActivity.this, PointInformationActivity.class);
                intent.putExtra("stopPoint", markerStopPoint);
//                Bundle stopPoint = new Bundle();
//                stopPoint.putSerializable("stopPoint", markerStopPoint);
//                Fragment tab1_general = new tab1_general();
//                tab1_general.setArguments(stopPoint);
                startActivity(intent);
//                Bundle bundle = new Bundle();
//                bundle.putInt("markerId", markerStopPoint.getId());
//                // set Fragmentclass Arguments
//                Fragmentclass fragobj = new Fragmentclass();
//                fragobj.setArguments(bundle);
                return false;
        }
}
