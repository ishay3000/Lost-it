package com.example.ishaycena.tabfragments.FoundService;

import com.example.ishaycena.tabfragments.SignupService.CustomLatLong;
import com.example.ishaycena.tabfragments.Utilities.AbsItem;

import java.io.Serializable;

/**
 * Represents a found in the database
 */
public class Found extends AbsItem implements Serializable {
    private String mUserName, mFoundDescription, mProfileImageUrl, mFoundImageUrl;
    private CustomLatLong mLatLong;

    /**
     * instance of a Found. This will be loaded from firebase.
     *
     * @param mUsername         found's user name
     * @param mFoundDescription user-typed description of the found
     * @param mProfileImageUrl  user profile image url (get from singleton)
     * @param mFoundImageUrl    found image url
     */
    public Found(String mUsername, String mFoundDescription, String mProfileImageUrl, String mFoundImageUrl, CustomLatLong latLong) {
        this.mUserName = mUserName;
        this.mFoundDescription = mFoundDescription;
        this.mProfileImageUrl = mProfileImageUrl;
        this.mFoundImageUrl = mFoundImageUrl;
        mLatLong = latLong;
    }

    public Found() {
    }

    @Override
    public String toString() {
        return "Found{" +
                "mUserName='" + mUserName + '\'' +
                ", mFoundDescription='" + mFoundDescription + '\'' +
                ", mProfileImageUrl='" + mProfileImageUrl + '\'' +
                ", mFoundImageUrl='" + mFoundImageUrl + '\'' +
                '}';
    }

    /**
     * instance of a Found. This will be loaded from firebase.
     *
     * @param mProfileImageUri  profile image uri
     * @param mFoundImageUri    found image uri
     * @param mUsername         the found's username
     * @param mFoundDescription the user's description of the found
     *//*
    public Found(Uri mProfileImageUri, Uri mFoundImageUri, String mUsername, String mFoundDescription) {
        this.mProfileImageUri = mProfileImageUri;
        this.mFoundImageUri = mFoundImageUri;
        this.mUserName = mUserName;
        this.mFoundDescription = mFoundDescription;
    }*/


    //#region properties
    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmFoundDescription() {
        return mFoundDescription;
    }

    public void setmFoundDescription(String mFoundDescription) {
        this.mFoundDescription = mFoundDescription;
    }

    public String getmProfileImageUrl() {
        return mProfileImageUrl;
    }

    public void setmProfileImageUrl(String mProfileImageUrl) {
        this.mProfileImageUrl = mProfileImageUrl;
    }

    public String getmFoundImageUrl() {
        return mFoundImageUrl;
    }

    public void setmFoundImageUrl(String mFoundImageUrl) {
        this.mFoundImageUrl = mFoundImageUrl;
    }

    public CustomLatLong getmLatLong() {
        return mLatLong;
    }

    public void setmLatLong(CustomLatLong mLatLong) {
        this.mLatLong = mLatLong;
    }

    //#endregion
}
