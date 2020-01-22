package com.example.skibslogapp.model.positionregistration;

import android.location.Location;

import com.example.skibslogapp.model.Position;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationResult;

/*
    Class indeholder den callback funktion som skal fusedLocationProviderClient anvender
     */
public class PositionCallback extends com.google.android.gms.location.LocationCallback {

    private static Position measuredPosition;

    @Override
    public void onLocationResult(LocationResult locationResult) {
        Location l = locationResult.getLastLocation();
        measuredPosition = new Position(l.getLatitude(), l.getLongitude());
    }

    //Checking if the the GPS is alvalable
    @Override
    public void onLocationAvailability(LocationAvailability locationAvailability) {
        super.onLocationAvailability(locationAvailability);
    }

    Position getKoordinates() {
        return measuredPosition;
    }
}
