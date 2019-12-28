package com.ygaps.travelapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.fragment.app.FragmentActivity;

import com.ygaps.travelapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.IOException;

import com.ygaps.travelapp.Response.Tour;


public class FollowTourActivity  extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener {

    Tour tour;
    private GoogleMap mMap;
    FloatingActionButton floatbtnRecord;
    FloatingActionButton floatbtnMessage;
    FloatingActionButton floatbtnSpeed;
    MediaRecorder myAudioRecorder;
    String outputFile;


        private void init(){
            floatbtnRecord = findViewById(R.id.floatbtnRecord);
            floatbtnMessage = findViewById(R.id.floatbtnMessage);

        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.follow_tour);
            init();

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


}
