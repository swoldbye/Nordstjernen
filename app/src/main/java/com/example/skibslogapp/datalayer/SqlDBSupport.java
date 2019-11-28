package com.example.skibslogapp.datalayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @author  Claes
 * the purpose of this class is to help, update the database,
 * whenever the app is updated or changed.
 */
public class SqlDBSupport extends SQLiteOpenHelper {
    static final int VERSION = 2;
    static final String DATABASE ="database.db";

    public SqlDBSupport(Context context) {
        super(context,DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
