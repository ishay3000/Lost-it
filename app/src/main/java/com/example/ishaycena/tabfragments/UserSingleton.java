package com.example.ishaycena.tabfragments;


import android.net.Uri;

import com.example.ishaycena.tabfragments.SignupService.CustomLatLong;
import com.example.ishaycena.tabfragments.SignupService.RegisterActivity;

public class UserSingleton {
    private static final UserSingleton ourInstance = new UserSingleton();

    static UserSingleton getInstance() {
        return ourInstance;
    }

    private UserSingleton() {
        mUsername = "Ishay";
    }

    private String mUsername, mUserEmail;
    private Uri mUserProfileUri;
    private CustomLatLong mLatLong;
    private static RegisterActivity.User USER_INSTANCE = null;

    public static void setUserInstance(RegisterActivity.User user) {
        USER_INSTANCE = user;
    }

    //#region properties
    public static UserSingleton getOurInstance() {
        return ourInstance;
    }

    public String getmUsername() {
        return USER_INSTANCE.userName;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmUserEmail() {
        return USER_INSTANCE.emailAddress;
    }

    public void setmUserEmail(String mUserEmail) {
        this.mUserEmail = mUserEmail;
    }

    public Uri getmUserProfileUri() {
        return mUserProfileUri;
    }

    public String getProfileImageStringUrl() {
        return USER_INSTANCE.imgURL;
    }

    public void setmUserProfileUri(Uri mUserProfileUri) {
        this.mUserProfileUri = mUserProfileUri;
    }

    public CustomLatLong getmLatLong() {
        return USER_INSTANCE.latLng;
    }

    public void setmLatLong(CustomLatLong mLatLong) {
        this.mLatLong = mLatLong;
    }

//#endregion
}
