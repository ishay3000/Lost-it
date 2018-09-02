package com.example.ishaycena.tabfragments.FoundService;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ishaycena.tabfragments.MainService.MainActivity;
import com.example.ishaycena.tabfragments.R;
import com.example.ishaycena.tabfragments.UserSingleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FoundActivity extends AppCompatActivity {
    private static final String TAG = "FoundActivity";

    private static final int RC_PHOTO_CHOOSE = 1;
    // widgets
    ImageView mFoundImageView;
    EditText mFoundDescriptionTextView;
    Button mBtnSubmit, mBtnCancel;

    // variables
    Uri mFoundUri, mUploadedFoundUri;
    boolean isPickedPicture = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found2);

        setWidgets();
        setListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_CHOOSE && resultCode == RESULT_OK) {
            Uri realUri = data.getData();
            if (realUri != null) {
                mFoundUri = realUri;
                mFoundImageView.setImageURI(realUri);
                isPickedPicture = true;
            }
        }
    }

    private void chooseProfilePhoto() {
        // gets all the file browsing / gallery apps from the phone and creates an intent chooser

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, RC_PHOTO_CHOOSE);
    }

    /**
     * verifies the details of the found
     *
     * @return if the details are valid
     */
    private boolean verifySubmit() {
        // if image was picked, and if description was added
        String description = mFoundDescriptionTextView.getText().toString();
        if (isPickedPicture && !description.equals("")) {
            // OK
            return true;
        } else {
            // ERR
            return false;
        }
    }

    private void uploadFoundImage() {
        // upload image
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateAndTime = sdf.format(new Date());
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage
                .getReference()
                .child("Found Pictures")
                .child(UserSingleton.getOurInstance().getmUsername())
                .child(currentDateAndTime);

        storageReference.putFile(mFoundUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // after uploading the image, we put the returned found image uri in 'mUploadedFoundUri'.
                mUploadedFoundUri = taskSnapshot.getDownloadUrl();

                UserSingleton user = UserSingleton.getOurInstance();
                // after uploading the image, upload the found to the database with the link
                Found found = new Found(user.getmUsername(),
                        mFoundDescriptionTextView.getText().toString(),
                        "TO BE ADDED",
                        mUploadedFoundUri.toString());
                found.setmUserName("Ishay");
                uploadFound(found);
            }
        });
    }

    private void uploadFound(Found found) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Founds NEW");

        try {
            databaseReference
                    .child(UserSingleton.getOurInstance().getmUsername())
                    .push()
                    .setValue(found)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                                Toast.makeText(FoundActivity.this, "Added found: " + found.toString(), Toast.LENGTH_SHORT).show();
                                new Intent(FoundActivity.this, MainActivity.class);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: ERROR: " + e.getMessage());
                        }
                    });
        } catch (Exception ex) {
            Log.d(TAG, "uploadFound: ERROR: " + ex.getMessage());
        }
    }

    private void setListeners() {
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // verify the submit
                if (verifySubmit()) {
                    // details OK
                    uploadFoundImage();

                } else {
                    // details ERR
                }
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mFoundImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProfilePhoto();
            }
        });
    }

    private void setWidgets() {
        mFoundImageView = findViewById(R.id.found_image);
        mFoundDescriptionTextView = findViewById(R.id.found_description);
        mBtnCancel = findViewById(R.id.found_button_cancel);
        mBtnSubmit = findViewById(R.id.found_button_submit);
    }
}