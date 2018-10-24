package com.example.ishaycena.tabfragments.NavigationViewService;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ishaycena.tabfragments.R;

public class NavProfileFragment extends Fragment {
    private static final String TAG = "NavProfileFragment";

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.nav_profile, container, false);
        }
        return view;
    }
}
