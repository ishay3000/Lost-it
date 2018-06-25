package com.example.ishaycena.tabfragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoundActivity extends AppCompatActivity {

    CircleImageView mProfilePic, mBadgePic, mFoundPic;
    RelativeLayout mainLayout;
    Button btnSubmit, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);

        btnCancel = findViewById(R.id.btnCancelForm);
        btnSubmit = findViewById(R.id.btnSubmitFound);
        mainLayout = findViewById(R.id.form_main_layout);
        //mProfilePic = fin

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

                // Bitmap profile =
                StorageReference storageReference = FirebaseStorage.getInstance().getReference("founds");
                // storageReference.put

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

    /**
     * converts bitmap into a byte array
     *
     * @param bmp bitmap image
     * @return byte array from the bitmap
     */
    private static byte[] convertBitmapToByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmp.recycle();

        return byteArray;
    }
}
