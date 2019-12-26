package hcmus.android.lighttour;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import hcmus.android.lighttour.APIService.GetTourInfoService;
import hcmus.android.lighttour.Adapter.PageAdapter;
import hcmus.android.lighttour.Response.StopPoint;
import hcmus.android.lighttour.Response.Tour;
import hcmus.android.lighttour.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourInformationActivity extends AppCompatActivity
        implements tourinfo_tab1_general.OnFragmentInteractionListener, tourinfo_tab2_review.OnFragmentInteractionListener, View.OnClickListener{
    private TabLayout tablayout;
    private ViewPager viewPager;
    private Button tab1, tab2, tab3, tab4, tab5;
    Intent intent;
    Tour tour;
    int tourId;
    boolean loadedData = false;
    Boolean fromHistory;
    Bundle infoTour;
    private static FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public PageAdapter pageAdapter;


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private synchronized void getData(){
        MyApplication myApplication = (MyApplication) getApplication();
        GetTourInfoService getTourInfoService = ApiUtils.getGetTourInfoService();
        getTourInfoService.sendData(myApplication.getToken(),tour.getId()).enqueue(new Callback<Tour>() {
            @Override
            public synchronized void onResponse(Call<Tour> call, Response<Tour> response) {
                if (response.code() == 200)
                {
                    tour = response.body();
                    infoTour.putString("tour", new Gson().toJson(tour));
                    loadedData = true;
                    Fragment tourinfo_tab1_general = new tourinfo_tab1_general();//Get Fragment Instance
                    tourinfo_tab1_general.setArguments(infoTour);
                    //Replace default fragment
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, tourinfo_tab1_general).commit();
                }
            }

            @Override
            public void onFailure(Call<Tour> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_information);



        fragmentManager = this.getSupportFragmentManager();//Get Fragment Manager

        Intent intent=getIntent();
        tour= new Gson().fromJson(intent.getStringExtra("tour"),Tour.class);
        fromHistory = intent.getBooleanExtra("fromHistory",false);

        infoTour = new Bundle();
        infoTour.putString("tour", new Gson().toJson(tour));
        infoTour.putBoolean("fromHistory",fromHistory);
        getData();

        //Find Ids
        tab1 = (Button) findViewById(R.id.Tab1);
        tab2 = (Button) findViewById(R.id.Tab2);
        tab3 = (Button) findViewById(R.id.Tab3);
        tab4 = (Button) findViewById(R.id.Tab4);
        tab5 = (Button) findViewById(R.id.Tab5);

        //Implement Click Listener
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        tab5.setOnClickListener(this);
//
//        intent=getIntent();
//        stopPoint= (StopPoint) intent.getSerializableExtra("stopPoint");
//        stPoint = new Bundle();
//        stPoint.putSerializable("stopPoint", stopPoint);

        //Khởi tạo toolbar cho activity
        Toolbar toolbar =findViewById(R.id.toolbar_tour_info);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tour information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        // khởi tạo tab fragment cho activity
//        tablayout = (TabLayout) findViewById(R.id.tab_layout);
//        tab1 = (TabItem) findViewById(R.id.Tab1);
//        tab2 = (TabItem) findViewById(R.id.Tab2);
//        viewPager = findViewById(R.id.viewpager);
//
//        pageAdapter = new PageAdapter(getSupportFragmentManager(),tablayout.getTabCount());
//        viewPager.setAdapter(pageAdapter);
//

//        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//                if (tab.getPosition() == 0) {
//                    pageAdapter.notifyDataSetChanged();
//                    Intent intent=getIntent();
//                    StopPoint stopPoint= (StopPoint) intent.getSerializableExtra("stopPoint");
//                    Bundle stPoint = new Bundle();
//                    stPoint.putSerializable("stopPoint", stopPoint);
//                    Fragment tab1_general = new tab1_general();
//                    tab1_general.setArguments(stPoint);
//                }
//                    else if (tab.getPosition() == 1) {
//                        pageAdapter.notifyDataSetChanged();
//                    }
//            }
//
//
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
    }

    @Override
    public void onClick(View view) {
        if(loadedData) {
            Fragment tourinfo_tab2_review = new tourinfo_tab2_review();//Get Fragment Instance

            switch (view.getId()) {
                case R.id.Tab1:


                    Fragment tourinfo_tab1_general = new tourinfo_tab1_general();//Get Fragment Instance
//                infoTour.putSerializable("tour", tour);
                    tourinfo_tab1_general.setArguments(infoTour);

                    //Replace default fragment
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, tourinfo_tab1_general).commit();
                    break;
                case R.id.Tab2:
                    infoTour.putInt("tab", 2);
//                infoTour.putSerializable("tour", tour);
                    tourinfo_tab2_review.setArguments(infoTour);
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, tourinfo_tab2_review).commit();//now replace the argument fragment

                    /**  Note: If you are passing argument in fragment then don't use below code always replace fragment instance where we had set bundle as argument as we had done above else it will give exception  **/
                    //   fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new ArgumentFragment()).commit();
                    break;
                case R.id.Tab3:
                    infoTour.putInt("tab", 3);
//                infoTour.putSerializable("tour", tour);
                    tourinfo_tab2_review.setArguments(infoTour);
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, tourinfo_tab2_review).commit();//now replace the argument fragment

                    /**  Note: If you are passing argument in fragment then don't use below code always replace fragment instance where we had set bundle as argument as we had done above else it will give exception  **/
                    //   fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new ArgumentFragment()).commit();
                    break;
                case R.id.Tab4:
                    infoTour.putInt("tab", 4);
//                infoTour.putSerializable("tour", tour);
                    tourinfo_tab2_review.setArguments(infoTour);
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, tourinfo_tab2_review).commit();//now replace the argument fragment

                    /**  Note: If you are passing argument in fragment then don't use below code always replace fragment instance where we had set bundle as argument as we had done above else it will give exception  **/
                    //   fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new ArgumentFragment()).commit();
                    break;
                case R.id.Tab5:
                    infoTour.putInt("tab", 5);
//                infoTour.putSerializable("tour", tour);
                    tourinfo_tab2_review.setArguments(infoTour);
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, tourinfo_tab2_review).commit();//now replace the argument fragment

                    /**  Note: If you are passing argument in fragment then don't use below code always replace fragment instance where we had set bundle as argument as we had done above else it will give exception  **/
                    //   fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new ArgumentFragment()).commit();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + view.getId());
            }
        }
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
