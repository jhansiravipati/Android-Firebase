package com.example.userlocation;

public class Uploaddata {


    public Uploaddata(Double latitude, Double longitude, String mimageuri) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.mimageuri = mimageuri;
    }
    public Uploaddata(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

    }


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    private Double latitude;
    private Double longitude;


    public String getMimageuri() {
        return mimageuri;
    }

    public void setMimageuri(String mimageuri) {
        this.mimageuri = mimageuri;
    }

    private String mimageuri;

    public Uploaddata() {
    }
}

