package com.example.ishaycena.tabfragments.SignupService;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class MyStepperAdapter extends AbstractFragmentStepAdapter {
    private static final String TAG = "MyStepperAdapter";
    public static final String CURRENT_STEP_POSITION_KEY = "StepPositionKey";
    private static String[] TITLES = {"Nothing", "Details", "Profile Pic", "Finish"};

    public MyStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        Log.d(TAG, "createStep: position is: " + position);
        //Step1Fragment step1 = new Step1Fragment();
        Register1Fragment step = null;

        switch (position) {
            case 0:
                step = new Register1Fragment();
                break;
            case 1:
                step = new Register1Fragment();
                break;
            case 2:
                step = new Register1Fragment();
                break;
            case 3:
                step = new Register1Fragment();
                break;
            default:
                break;
        }
        Bundle b = new Bundle();
        b.putInt(CURRENT_STEP_POSITION_KEY, position);
        if (step != null) {
            step.setArguments(b);
        }
        return step;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        return new StepViewModel.Builder(context)
                .setTitle(TITLES[position]) //can be a CharSequence instead
                .create();
    }
}