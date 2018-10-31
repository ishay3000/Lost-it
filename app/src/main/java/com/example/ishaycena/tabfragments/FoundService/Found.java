package com.example.ishaycena.tabfragments.FoundService;

import com.example.ishaycena.tabfragments.SignupService.CustomLatLong;
import com.example.ishaycena.tabfragments.Utilities.AbsLostFound;

import java.io.Serializable;

/**
 * Represents a found in the database
 */
public class Found extends AbsLostFound implements Serializable {
    public Found(String mUsername, String itemDescription, String mProfileImageUrl, String itemImageUrl, CustomLatLong latLong) {
        super(mUsername, itemDescription, mProfileImageUrl, itemImageUrl, latLong);
    }

    public Found() {
    }

    //    private String mUserName, mFoundDescription, mProfileImageUrl, mFoundImageUrl;
//    private CustomLatLong mLatLong;
//
//    /**
//     * instance of a AdapterFound. This will be loaded from firebase.
//     *
//     * @param mUsername         found's user name
//     * @param mFoundDescription user-typed description of the found
//     * @param mProfileImageUrl  user profile image url (get from singleton)
//     * @param mFoundImageUrl    found image url
//     */
//    public Found(String mUsername, String mFoundDescription, String mProfileImageUrl, String mFoundImageUrl, CustomLatLong latLong) {
//        this.mUserName = mUserName;
//        this.mFoundDescription = mFoundDescription;
//        this.mProfileImageUrl = mProfileImageUrl;
//        this.mFoundImageUrl = mFoundImageUrl;
//        mLatLong = latLong;
//
//    }
//
//    public Found() {
//    }
//
//    @Override
//    public String toString() {
//        return "AdapterFound{" +
//                "mUserName='" + mUserName + '\'' +
//                ", mItemDescription='" + mFoundDescription + '\'' +
//                ", mProfileImageUrl='" + mProfileImageUrl + '\'' +
//                ", mItemImageUrl='" + mFoundImageUrl + '\'' +
//                '}';
//    }
//
//    /**
//     * instance of a AdapterFound. This will be loaded from firebase.
//     *
//     * @param mProfileImageUri  profile image uri
//     * @param mFoundImageUri    found image uri
//     * @param mUsername         the found's username
//     * @param mFoundDescription the user's description of the found
//     *//*
//    public AdapterFound(Uri mProfileImageUri, Uri mFoundImageUri, String mUsername, String mItemDescription) {
//        this.mProfileImageUri = mProfileImageUri;
//        this.mFoundImageUri = mFoundImageUri;
//        this.mUserName = mUserName;
//        this.mItemDescription = mItemDescription;
//    }*/
//
//
//    //#region properties
//    public String getmUserName() {
//        return mUserName;
//    }
//
//    public void setmUserName(String mUserName) {
//        this.mUserName = mUserName;
//    }
//
//    public String getmItemDescription() {
//        return mFoundDescription;
//    }
//
//    public void setmItemDescription(String mItemDescription) {
//        this.mFoundDescription = mItemDescription;
//    }
//
//    public String getmProfileImageUrl() {
//        return mProfileImageUrl;
//    }
//
//    public void setmProfileImageUrl(String mProfileImageUrl) {
//        this.mProfileImageUrl = mProfileImageUrl;
//    }
//
//    public String getmItemImageUrl() {
//        return mFoundImageUrl;
//    }
//
//    public void setmItemImageUrl(String mItemImageUrl) {
//        this.mFoundImageUrl = mItemImageUrl;
//    }
//
//    public CustomLatLong getmLatLong() {
//        return mLatLong;
//    }
//
//    public void setmLatLong(CustomLatLong mLatLong) {
//        this.mLatLong = mLatLong;
//    }
//
//    //#endregion
}
