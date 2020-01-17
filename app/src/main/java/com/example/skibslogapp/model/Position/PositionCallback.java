package com.example.skibslogapp.model.Position;

import android.location.Location;

import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationResult;

/*
    Class indeholder den callback funktion som skal fusedLocationProviderClient anvender
     */
public class PositionCallback extends com.google.android.gms.location.LocationCallback {

    private static Position measuredKoordinat;

    @Override
    public void onLocationResult(LocationResult locationResult) {
        Location l = locationResult.getLastLocation();
        System.out.println("New location registered -----");
        System.out.println("Altitude: " +l.getAltitude());
        System.out.println("Latitude: " +l.getLatitude());
        System.out.println("Longitude: " +l.getLongitude());
        measuredKoordinat = new Position(l.getLatitude(),l.getLongitude());
    }


    //Checking if the the GPS is alvalable
    @Override
    public void onLocationAvailability(LocationAvailability locationAvailability) {
        super.onLocationAvailability(locationAvailability);
    }


    public Position getKoordinates(){
        return measuredKoordinat;
    }
}
