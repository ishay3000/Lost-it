package com.example.ishaycena.tabfragments.Utilities;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Found implements Serializable {
    private Bitmap imgProfileUrl, imgBadgeUrl, imgItem, imgMap;
    private String personName, description;
    public String imgUrl, badgeUrl, itemUrl;

    public class miniFound {
        public String personName, description;
        public String imgUrl, badgeUrl, itemUrl;

        public miniFound(String personName, String description, String imgUrl, String badgeUrl, String itemUrl) {
            this.personName = personName;
            this.description = description;
            this.imgUrl = imgUrl;
            this.badgeUrl = badgeUrl;
            this.itemUrl = itemUrl;
        }
    }

    public Found(Bitmap imgProfileUrl, Bitmap imgBadgeUrl, Bitmap imgItem, Bitmap imgMap, String personName, String description) {
        this.imgProfileUrl = imgProfileUrl;
        this.imgBadgeUrl = imgBadgeUrl;
        this.imgItem = imgItem;
        this.imgMap = imgMap;
        this.personName = personName;
        this.description = description;
    }

    public Found(String personName, String description, String imgUrl, String badgeUrl, String itemUrl) {
        this.personName = personName;
        this.description = description;
        this.imgUrl = imgUrl;
        this.badgeUrl = badgeUrl;
        this.itemUrl = itemUrl;
    }

    @Override
    public String toString() {
        return "Found{" +
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
}
