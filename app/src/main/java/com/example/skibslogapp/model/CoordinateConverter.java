package com.example.skibslogapp.model;

public class CoordinateConverter {

    /**
     * Convert longitude from format 29.3994  to  29°23.96'N
     */
    public static String toLongitudeString(double coordinate){
        int degrees = (int) coordinate;
        double minutes = (coordinate-degrees)*60;
        String direction = minutes < 0 ? "S" : "N";
        return String.format("%d\u00B0%.2f'%s", degrees, minutes, direction);
    }

    /**
     * Convert latitude from format 29.3994  to  29°23.96'N
     */
    public static String toLatitudeString(double coordinate){
        int degrees = (int) coordinate;
        double minutes = (coordinate-degrees)*60;
        String direction = minutes < 0 ? "W" : "E";
        return String.format("%d\u00B0%.2f'%s", degrees, minutes, direction);
    }

}
