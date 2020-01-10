package com.example.skibslogapp.model.Koordinat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
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
    static FusedLocationProviderClient fusedLocationProviderClient;

    public Koordinat(Context context, Activity fraq_activity) {
        this.mContext = context;
        this.fraq_activity = fraq_activity;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    /*
    Method describes what the request that we make.
     */
    public void startMeassureKoordinat() {
        prepRequestLocationUpdates();
    }

    //Ask for access permission
    //TODO: Fix shouldShowRequestPermissionRationale

    public void prepRequestLocationUpdates() {
        System.out.println("prepRequestLocationUpdates");
        //If the user already have given permission, ACCESS_FINE_PERMISSION MAKE USE OF BOTH SATTELITE ANT TELETOWERS
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


            //Permission access start measuering
            System.out.println("User ACCESS");
            startGetCoordinates();
        } else {
            System.out.println("User NO PERMISSION");

            //Will retur false if the user tabs "Bont ask me again/Permission denied".
            //Returns true if the user previusly rejected the message and now try to access it again. -> Indication of user confussion
            if (ActivityCompat.shouldShowRequestPermissionRationale(fraq_activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(mContext, "Lokation skal være aktiveret for at GPS lokation kan logges.", Toast.LENGTH_SHORT).show();
            }
            //Requesting permission
            ActivityCompat.requestPermissions(fraq_activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1234);
        }
    }


    public void removeLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(callback);
    }


    public void startGetCoordinates() {
        isGooglePlayInstalled(mContext);
        //Uses the requestLocationUpdates because i do not want the measureing to happen in the background. I only want it to happen when the Opret_log fraq is vissible
        fusedLocationProviderClient.requestLocationUpdates(forespørgsel.getLocationRequest(), callback, null);
    }


    public KoordinatDTO getKoordinates() {
        return callback.getKoordinates();
    }


    /*
    This method checks if google Play is installed - Not sure if this is the right way
     */
    public static void isGooglePlayInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {

            //context.getPackageManager().getProviderInfo()
            PackageInfo info = pm.getPackageInfo("com.android.vending", PackageManager.GET_ACTIVITIES);
            String label = (String) info.applicationInfo.loadLabel(pm);
            app_installed = (label != null && !label.equals("Market"));
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        System.out.println("Google play is: " + app_installed);
    }
}