package hcmus.android.lighttour;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import hcmus.android.lighttour.Adapter.PageAdapter;

public class PointInformationActivity extends AppCompatActivity
        implements tab1_general.OnFragmentInteractionListener, tab2_review.OnFragmentInteractionListener{
    private TabLayout tablayout;
    private ViewPager viewPager;
    private TabItem tab1, tab2;
    public PageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.point_information);

        //Khởi tạo toolbar cho activity
        Toolbar toolbar =findViewById(R.id.toolbar_pt_info);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Point information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // khởi tạo tab fragment cho activity
        tablayout = (TabLayout) findViewById(R.id.tab_layout);
        tab1 = (TabItem) findViewById(R.id.Tab1);
        tab2 = (TabItem) findViewById(R.id.Tab2);
        viewPager = findViewById(R.id.viewpager);

        pageAdapter = new PageAdapter(getSupportFragmentManager(),tablayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pageAdapter.notifyDataSetChanged();
                }
                    else if (tab.getPosition() == 1) {
                        pageAdapter.notifyDataSetChanged();
                    }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
