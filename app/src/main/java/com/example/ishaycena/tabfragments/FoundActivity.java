package com.example.ishaycena.tabfragments;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;

public class FoundActivity extends AppCompatActivity {

    RelativeLayout mainLayout;
    Button btnSubmit, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);

        btnCancel = findViewById(R.id.btnCancelForm);
        btnSubmit = findViewById(R.id.btnSubmitFound);
        mainLayout = findViewById(R.id.form_main_layout);

        setListeners();
    }

    private void setListeners(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO implementation of adding to firebase, but beforehand - add a SNACK-BAR
                Snackbar snackbar = Snackbar.make(mainLayout, "Adding is not yet added ;/ btw Dor sucks", Snackbar.LENGTH_LONG)
                        .setAction("Go to Home Activity", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(FoundActivity.this, MainActivity.class));
                                finish();
                            }
                        });
                snackbar.show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoundActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
