package com.example.ishaycena.tabfragments.Utilities;

import com.example.ishaycena.tabfragments.SignupService.CustomLatLong;

public abstract class AbsLostFound {
    protected String mUserName, mItemDescription, mProfileImageUrl, mItemImageUrl;
    protected CustomLatLong mLatLong;

    /**
     * instance of a AdapterFound. This will be loaded from firebase.
     *
     * @param mUsername        item's user name
     * @param itemDescription  user-typed description of the item
     * @param mProfileImageUrl user profile image url (get from singleton)
     * @param itemImageUrl     item image url
     */
    public AbsLostFound(String mUsername, String itemDescription, String mProfileImageUrl, String itemImageUrl, CustomLatLong latLong) {
        this.mUserName = mUserName;
        this.mItemDescription = itemDescription;
        this.mProfileImageUrl = mProfileImageUrl;
        this.mItemImageUrl = itemImageUrl;
        mLatLong = latLong;
    }

    public AbsLostFound() {
    }

    @Override
    public String toString() {
        return "AdapterFound{" +
                "mUserName='" + mUserName + '\'' +
                ", mItemDescription='" + mItemDescription + '\'' +
                ", mProfileImageUrl='" + mProfileImageUrl + '\'' +
                ", mItemImageUrl='" + mItemImageUrl + '\'' +
                '}';
    }

    /**
     * instance of a AdapterFound. This will be loaded from firebase.
     *
     * @param mProfileImageUri  profile image uri
     * @param mFoundImageUri    found image uri
     * @param mUsername         the found's username
     * @param mFoundDescription the user's description of the found
     *//*
    public AdapterFound(Uri mProfileImageUri, Uri mFoundImageUri, String mUsername, String mItemDescription) {
        this.mProfileImageUri = mProfileImageUri;
        this.mFoundImageUri = mFoundImageUri;
        this.mUserName = mUserName;
        this.mItemDescription = mItemDescription;
    }*/


    //#region properties
    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmItemDescription() {
        return mItemDescription;
    }

    public void setmItemDescription(String mItemDescription) {
        this.mItemDescription = mItemDescription;
    }

    public String getmProfileImageUrl() {
        return mProfileImageUrl;
    }

    public void setmProfileImageUrl(String mProfileImageUrl) {
        this.mProfileImageUrl = mProfileImageUrl;
    }

    public String getmItemImageUrl() {
        return mItemImageUrl;
    }

    public void setmItemImageUrl(String mItemImageUrl) {
        this.mItemImageUrl = mItemImageUrl;
    }

    public CustomLatLong getmLatLong() {
        return mLatLong;
    }

    public void setmLatLong(CustomLatLong mLatLong) {
        this.mLatLong = mLatLong;
    }

    //#endregion
}
