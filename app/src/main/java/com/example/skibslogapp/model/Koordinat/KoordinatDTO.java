package com.example.skibslogapp.model.Koordinat;

import androidx.annotation.NonNull;

public class KoordinatDTO {
    private double latitude;
    private double longitude;
    private boolean successfulCoordinate;

    public KoordinatDTO(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void printKoordinates(){
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);
    }

    @NonNull
    @Override
    public String toString() {
        String koordinat = "Latitude: " + latitude + ", Longitude" + longitude;
        return koordinat;
    }
}
