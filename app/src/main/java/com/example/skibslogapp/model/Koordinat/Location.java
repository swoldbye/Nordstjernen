package com.example.skibslogapp.model.Koordinat;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import static android.content.Context.LOCATION_SERVICE;

/*
Denne Klasse er blot en klasse, hvor der anvedes en LocationManager
Denne klasse er ikke i brug, eftersom at det antages at alle telefoner har installeret
Google API'et
 */


public class Location implements LocationListener {
    LocationManager locationManager;
    private Context mContext;

    public Location(Context context){
        mContext = context;
    }

    public void initiate() {

        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        Criteria kriterium = new Criteria();
        kriterium.setAccuracy(Criteria.ACCURACY_FINE);
        String udbyder = locationManager.getBestProvider(kriterium, true); // "gps" hvis slået til
        if (udbyder == null) {
            mContext.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            return; // Der var ikke tændt for nogen udbyder. Tænd og prøv igen
        }
// Bed om opdateringer, der går mindst 60. sekunder og mindst 20. meter mellem hver
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }

        System.out.println("Start requestLocation from Test Location");
        locationManager.requestLocationUpdates(udbyder, 5000, 5, this);
        }


        protected void onPause() {

            locationManager.removeUpdates(this);
        }
        @Override
        public void onLocationChanged(android.location.Location sted) { // Metode specificeret i LocationListener
            System.out.println("sted: "+sted);
        }
        // ignorér - men vi er nødt til at have metoderne for at implementere LocationListener
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
        public void onProviderEnabled(String arg0) {
            System.out.println("onProviderEnabled" +arg0);
        }
        public void onProviderDisabled(String arg0) {
            System.out.println("onProviderDisabled "+arg0);
        }
}
