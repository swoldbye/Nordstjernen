package com.example.skibslogapp.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.speech.tts.TextToSpeech;

import androidx.core.app.ActivityCompat;

import com.example.skibslogapp.view.OpretLog_frag;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.Locale;

public class Koordinat {

    //private Context mContext;
    FusedLocationProviderClient fusedLocationProviderClient;

   public Koordinat(Context context, Activity fraq_activity){
        //this.mContext = context;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        //Ask for access permission
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(fraq_activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1234);
            return;
        }

    }


    public void getKoordinat(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setExpirationDuration(10000);
        locationRequest.setNumUpdates(1);


        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);


    }

    /*
    Her er callbackfunktionen som udføres efter at koordinater er indhentet.
     */

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location l = locationResult.getLastLocation();
            System.out.println(l.getAltitude());
            System.out.println("Latitude: " +l.getLatitude());
            System.out.println("Longitude: " +l.getLongitude());

        }
    };

}
