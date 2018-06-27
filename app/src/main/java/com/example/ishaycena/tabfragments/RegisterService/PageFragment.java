package com.example.ishaycena.tabfragments.RegisterService;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ishaycena.tabfragments.R;

import java.util.Objects;

public class PageFragment extends Fragment {

    public static PageFragment newInstance(int page, boolean isLast) {

        Bundle args = new Bundle();

        args.putInt("page", page);

        if (isLast) {
            args.putBoolean("isLast", true);
        }

        final PageFragment fragment = new PageFragment();

        fragment.setArguments(args);

        return fragment;

    }


    @Nullable

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.layout_register1_fragment, container, false);
        Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        EditText edtUsername = view.findViewById(R.id.signup_edtPass);
        showKeyboard(edtUsername);

        Button btn = view.findViewById(R.id.signup_btnProceed);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int page = getArguments().getInt("page", -999);

                Toast.makeText(getActivity(), "page: " + page, Toast.LENGTH_SHORT).show();
                if (page != -999) {
                    SignupActivity activity = (SignupActivity) getActivity();
                    if (activity != null) {
                        activity.pager.setCurrentItem(page, true);
                    }
                }
            }
        });

        return view;

    }

    private void showKeyboard(EditText editText) {
        SignupActivity activity = (SignupActivity) getActivity();
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                editText.requestFocus();
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        }

    }


    @Override

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            final int page = getArguments().getInt("page", 0);
        }

        if (getArguments().containsKey("isLast")) {
            // TODO add "completion page" logic here
        }
    }

}