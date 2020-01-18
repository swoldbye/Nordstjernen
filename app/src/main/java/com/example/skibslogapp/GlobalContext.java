package com.example.skibslogapp;

import android.app.Application;
import android.content.Context;

/**
 * A global static way of getting the application context.
 *
 * Use the static 'get()' method instead of passing the context
 * or activity/fragments around, if someone needs the context.
 */
public class GlobalContext extends Application {
    private static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    /** Retrieves the application context */
    public static Context get(){
        return context;
    }
}
