package com.example.ishaycena.tabfragments.SignupService;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ishaycena.tabfragments.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class RegisterActivity extends AppCompatActivity implements StepperLayout.StepperListener {
    private static final String TAG = "RegisterActivity";

    StepperLayout mStepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mStepperLayout = findViewById(R.id.stepperLayout);
        RegisterActivity.MyStepperAdapter adapter = new MyStepperAdapter(getSupportFragmentManager(), this);

        mStepperLayout.setAdapter(adapter);
        mStepperLayout.setListener(this);
//        mStepperLayout.setBackButtonColor(Color.BLUE);
//        mStepperLayout.setNextButtonColor(Color.RED);

        mStepperLayout.setCurrentStepPosition(0);
    }

    @Override
    public void onCompleted(View completeButton) {
        Log.d(TAG, "onCompleted: called");
        Toast.makeText(this, "Completed sign up", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(VerificationError verificationError) {
        Log.d(TAG, "onError: called");
    }

    @Override
    public void onStepSelected(int newStepPosition) {
        Log.d(TAG, "onStepSelected: called");
    }

    @Override
    public void onReturn() {
        Log.d(TAG, "onReturn: called");
    }


    /*
    ========================= Stepper Adapter
     */
    public static class MyStepperAdapter extends AbstractFragmentStepAdapter {
        private static final String TAG = "MyStepperAdapter";
        public static final String CURRENT_STEP_POSITION_KEY = "StepPositionKey";
        //        private static String[] TITLES = {"Nothing", "Details", "Profile Pic", "Finish"};
        private static String[] TITLES = {"Email"};

        @Override
        public Step findStep(int position) {
            return super.findStep(position);
        }

        public MyStepperAdapter(FragmentManager fm, Context context) {
            super(fm, context);
        }

        @Override
        public Step createStep(int position) {
            Log.d(TAG, "createStep: position is: " + position);

            switch (position) {
                case 0:
                case 1:
                    final Register1Fragment fragment = new Register1Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(CURRENT_STEP_POSITION_KEY, position);

                    fragment.setArguments(bundle);
                    return fragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @NonNull
        @Override
        public StepViewModel getViewModel(@IntRange(from = 0) int position) {
            //Override this method to set Step title for the Tabs, not necessary for other stepper types
            return new StepViewModel.Builder(context)
                    .setTitle("") //can be a CharSequence instead
                    .create();
        }
    }
}
