package com.example.skibslogapp.model;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class FucedLocator {

    static FusedLocationProviderClient fusedLocationProviderClient = null;

    private static Context mContext;

    public FucedLocator(Context context){}

    public static FusedLocationProviderClient getInstance(Context context){
        mContext = context;
        if (fusedLocationProviderClient == null)
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
        return fusedLocationProviderClient;
    }

}
