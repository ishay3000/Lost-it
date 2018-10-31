package com.example.ishaycena.tabfragments.Utilities;

import android.graphics.Bitmap;

import com.example.ishaycena.tabfragments.SignupService.CustomLatLong;

import java.io.Serializable;

public class AdapterFound extends AbsRecyclerViewAdapterItem implements Serializable {
    private Bitmap imgProfileUrl, imgBadgeUrl, imgItem, imgMap;
    private String personName, description;
    private CustomLatLong customLatLong;

    public AdapterFound(Bitmap imgProfileUrl, Bitmap imgBadgeUrl, Bitmap imgItem, Bitmap imgMap, String personName, String description) {
        this.imgProfileUrl = imgProfileUrl;
        this.imgBadgeUrl = imgBadgeUrl;
        this.imgItem = imgItem;
        this.imgMap = imgMap;
        this.personName = personName;
        this.description = description;
    }

    public AdapterFound() {
    }

    @Override
    public String toString() {
        return "AdapterFound{" +
                "personName='" + personName + '\'' +
                ", description='" + description + '\'' +
                '}';
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

    public Bitmap getImgItem() {
        return imgItem;
    }

    public void setImgItem(Bitmap imgItem) {
        this.imgItem = imgItem;
    }

    public Bitmap getImgMap() {
        return imgMap;
    }

    public void setImgMap(Bitmap imgMap) {
        this.imgMap = imgMap;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CustomLatLong getCustomLatLong() {
        return customLatLong;
    }

    public void setCustomLatLong(CustomLatLong customLatLong) {
        this.customLatLong = customLatLong;
    }
}
