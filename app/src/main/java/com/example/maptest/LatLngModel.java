package com.example.maptest;

public class LatLngModel {

    double lat;
    double lng;
    String title;
    String description;

    public LatLngModel(double lat, double lng, String title, String description) {
        this.lat = lat;
        this.lng = lng;
        this.title = title;
        this.description = description;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
