package com.example.ishaycena.tabfragments.FoundService;

import java.io.Serializable;

/**
 * Represents a found in the database
 */
public class Found implements Serializable {
    private String mUserName, mFoundDescription, mProfileImageUrl, mFoundImageUrl;

    /**
     * instance of a Found. This will be loaded from firebase.
     *
     * @param mUsername         found's user name
     * @param mFoundDescription user-typed description of the found
     * @param mProfileImageUrl  user profile image url (get from singleton)
     * @param mFoundImageUrl    found image url
     */
    public Found(String mUsername, String mFoundDescription, String mProfileImageUrl, String mFoundImageUrl) {
        this.mUserName = mUserName;
        this.mFoundDescription = mFoundDescription;
        this.mProfileImageUrl = mProfileImageUrl;
        this.mFoundImageUrl = mFoundImageUrl;
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

    //#endregion
}
