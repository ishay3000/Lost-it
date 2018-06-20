package com.example.ishaycena.tabfragments;

import android.content.Intent;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ishaycena.tabfragments.Utilities.Found;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;

import butterknife.BindView;

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
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setListeners(){
        fabFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoundActivity.class));
                finish();
            }
        });
    }

    private void initViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new Tab1Fragment(), "Lost");
        adapter.addFragment(new Tab2Fragment(), "Found");
        adapter.addFragment(new Tab3Fragment(), "HighScores");

        viewPager.setAdapter(adapter);
    }
}
