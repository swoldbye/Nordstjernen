package com.example.skibslogapp.model.positionregistration;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.example.skibslogapp.model.Position;
import com.example.skibslogapp.view.opretlogpunkt.OpretLog_frag;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class PositionController {

    private PositionCallback callback = new PositionCallback();
    private ForespoergselMaaling forespoergsel = new ForespoergselMaaling();
    private OpretLog_frag fragment;
    private Context mContext;

    /*
    Fused - Google API sørger selv for at koordinater indhentes på den mest optimal måde. Altså om det skal være GPS, WIFI, Antennemast
     */
    private FusedLocationProviderClient fusedLocationProviderClient;

    public PositionController(Context context, OpretLog_frag fragment) {
        this.mContext = context;
        this.fragment = fragment;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    /*
    Method describes what the request that we generateEtape.
     */
    public void startMeassureKoordinat() {
        System.out.println("Requesting permission to access location");

        // If the user already have given permission, ACCESS_FINE_PERMISSION MAKE USE OF BOTH SATTELITE ANT TELETOWERS
        if ((ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            // Permission access start measuering
            System.out.println("User ACCESS");
            startGetCoordinates();
        } else {
            System.out.println("User NO PERMISSION");


            //Requesting permission
            fragment.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1234);
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