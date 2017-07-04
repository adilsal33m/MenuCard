package com.adilsal33m.menucard;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by Adil Saleem on 3/1/2017.
 */

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "Starter", "Main Course", "Dessert" ,"Others","Bill"};
    private Context context;
    public BillFragment mBillFragment;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context, BillFragment billFragment) {
        super(fm);
        this.context = context;
        this.mBillFragment=billFragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return position<4?PageFragment.newInstance(position):this.mBillFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
