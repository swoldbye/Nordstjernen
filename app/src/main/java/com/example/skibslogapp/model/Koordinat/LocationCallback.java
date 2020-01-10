package com.example.skibslogapp.model.Koordinat;

import android.location.Location;

import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

/*
    Class indeholder den callback funktion som skal fusedLocationProviderClient anvender
     */

public class LocationCallback extends com.google.android.gms.location.LocationCallback {
 static KoordinatDTO measueredKoordinat;

    LocationCallback(){

    }


    @Override
    public void onLocationResult(LocationResult locationResult) {
        Location l = locationResult.getLastLocation();
        System.out.println(l.getAltitude());
        System.out.println("Latitude: " +l.getLatitude());
        System.out.println("Longitude: " +l.getLongitude());
        measueredKoordinat = new KoordinatDTO(l.getLatitude(),l.getLongitude());


    }


    //Checking if the the GPS is alvalable
    @Override
    public void onLocationAvailability(LocationAvailability locationAvailability) {
        super.onLocationAvailability(locationAvailability);

    }

    public KoordinatDTO getKoordinates(){
        return measueredKoordinat;
    }


}
