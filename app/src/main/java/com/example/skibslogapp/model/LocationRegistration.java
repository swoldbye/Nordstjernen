package com.example.skibslogapp.model;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import com.google.android.gms.location.FusedLocationProviderClient;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;



import static android.location.Criteria.ACCURACY_HIGH;

public class LocationRegistration extends Service implements LocationListener {

    Location loc;
    boolean isGPS_enabled = false;
    boolean isNetwork_enabled = false;
    LocationManager locationManager;
    LocationListener locationListener;
    Context mContext;
    double longitude;
    double latitude;
    final int MIN_TIME = 1000;
    final int MIN_DISTANCE_CHANGE_FOR_UPDATE = 10;
    FusedLocationProviderClient fusedLocationProviderClient;



    public LocationRegistration(Context context) {
        this.mContext = context;


        locationManager = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
        //GPS Provider rely on the Sattelite signal to  make locate location. Alternate use NETWORK_PROVIDER
        isGPS_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //Checks if the network is enabled.
        isNetwork_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        try {
            if (isGPS_enabled) {

                //A class indicating the application criteria for selecting a location provider. Providers may be ordered according to accuracy, power usage, ability to report altitude, speed, bearing, and monetary cost.


                //locationManager.requestSingleUpdate(kriterierForCoordinat,this,);
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        MIN_TIME,
                        MIN_DISTANCE_CHANGE_FOR_UPDATE, this);
                if (locationManager != null) {
                    loc = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (loc != null) {
                        latitude = loc.getLatitude();
                        longitude = loc.getLongitude();

                        System.out.println(latitude);
                        System.out.println(longitude);

                    }
                }


            }


        } catch (Exception e) {
        e.printStackTrace();
    }

}


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



}
