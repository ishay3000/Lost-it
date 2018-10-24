package com.example.ishaycena.tabfragments.MapService;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.ishaycena.tabfragments.R;
import com.example.ishaycena.tabfragments.SignupService.CustomLatLong;

import java.util.Objects;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        FrameLayout layout = findViewById(R.id.location_framelayout);
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();

        Intent intent = getIntent();
        CustomLatLong latLong = (CustomLatLong) Objects.requireNonNull(intent.getExtras()).get("LOCATION");
        LocationFragment fragment = LocationFragment.newInstance(latLong);
        transaction.replace(R.id.location_framelayout, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}
