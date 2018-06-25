package com.example.ishaycena.tabfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // RFAB
    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;

    private SectionsPageAdapter sectionsPageAdapter;

    private ViewPager viewPager;
    public BottomNavigationView bottomNavigationView;
    com.getbase.floatingactionbutton.FloatingActionButton fabFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.viewpager);
        fabFound = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_found);
        initViewPager(viewPager);
        setListeners();

        TabLayout tabLayout = findViewById(R.id.tabs);
        // lets the tabs use the view pager with its adapter
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * sets listeners for button clicks
     */
    private void setListeners(){
        fabFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoundActivity.class));
                finish();
            }
        });
    }

    /**
     * initiates the view pager with its adapter, and adds fragments to it
     *
     * @param viewPager the view pager for the tabLayout
     */
    private void initViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new Tab1Fragment(), "Lost");
        adapter.addFragment(new Tab2Fragment(), "Found");
        adapter.addFragment(new Tab3Fragment(), "HighScores");

        viewPager.setAdapter(adapter);
    }
}
