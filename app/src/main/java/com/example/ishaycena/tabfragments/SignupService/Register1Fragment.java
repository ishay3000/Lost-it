package com.example.ishaycena.tabfragments.SignupService;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ishaycena.tabfragments.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class Register1Fragment extends Fragment implements BlockingStep {
    private static final String TAG = "Register1Fragment";

    View view;

    EditText editText1, editText2;

    int mPosition = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
//            Toast.makeText(getContext(), "This should only appear once", Toast.LENGTH_SHORT).show();

            view = inflater.inflate(R.layout.layout_register1_fragment, container, false);

            editText1 = view.findViewById(R.id.signup_edtUser);
            editText2 = view.findViewById(R.id.signup_edtPass);
            int position = 0;
            if (getArguments() != null) {
                position = getArguments().getInt(MyStepperAdapter.CURRENT_STEP_POSITION_KEY);
                mPosition = position;
            }
            Toast.makeText(getContext(), "position: " + position, Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    @UiThread
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        callback.goToNextStep();
    }

    @Override
    @UiThread
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        Toast.makeText(getContext(), "Completed!", Toast.LENGTH_SHORT).show();
        callback.complete();
    }

    @Override
    @UiThread
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Nullable
    @Override
    @UiThread
    public VerificationError verifyStep() {
        if (editText1.getText().toString().equals("")) {
            return new VerificationError("Error!");
        }
        return null;
    }

    @Override
    @UiThread
    public void onSelected() {
//        Toast.makeText(getContext(), "Selected " + this.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    @UiThread
    public void onError(@NonNull VerificationError error) {
        String msg = error.getErrorMessage();

        Toast.makeText(getContext(), "Error: " + msg, Toast.LENGTH_SHORT).show();
    }
}
