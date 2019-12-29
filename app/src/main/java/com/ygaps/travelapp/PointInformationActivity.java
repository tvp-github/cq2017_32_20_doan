package com.ygaps.travelapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.ygaps.travelapp.R;
import com.google.android.material.tabs.TabLayout;

import com.ygaps.travelapp.Adapter.PageAdapter;
import com.ygaps.travelapp.Response.StopPoint;

public class PointInformationActivity extends AppCompatActivity
        implements tab1_general.OnFragmentInteractionListener, tab2_review.OnFragmentInteractionListener, View.OnClickListener{
    private TabLayout tablayout;
    private ViewPager viewPager;
    private Button tab1, tab2;
    Intent intent;
    StopPoint stopPoint;
    Bundle stPoint;
    private static FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public PageAdapter pageAdapter;


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.point_information);

        //Find Ids
        tab1 = (Button) findViewById(R.id.Tab1);
        tab2 = (Button) findViewById(R.id.Tab2);

        //Implement Click Listener
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);

        fragmentManager = this.getSupportFragmentManager();//Get Fragment Manager

        Intent intent=getIntent();
        stopPoint= (StopPoint) intent.getSerializableExtra("stopPoint");

        Fragment tab1_general = new tab1_general();//Get Fragment Instance
        stPoint = new Bundle();
        stPoint.putSerializable("stopPoint", stopPoint);
        tab1_general.setArguments(stPoint);
        tab1.setBackgroundResource(R.drawable.bg_tab_active);
        tab2.setBackgroundResource(R.drawable.bg_tab);

        //Replace default fragment
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, tab1_general).commit();


//
//        intent=getIntent();
//        stopPoint= (StopPoint) intent.getSerializableExtra("stopPoint");
//        stPoint = new Bundle();
//        stPoint.putSerializable("stopPoint", stopPoint);

        //Khởi tạo toolbar cho activity
        Toolbar toolbar =findViewById(R.id.toolbar_pt_info);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Point information");
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
        switch (view.getId()) {
            case R.id.Tab1:
                tab1.setBackgroundResource(R.drawable.bg_tab_active);
                tab2.setBackgroundResource(R.drawable.bg_tab);

                Fragment tab1_general = new tab1_general();//Get Fragment Instance
                stPoint.putSerializable("stopPoint", stopPoint);
                tab1_general.setArguments(stPoint);

                //Replace default fragment
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, tab1_general).commit();
                break;
            case R.id.Tab2:
                tab1.setBackgroundResource(R.drawable.bg_tab);
                tab2.setBackgroundResource(R.drawable.bg_tab_active);
                Fragment tab2_review = new tab2_review();//Get Fragment Instance
                stPoint.putSerializable("stopPoint", stopPoint);
                tab2_review.setArguments(stPoint);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, tab2_review).commit();//now replace the argument fragment

                /**  Note: If you are passing argument in fragment then don't use below code always replace fragment instance where we had set bundle as argument as we had done above else it will give exception  **/
                //   fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new ArgumentFragment()).commit();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
