package com.example.ishaycena.tabfragments.SignupService;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ishaycena.tabfragments.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class UsernamePhotoFragment extends Fragment implements BlockingStep {
    private static final String TAG = "UsernamePhotoFragment";
    private static final int RC_CAMERA_CAPTURE = 9001;
    private static final int RC_PHOTO_CHOOSE = 6969;

    public static UsernamePhotoFragment newInstance(int page, boolean isLast) {
        Bundle args = new Bundle();
        args.putInt("page", page);

        if (isLast) {
            args.putBoolean("isLast", true);
        }
        final UsernamePhotoFragment fragment = new UsernamePhotoFragment();
        fragment.setArguments(args);

        return fragment;
    }

    // widgets
    View view;
    ImageView imageView;
    EditText edtUsername;

    // vars
    String mCurrentPhotoPath;
    boolean isPickedPicture = false;
    Uri mImageUri;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.username_photo_fragment, container, false);
            imageView = view.findViewById(R.id.signup_imgprofile);
            edtUsername = view.findViewById(R.id.signup_edtusername);

            setListeners();
        }
        return view;
    }

    private void setListeners() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProfilePhoto();
            }
        });
    }

    /**
     * opens intent for choosing a profile picture form the gallery or any file system
     */
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

//    void tmpTakePicture(){
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, 6969);
//    }
//
//    private void takePicture() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//                Toast.makeText(getContext(), "Error taking the picture !", Toast.LENGTH_SHORT).show();
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(Objects.requireNonNull(getContext()),
//                        /*"com.example.android.fileprovider"*/BuildConfig.APPLICATION_ID+".fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, RC_CAMERA_CAPTURE);
//            }
//        }
//    }
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//        Log.d(TAG, "createImageFile: IMAGE PATH: " + image.getAbsolutePath());
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_CHOOSE && resultCode == RESULT_OK) {
            Uri realUri = data.getData();
            if (realUri != null) {
                mImageUri = realUri;
                imageView.setImageURI(realUri);
                isPickedPicture = true;
            }
        }

//        if (requestCode == RC_CAMERA_CAPTURE && resultCode == RESULT_OK) {
//            // capture successful
//            try {
//                final Uri imageUri = data.getData();
//                final InputStream imageStream;
//
//                imageStream = getActivity().getContentResolver().openInputStream(imageUri);
//
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                imageView.setImageBitmap(selectedImage);
//
//            } catch (Exception ex) {
//                Toast.makeText(getContext(), "Error saving and displaying image", Toast.LENGTH_SHORT).show();
//            }
//        }
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Override
    public VerificationError verifyStep() {
        if (isPickedPicture && !edtUsername.getText().toString().equals("")) {
            ((RegisterActivity) Objects.requireNonNull(getActivity())).user.imageUri = mImageUri;
            ((RegisterActivity) Objects.requireNonNull(getActivity())).user.userName = edtUsername.getText().toString();
            return null;
        } else {
            // if didn't picked picture & picked a username
            StringBuilder builder = new StringBuilder();
            if (!isPickedPicture) {
                builder.append("You must choose a picture first!\n");
            }
            if (edtUsername.getText().toString().equals("")) {
                builder.append("You must enter a username!\n");
            }
            return new VerificationError(builder.toString());
        }
    }


    @Override
    public void onSelected() {
        // Toast.makeText(getContext(), "Selected: " + getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        Log.d(TAG, "onError: error: " + error.getErrorMessage());

        Toast.makeText(getContext(), "Error: \n" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }
}
