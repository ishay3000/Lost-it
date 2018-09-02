package com.example.ishaycena.tabfragments.SignupService;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ishaycena.tabfragments.MainService.MainActivity;
import com.example.ishaycena.tabfragments.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements StepperLayout.StepperListener {
    private static final String TAG = "RegisterActivity";

    public StepperLayout mStepperLayout;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = new User();

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

        registerUserToFirebase();
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

    /**
     * registers the user to firebase after completing the stepper layout registration
     */
    private void registerUserToFirebase() {
        uploadProfilePicture();
    }

    /**
     * uploads the profile image to the firebase storage
     */
    private void uploadProfilePicture() {
        // upload image
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference().child("Profile Pictures").child(user.emailAddress);
        storageReference.putFile(user.imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // after uploading the image, we put the returned image-uri in the user object
                user.imgURL = Objects.requireNonNull(taskSnapshot.getDownloadUrl()).toString();
                Toast.makeText(RegisterActivity.this, "Uploaded image: " + user.imgURL, Toast.LENGTH_SHORT).show();

                // after uploading the image, register the user
                uploadUser();
            }
        });
    }

    /**
     * uploads the user's details to the database
     */
    private void uploadUser() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

        // replacing dots (".") in the emails with commas (","), because firebase cannot contain dots
        databaseReference.child(user.emailAddress.replace('.', ',')).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i(TAG, "onComplete: task completed? " + task.isSuccessful());

                if (task.isSuccessful()) {
                    // user registered successfully
                    Log.i(TAG, "onComplete: <<Registered user>>");
                    Toast.makeText(RegisterActivity.this, String.format("Registered user %s", user.userName), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    public static class User {
        public String emailAddress;
        public String userName;
        public CustomLatLong latLng;
        @Exclude
        Uri imageUri;
        // will be added after uploading image to firebase
        public String imgURL;

        public User(String mEmailAddress, String mUsername, Uri mImageUri, CustomLatLong mLatLng) {
            this.emailAddress = mEmailAddress;
            this.userName = mUsername;
            this.imageUri = mImageUri;
            this.latLng = mLatLng;
        }

        public User() {

        }

        public String getImgURL() {
            return imgURL;
        }

        public void setImgURL(String imgURL) {
            this.imgURL = imgURL;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public CustomLatLong getLatLng() {
            return latLng;
        }

        public void setLatLng(CustomLatLong latLng) {
            this.latLng = latLng;
        }

        //        public Uri getImageUri() {
//            return imageUri;
//        }
//
//        public void setImageUri(Uri imageUri) {
//            this.imageUri = imageUri;
//        }


    }

}
