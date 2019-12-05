package hcmus.android.lighttour.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import hcmus.android.lighttour.tab1_general;
import hcmus.android.lighttour.tab2_review;

public class PageAdapter extends FragmentPagerAdapter {

    private  int numoftabs;
    public PageAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.numoftabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new tab1_general();
            case 1:
                return new tab2_review();
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return numoftabs;
    }
}
