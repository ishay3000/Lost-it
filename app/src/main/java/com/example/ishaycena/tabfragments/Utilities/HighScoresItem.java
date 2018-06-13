package com.example.ishaycena.tabfragments.Utilities;

import android.graphics.Bitmap;

public class HighScoresItem {
    private Bitmap imgProfileUrl, imgBadgeUrl;
    private String personName;
    private int foundsCount, lostsCount;

    public HighScoresItem(Bitmap imgProfileUrl, Bitmap imgBadgeUrl, String personName, int foundsCount, int lostsCount) {
        this.imgProfileUrl = imgProfileUrl;
        this.imgBadgeUrl = imgBadgeUrl;
        this.personName = personName;
        this.foundsCount = foundsCount;
        this.lostsCount = lostsCount;
    }

    public Bitmap getImgProfileUrl() {
        return imgProfileUrl;
    }

    public void setImgProfileUrl(Bitmap imgProfileUrl) {
        this.imgProfileUrl = imgProfileUrl;
    }

    public Bitmap getImgBadgeUrl() {
        return imgBadgeUrl;
    }

    public void setImgBadgeUrl(Bitmap imgBadgeUrl) {
        this.imgBadgeUrl = imgBadgeUrl;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getFoundsCount() {
        return foundsCount;
    }

    public void setFoundsCount(int foundsCount) {
        this.foundsCount = foundsCount;
    }

    public int getLostsCount() {
        return lostsCount;
    }

    public void setLostsCount(int lostsCount) {
        this.lostsCount = lostsCount;
    }
}
