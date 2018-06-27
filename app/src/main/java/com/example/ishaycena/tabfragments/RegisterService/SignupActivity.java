package com.example.ishaycena.tabfragments.RegisterService;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.badoualy.stepperindicator.StepperIndicator;
import com.example.ishaycena.tabfragments.R;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    StepperIndicator indicator;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        pager = findViewById(R.id.signup_viewpager);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        indicator = findViewById(R.id.stepper_indicator);

        // We keep last page for a "finishing" page
        indicator.setViewPager(pager, true);
        indicator.addOnStepClickListener(new StepperIndicator.OnStepClickListener() {
            @Override
            public void onStepClicked(int step) {
                pager.setCurrentItem(step, true);
            }
        });
    }
}
