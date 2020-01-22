package com.example.skibslogapp.model;

import androidx.annotation.NonNull;

import java.util.Locale;

/**
 * Represents a position as a set of breddegrad and laengdegrad coordinates.
 */
public class Position {
    private double breddegrad; // Latitude
    private double laengdegrad; // Longitude

    public Position(double breddegrad, double laengdegrad) {
        this.breddegrad = breddegrad;
        this.laengdegrad = laengdegrad;
    }

    public double getBreddegrad() {
        return breddegrad;
    }

    public double getLaengdegrad() {
        return laengdegrad;
    }

    /**
     * Convert latitude from format 29.3994  to  29°23.96'N
     */
    public String getBreddegradString() {
        int degrees = (int) breddegrad;
        double minutes = (breddegrad - degrees) * 60;
        String direction = minutes < 0 ? "S" : "N";
        return String.format(Locale.UK, "%d\u00B0%.2f'%s", degrees < 0 ? degrees * -1 : degrees, minutes < 0 ? minutes * -1 : minutes, direction);
    }

    /**
     * Convert longitude from format 29.3994  to  29°23.96'N
     */
    public String getLaengdegradString() {
        int degrees = (int) laengdegrad;
        double minutes = (laengdegrad - degrees) * 60;
        String direction = minutes < 0 ? "W" : "E";
        return String.format(Locale.UK, "%d\u00B0%.2f'%s", degrees < 0 ? degrees * -1 : degrees, minutes < 0 ? minutes * -1 : minutes, direction);
    }

    // Test string conversion with https://www.latlong.net/lat-long-dms.html

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.US, "Position{B.grad: %s, L.grad: %s}", getBreddegradString(), getLaengdegradString());
    }
}
