package com.example.ishaycena.tabfragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SectionsPageAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> lstFragments;
    private final ArrayList<String> lstFragmentTitles;

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
        lstFragments = new ArrayList<Fragment>();
        lstFragmentTitles = new ArrayList<String>();
    }

    public void addFragment(Fragment fragment, String fragmentTitle){
        lstFragments.add(fragment);
        lstFragmentTitles.add(fragmentTitle);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return lstFragmentTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return lstFragments.get(position);
    }

    @Override
    public int getCount() {
        return lstFragments.size();
    }
}
