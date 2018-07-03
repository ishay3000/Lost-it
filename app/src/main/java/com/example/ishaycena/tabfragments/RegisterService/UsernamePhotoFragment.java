package com.example.ishaycena.tabfragments.RegisterService;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ishaycena.tabfragments.BuildConfig;
import com.example.ishaycena.tabfragments.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class UsernamePhotoFragment extends Fragment {
    private static final String TAG = "UsernamePhotoFragment";
    private static final int RC_CAMERA_CAPTURE = 9001;

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
                tmpTakePicture();
            }
        });
    }

    void tmpTakePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 6969);
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getContext(), "Error taking the picture !", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(Objects.requireNonNull(getContext()),
                        /*"com.example.android.fileprovider"*/BuildConfig.APPLICATION_ID + ".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, RC_CAMERA_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        Log.d(TAG, "createImageFile: IMAGE PATH: " + image.getAbsolutePath());

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 6969 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

            imageView.setImageBitmap(bitmap);

        }

        if (requestCode == RC_CAMERA_CAPTURE && resultCode == RESULT_OK) {
            // capture successful
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream;

                imageStream = getActivity().getContentResolver().openInputStream(imageUri);

                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);

            } catch (Exception ex) {
                Toast.makeText(getContext(), "Error saving and displaying image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
