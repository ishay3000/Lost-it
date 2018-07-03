package com.example.ishaycena.tabfragments.SignupService;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ishaycena.tabfragments.R;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class RegisterActivity extends AppCompatActivity implements StepperLayout.StepperListener {
    private static final String TAG = "RegisterActivity";

    public StepperLayout mStepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mStepperLayout = findViewById(R.id.stepperLayout);
        com.example.ishaycena.tabfragments.SignupService.MyStepperAdapter adapter = new com.example.ishaycena.tabfragments.SignupService.MyStepperAdapter(getSupportFragmentManager(), this);
//        mStepperLayout.setOffscreenPageLimit(0);

        mStepperLayout.setAdapter(adapter);
        mStepperLayout.setListener(this);
//        mStepperLayout.setBackButtonColor(Color.BLUE);
//        mStepperLayout.setNextButtonColor(Color.RED);

        mStepperLayout.setCurrentStepPosition(0);
    }

    /**
     * <p>
     * attempts to go to the next step.
     * </p>
     * intended usage from step fragments only !
     */
    public void goToNextStep() {
        // VERIFIES whether to go to the next step
        mStepperLayout.proceed();
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

}
