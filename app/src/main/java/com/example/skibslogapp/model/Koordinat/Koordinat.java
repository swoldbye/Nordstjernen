package com.example.skibslogapp.model.Koordinat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.speech.tts.TextToSpeech;

import androidx.core.app.ActivityCompat;

import com.example.skibslogapp.view.OpretLog_frag;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.Locale;

public class Koordinat {




private LocationCallback callback = new LocationCallback();
private ForespørgselMåling forespørgsel = new ForespørgselMåling();
private Activity fraq_activity;
private Context mContext;
    //private Context mContext;

    /*
    Fuced - Google API sørger selv for at koordinater indhentes på den mest optimal måde. Altså om det skla være GPS, WIFI, Antennemest.
     */
    FusedLocationProviderClient fusedLocationProviderClient;

   public Koordinat(Context context, Activity fraq_activity){
        this.mContext = context;
       this.fraq_activity = fraq_activity;


       fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);



    }

/*
Method describes what the request that we make.
 */
    public void startMeassureKoordinat(){
        prepRequestLocationUpdates();
    }

    //Ask for access permission
    public void prepRequestLocationUpdates(){
        System.out.println("prepRequestLocationUpdates");
        //If the user already have given permission
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("User NO PERMISSION");
            //Requesting permission
            ActivityCompat.requestPermissions(fraq_activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1234);
            return;

        }else{
            //Permission access start measuering
            System.out.println("User ACCESS");
            startGetCoordinates();



        }
    }

    public void removeLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(callback);
    }

    public void startGetCoordinates(){
        //Uses the requestLocationUpdates because i do not want the measureing to happen in the background. I only want it to happen when the Opret_log fraq is vissible
        fusedLocationProviderClient.requestLocationUpdates(forespørgsel.getLocationRequest(), callback, null);
    }

    public KoordinatDTO getKoordinates(){
        return callback.getKoordinates();
    }


    /*
    Her er callbackfunktionen som udføres efter at koordinater er indhentet.


    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location l = locationResult.getLastLocation();
            System.out.println(l.getAltitude());
            System.out.println("Latitude: " +l.getLatitude());
            System.out.println("Longitude: " +l.getLongitude());

        }

        //Checking if the the GPS is alvalable
        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);

        }
    };
  */
}

/*
    public void getKoordinat(){
        //Creatin a request for koordinates
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //Setting a expiration duration so that request wont be pending
        locationRequest.setExpirationDuration(10000);
        //Do one request
        locationRequest.setNumUpdates(1);


        fusedLocationProviderClient.requestLocationUpdates(locationRequest, callback, null);


    }


 */