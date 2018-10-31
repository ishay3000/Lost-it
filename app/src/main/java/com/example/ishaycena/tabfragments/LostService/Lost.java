package com.example.ishaycena.tabfragments.LostService;

import com.example.ishaycena.tabfragments.SignupService.CustomLatLong;
import com.example.ishaycena.tabfragments.Utilities.AbsLostFound;

public class Lost extends AbsLostFound {
    public Lost(String mUsername, String itemDescription, String mProfileImageUrl, String itemImageUrl, CustomLatLong latLong) {
        super(mUsername, itemDescription, mProfileImageUrl, itemImageUrl, latLong);
    }

    public Lost() {
    }
}
