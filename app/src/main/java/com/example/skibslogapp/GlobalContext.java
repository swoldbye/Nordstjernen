package com.example.skibslogapp;

import android.app.Application;
import android.content.Context;

public class GlobalContext extends Application {
    private static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context get(){
        return context;
    }
}
