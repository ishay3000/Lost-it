package com.example.ishaycena.tabfragments.MainService;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ishaycena.tabfragments.ProfileActivity;
import com.example.ishaycena.tabfragments.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // RFAB
    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;

    private SectionsPageAdapter sectionsPageAdapter;

    // vars
    private BottomNavigationView.OnNavigationItemSelectedListener mItemSelectedListener;

    // widgets
    private ViewPager viewPager;
    public BottomNavigationView bottomNavigationView;
    com.getbase.floatingactionbutton.FloatingActionButton fabFound;
    FloatingActionButton fabLost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.viewpager);
        fabFound = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_found);
        fabLost = findViewById(R.id.fab_lost);
//        initViewPager(viewPager);
//        setListeners();
//
//        TabLayout tabLayout = findViewById(R.id.tabs);
//        // lets the tabs use the view pager with its adapter
//        tabLayout.setupWithViewPager(viewPager);


        new Worker(this).execute();
        mItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.menu_profile:
//                        viewPager.setCurrentItem(3);
                        intent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.menu_categories:
                        return true;

                    case R.id.menu_home:
                        return true;
                }
                return false;
            }
        };
        bottomNavigationView.setOnNavigationItemSelectedListener(mItemSelectedListener);
    }

    private static class Worker extends AsyncTask<Void, Void, Void> {
        WeakReference<MainActivity> activityWeakReference;

        public Worker(MainActivity activity) {
            super();

            this.activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MainActivity activity = activityWeakReference.get();
            activity.initViewPager(activity.viewPager);
            activity.setListeners();


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(activityWeakReference.get().getApplicationContext(), "Finished loading activity", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * sets listeners for button clicks
     */
    private void setListeners(){
        fabFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, com.example.ishaycena.tabfragments.FoundService.FoundActivity.class));
                finish();
            }
        });

        fabLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                finish();*/
                Toast.makeText(MainActivity.this, "Adding a lost isn't supported yet", Toast.LENGTH_SHORT).show();
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
        // TODO add fragments for bottom nav view
//        adapter.addFragment(new NavProfileFragment(), "Profile");

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        // lets the tabs use the view pager with its adapter
        tabLayout.setupWithViewPager(viewPager);
    }
}
