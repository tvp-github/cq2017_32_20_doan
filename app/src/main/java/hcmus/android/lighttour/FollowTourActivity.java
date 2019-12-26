package hcmus.android.lighttour;

import android.os.Bundle;


import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


public class FollowTourActivity  extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener {

        private GoogleMap mMap;

        private void init(){

        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.follow_tour);
            init();


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
