package hcmus.android.lighttour;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private TextView mTapTextView;
    private TextView mCameraTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

//        //Khởi tạo toolbar cho activity
//        Toolbar toolbar =findViewById(R.id.toolbar_map);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Create stop point");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
//        mTitle.setText(toolbar.getTitle());
//        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapClick(LatLng point) {
 //       mTapTextView.setText("tapped, point=" + point);
        Intent intent;
        intent = new Intent(MapsActivity.this, PointInformationActivity.class);
        startActivity(intent);
        //displayAlertDialog();

    }


    public void displayAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.create_stop_point, null);
//        final EditText etUsername = (EditText) alertLayout.findViewById(R.id.et_Username);
//        final EditText etPassword = (EditText) alertLayout.findViewById(R.id.et_Password);
//        final CheckBox cbShowPassword = (CheckBox) alertLayout.findViewById(R.id.cb_ShowPassword);



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



//        alert.setPositiveButton("Login", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // code for matching password
//                String user = etUsername.getText().toString();
//                String pass = etPassword.getText().toString();
//                Toast.makeText(getBaseContext(), "Username: " + user + " Password: " + pass, Toast.LENGTH_SHORT).show();
//            }
//        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }



//    @Override
//    public void onMapLongClick(LatLng point) {
//        mTapTextView.setText("long pressed, point=" + point);
//    }

//    @Override
//    public void onCameraIdle() {
//        mCameraTextView.setText(mMap.getCameraPosition().toString());
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.dashboard, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//
//        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);
//
//        SearchView searchView = null;
//        if (searchItem != null) {
//            searchView = (SearchView) searchItem.getActionView();
//        }
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
//        }
//        return super.onCreateOptionsMenu(menu);
//    }
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
        LatLng hochiminh = new LatLng(-25.363882,131.044922);
        mMap.addMarker(new MarkerOptions().position(hochiminh).title("Marker in Ho Chi Minh"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hochiminh));
        mMap.setOnMapClickListener(this);
//        mMap.setOnMapLongClickListener(this);
//        mMap.setOnCameraIdleListener(this);
    }
}
