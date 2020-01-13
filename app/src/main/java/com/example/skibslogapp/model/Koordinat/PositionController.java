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

public class PositionController {

    private PositionCallback callback = new PositionCallback();
    private ForespørgselMåling forespoergsel = new ForespørgselMåling();
    private Activity activity;
    private Context mContext;

    /*
    Fused - Google API sørger selv for at koordinater indhentes på den mest optimal måde. Altså om det skal være GPS, WIFI, Antennemast
     */
    private FusedLocationProviderClient fusedLocationProviderClient;

    public PositionController(Context context, Activity activity) {
        this.mContext = context;
        this.activity = activity;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }


    /*
    Method describes what the request that we make.
     */
    public void startMeassureKoordinat() {
        prepRequestLocationUpdates();
    }


    // Ask for access permission
    public void prepRequestLocationUpdates() {
        System.out.println("Requesting permission to access location");

        // If the user already have given permission, ACCESS_FINE_PERMISSION MAKE USE OF BOTH SATTELITE ANT TELETOWERS
        if( (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            // Permission access start measuering
            System.out.println("User ACCESS");
            startGetCoordinates();
        } else {
            System.out.println("User NO PERMISSION");

            // Will retur false if the user tabs "Bont ask me again/Permission denied".
            // Returns true if the user previusly rejected the message and now try to access it again. -> Indication of user confussion
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(mContext, "Lokation skal være aktiveret for at GPS lokation kan logges.", Toast.LENGTH_SHORT).show();
            }
            // Requesting permission
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1234);
        }
    }


    public void removeLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(callback);
    }


    public void startGetCoordinates() {
        System.out.println("Starting coordinate request");
        isGooglePlayInstalled(mContext);
        //Uses the requestLocationUpdates because I do not want the measuring to happen in the background. I only want it to happen when the Opret_log fraq is visible
        fusedLocationProviderClient.requestLocationUpdates(forespoergsel.getLocationRequest(), callback, null);
    }


    public Position getKoordinates() {
        return callback.getKoordinates();
    }


    /*
        Checks if google Play is installed - Not sure if this is the right way
     */
    private static void isGooglePlayInstalled(Context context) {
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