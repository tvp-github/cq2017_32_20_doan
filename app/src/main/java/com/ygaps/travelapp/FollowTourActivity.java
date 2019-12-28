package com.ygaps.travelapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.reflect.TypeToken;
import com.ygaps.travelapp.APIService.GetCoordinatesOfMembersService;
import com.ygaps.travelapp.APIService.GetNotificationOnRoadService;
import com.ygaps.travelapp.APIService.SendSpeedService;
import com.ygaps.travelapp.AppUtils.CurrentLocationBody;
import com.ygaps.travelapp.AppUtils.SendSpeedBody;
import com.ygaps.travelapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.ygaps.travelapp.Response.ListOnRoad;
import com.ygaps.travelapp.Response.Tour;
import com.ygaps.travelapp.Response.UserLocation;
import com.ygaps.travelapp.Retrofit.ApiUtils;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class FollowTourActivity  extends FragmentActivity implements LocationListener, OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener {

    Tour tour;
    String token;
    private GoogleMap mMap;
    FloatingActionButton floatbtnRecord;
    FloatingActionButton floatbtnMessage;
    FloatingActionButton floatbtnSpeed;
    MediaRecorder myAudioRecorder;
    String outputFile;
    private final int REQUEST_CODE = 005;
    Handler handler;
    MyRunnable myRun;
    LocationManager locationManager;
    double currentLat, currentLong;
    MyApplication myApplication;
    GetCoordinatesOfMembersService getCoordinatesOfMembersService;
    boolean hasLocation = false;
    private int idUser;
    boolean didClear = false;
    @RequiresApi(api = Build.VERSION_CODES.M)
        @SuppressLint("HandlerLeak")
        private void init(){
            myApplication = (MyApplication) getApplication();
            floatbtnRecord = findViewById(R.id.floatbtnRecord);
            floatbtnMessage = findViewById(R.id.floatbtnMessage);
            floatbtnSpeed = findViewById(R.id.floatbtnSpeed);
            token = myApplication.getToken();
            idUser = myApplication.getIdUser();
            getCoordinatesOfMembersService = ApiUtils.getGetCoordinatesOfMembersService();
            handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    boolean noti = msg.getData().getBoolean("noti", false);
                    if(!noti) {
                        Type listType = new TypeToken<List<UserLocation>>() {
                        }.getType();
                        List<UserLocation> list = new Gson().fromJson(msg.getData().getString("info"), listType);
                        if (!didClear){
                            mMap.clear();
                            didClear = true;
                        }
                        else
                            didClear = false;
                        for (UserLocation userLocation : list) {
                            String userName = userLocation.getId();
                            for (int i = 0; i < tour.getMembers().size(); i++)
                                if (tour.getMembers().get(i).getId() == Integer.parseInt(userLocation.getId())) {
                                    userName = tour.getMembers().get(i).getName();
                                    break;
                                }

                            if (userLocation.getId().equals("" + myApplication.getIdUser())) {
                                mMap.addMarker(new MarkerOptions().position(new LatLng(userLocation.getLat(), userLocation.get_long())).title(userName).icon(BitmapDescriptorFactory.fromResource(R.drawable.other)));
                            } else
                                mMap.addMarker(new MarkerOptions().position(new LatLng(userLocation.getLat(), userLocation.get_long())).title(userName));
                        }
                    }
                    else{
                        if (!didClear){
                            mMap.clear();
                            didClear = true;
                        }
                        else
                            didClear = false;
                        ListOnRoad listOnRoad = new Gson().fromJson(msg.getData().getString("notilist"), ListOnRoad.class);
                        for (ListOnRoad.OnRoadNoti onRoadNoti : listOnRoad.getNotiList()){
                            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(onRoadNoti.getLat()), Double.parseDouble(onRoadNoti.getLong()))).title(""+onRoadNoti.getSpeed()));
                        }
                    }
                }
            };


            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION} , REQUEST_CODE);
            }
            else {
                if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) )
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!= null){
                        currentLat = location.getLatitude();
                        currentLong = location.getLongitude();
                        hasLocation = true;
                    }
                else {
                    showAlertToEnableGPS();
                }
            }

        }
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.follow_tour);
            init();
            floatbtnSpeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(hasLocation) {
                        displayAlertDialog();
                    }
                }
            });
            Intent intent=getIntent();
            tour= new Gson().fromJson(intent.getStringExtra("tour"),Tour.class);
            floatbtnRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayRecordVoice();
                }
            });

            floatbtnMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FollowTourActivity.this, TextMessageActivity.class);
                    intent.putExtra("tour", new Gson().toJson(tour));
                    startActivity(intent);
                }
            });

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }

    private void displayAlertDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.speed_input,null);
        final EditText edtSpeed = view.findViewById(R.id.edt_speed);
        ImageButton btn50 = view.findViewById(R.id.imgbtn_50);
        ImageButton btn60 = view.findViewById(R.id.imgbtn_60);
        btn50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSpeed.setText("50");
            }
        });
        btn60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSpeed.setText("60");
            }
        });


        builder.setView(view)
                        .setTitle("Send speed").setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendSpeed(Integer.parseInt(edtSpeed.getText().toString()));
            }
        });
            final AlertDialog dialog = builder.create();
            dialog.show();
    }

    private void sendSpeed(int speed) {
        SendSpeedService sendSpeedService = ApiUtils.getSendSpeedService();
        sendSpeedService.sendData(token, new SendSpeedBody(currentLat,currentLong,tour.getId(),idUser,3,speed)).enqueue(new Callback<com.ygaps.travelapp.AppUtils.Message>() {
            @Override
            public void onResponse(Call<com.ygaps.travelapp.AppUtils.Message> call, Response<com.ygaps.travelapp.AppUtils.Message> response) {
                if(response.code() == 200){
                    Toast.makeText(FollowTourActivity.this, "Send speed successful", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(FollowTourActivity.this, "Send speed failed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<com.ygaps.travelapp.AppUtils.Message> call, Throwable t) {
                Toast.makeText(FollowTourActivity.this, "Unable to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayRecordVoice() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.record_dialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Audio Record");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        final Button btnRecord;
        final Button btnStop;
        final Button btnPlay;
        btnRecord = alertLayout.findViewById(R.id.btnRecord);
        btnStop = alertLayout.findViewById(R.id.btnStop);
        btnPlay = alertLayout.findViewById(R.id.btnPlay);
        btnStop.setEnabled(false);
        btnPlay.setEnabled(false);

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setAudioSamplingRate(16000);
        myAudioRecorder.setOutputFile(outputFile);


        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d("111","start: ");
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                } catch (IllegalStateException ise) {
                    // make something ...
                } catch (IOException ioe) {
                    // make something
                }
                btnRecord.setEnabled(false);
                btnRecord.setBackgroundResource(R.drawable.recordsmall);
                btnStop.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try{
                   myAudioRecorder.stop();
                   myAudioRecorder.release();
                   myAudioRecorder = null;
               }catch(RuntimeException stopException){
                   //handle cleanup here
               }


                btnRecord.setEnabled(true);
                btnRecord.setBackgroundResource(R.drawable.record);
                btnStop.setEnabled(false);
                btnPlay.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio Recorder stopped", Toast.LENGTH_LONG).show();
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    Log.d("111","start playing: ");
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // make something
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void showAlertToEnableGPS() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Turn On GPS please!")
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        FollowTourActivity.this.finish();
                    }
                })
                .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                }).create().show();
    }
    @SuppressLint("MissingPermission")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 0) {
            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if(provider != null){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location!= null){
                    currentLat = location.getLatitude();
                    currentLong = location.getLongitude();
                    hasLocation = true;
                }
            }
            else
            {
                startActivity(getIntent());
                finish();
            }
        }

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, FollowTourActivity.this);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location!= null){
                    currentLat = location.getLatitude();
                    currentLong = location.getLongitude();
                    hasLocation = true;
                }
            }
        }
        else finish();
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
            myRun = new MyRunnable();
            Thread t1 = new Thread(myRun, "T1");
            t1.start();
        }

        @Override
        public void onMapLongClick(LatLng latLng) {

        }

        @Override
        public void onCameraIdle() {
            LatLng latLng = mMap.getCameraPosition().target;

        }

        @Override
        public boolean onMarkerClick(Marker marker) {

            return false;
        }

        @Override
        public void onMapClick(LatLng latLng) {

        }

    @Override
    public void onLocationChanged(Location location) {
        currentLat = location.getLatitude();
        currentLong = location.getLongitude();
        hasLocation = true;
        Log.d("TTTT", "onLocationChanged: ");
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void sendLocationInfo(){
        //send current lat long
        Log.d("TTTT", "sendLocationInfo: send");
        String userId = ""+idUser;
        String tourId = ""+tour.getId();
        double lat= currentLat;
        double _long = currentLong;
        Log.d("TTTT", "sendLocationInfo: "+token+'\n'+userId+'\n'+tourId+'\n'+lat+'\n'+_long);
        getCoordinatesOfMembersService.sendData(token, new CurrentLocationBody(userId, tourId, lat, _long)).enqueue(new Callback<List<UserLocation>>() {
            @Override
            public void onResponse(Call<List<UserLocation>> call, Response<List<UserLocation>> response) {
                if(response.code() == 200)
                {
                    Message message= handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("info",new Gson().toJson(response.body()));
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<List<UserLocation>> call, Throwable t) {
                Toast.makeText(FollowTourActivity.this, "Unable to connect to server", Toast.LENGTH_SHORT).show();
            }
        });

    }


    class MyRunnable implements Runnable{
        private volatile boolean exit = false;

        @SuppressLint("MissingPermission")
        public void run() {
            while(!exit){
                if(hasLocation)
                    sendLocationInfo();
                    getNotificationOnRoad();
                try {

                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("TTTT", "run: Thread is running...."+currentLat+ '-' + currentLong);
            }

            Log.d("TTTT", "run: Thread is stopped....");
        }

        public void stop(){
            exit = true;
        }
    }

    private void getNotificationOnRoad() {
            GetNotificationOnRoadService getNotificationOnRoadService = ApiUtils.getNotificationOnRoadService();
            getNotificationOnRoadService.sendData(token,tour.getId(),1,100).enqueue(new Callback<ListOnRoad>() {
                @Override
                public void onResponse(Call<ListOnRoad> call, Response<ListOnRoad> response) {
                    if(response.code() == 200)
                    {
                        Message message = handler.obtainMessage();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("noti",true);
                        bundle.putString("notilist", new Gson().toJson(response.body()));
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                }

                @Override
                public void onFailure(Call<ListOnRoad> call, Throwable t) {

                }
            });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myRun!=null) myRun.stop();
    }
}
