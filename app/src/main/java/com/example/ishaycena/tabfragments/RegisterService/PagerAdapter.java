package com.example.ishaycena.tabfragments.RegisterService;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class PagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "PagerAdapter";

    public static final int NUM_OF_STEPS = 4;


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override

    public int getCount() {

        return NUM_OF_STEPS;
    }


    @Override

    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1, position == getCount() - 1);
    }


    @Override

    public CharSequence getPageTitle(int position) {

        return "Page " + position;

    }

}