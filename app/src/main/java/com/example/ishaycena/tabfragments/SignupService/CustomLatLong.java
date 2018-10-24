package com.example.ishaycena.tabfragments.SignupService;

import java.io.Serializable;

public class CustomLatLong implements Serializable {
    public double lat, lng;

    public CustomLatLong(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public CustomLatLong() {
    }
}
