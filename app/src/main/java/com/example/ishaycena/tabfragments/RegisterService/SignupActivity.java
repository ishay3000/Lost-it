package com.example.ishaycena.tabfragments.RegisterService;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.badoualy.stepperindicator.StepperIndicator;
import com.example.ishaycena.tabfragments.MainActivity;
import com.example.ishaycena.tabfragments.R;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    StepperIndicator indicator;
    ViewPager pager;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        layout = findViewById(R.id.signup_mainLayout);
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

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: called");
        Snackbar.make(layout, "Go back?", Snackbar.LENGTH_LONG)
                .setAction("Home", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    }
                })
                .show();
    }
}
