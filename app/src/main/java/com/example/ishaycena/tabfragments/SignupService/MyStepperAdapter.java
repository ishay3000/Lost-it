package com.example.ishaycena.tabfragments.SignupService;

import android.content.Context;
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
    private static String[] TITLES = {"Email", "Profile", "Home address", "Complete sign up"};

    public MyStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        Log.d(TAG, "createStep: position is: " + position);
        //Step1Fragment step1 = new Step1Fragment();
        Step step = null;

        switch (position) {
            case 0:
                step = EmailStepFragment.newInstance(position, false); //new EmailStepFragment();
                break;
            case 1:
                step = UsernamePhotoFragment.newInstance(position, false);
                break;
            case 2:
                step = HomeAddressStepFragment.newInstance(position, false);
                break;
//            case 3:
//                // TODO ADD LAST PAGE LOGIC
//                break;
            default:
                step = EmailStepFragment.newInstance(position, true);
                break;
        }
//        Bundle b = new Bundle();
//        b.putInt(CURRENT_STEP_POSITION_KEY, position);
//        if (step != null) {
//            step.setArguments(b);
//        }

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