package com.example.skibslogapp.model.Koordinat;

import com.google.android.gms.location.LocationRequest;

/*
This call contains the request that we do when measuring the koordinates
 */


public class ForespørgselMåling {
    private LocationRequest locationRequest;

    ForespørgselMåling(){
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //Setting a expiration duration so that request wont be pending
        locationRequest.setExpirationDuration(10000);
        //Do one request
        locationRequest.setNumUpdates(1);
    }

    /*
    Return the predefined request.
     */
    public LocationRequest getLocationRequest() {
        return locationRequest;
    }
}
