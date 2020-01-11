package com.example.skibslogapp.model.Koordinat;

import androidx.annotation.NonNull;
import java.util.Locale;

/**
 * Represents a position as a set of latitude and longitude coordinates.
 */
public class Position {
    private double latitude; // Breddegrad
    private double longitude; // Længdegrad

    Position(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }



    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.US,"Position{Lat: %.4f, Long: %.4f}", latitude, longitude);
    }
}
