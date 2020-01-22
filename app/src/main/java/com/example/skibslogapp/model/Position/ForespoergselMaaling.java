package com.example.skibslogapp.model.Position;

import com.google.android.gms.location.LocationRequest;

/*
This call contains the request that we do when measuring the koordinates
 */
public class ForespoergselMaaling {
    private LocationRequest locationRequest;
    private final static int EXPIRATION_TIME = 100000;
    private final static int INTERVAL_BETWEEN_MEASUREMENTS = 10000;
    private final static int UPDATE_NUM = 10000;

    ForespoergselMaaling() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //Setting a expiration duration so that request wont be pending

        //Set to 10 measurements * set interval
        locationRequest.setExpirationDuration(EXPIRATION_TIME);
        //Setting interval between each request
        locationRequest.setInterval(INTERVAL_BETWEEN_MEASUREMENTS);
        //Do one request
        locationRequest.setNumUpdates(UPDATE_NUM);
    }

    /*
    Return the predefined request.
     */
    public LocationRequest getLocationRequest() {
        return locationRequest;
    }
}
