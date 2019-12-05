package com.example.skibslogapp.datalayer.local;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// The database can be found in /data/data/com.example.skibslogapp/

class SQLiteConnector extends SQLiteOpenHelper {

    // Increment version number if you change anything
    private static final int VERSION = 2;

    // Name of database
    private static final String DATABASE ="logbog.db";
    private static final boolean CLEAR_ON_START = false; // ONLY FOR DEBUGGING PURPOSES


    public SQLiteConnector(Context context) {
        super(context, DATABASE, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        /* This method is called when the database
            is created (i.e. if it didn't exist when
            creating the SQLiteConnector) */

        // Togt Table
        db.execSQL(
            "CREATE TABLE togter (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL" +
            ")"
        );

        // Etape Table
        db.execSQL(
            "CREATE TABLE etaper (" +
                "id INTEGER," +
                "togt INTEGER," +
                "startDate INTEGER NOT NULL," +
                "endDate INTEGER," +
                "PRIMARY KEY(id)," +
                "FOREIGN KEY(togt) REFERENCES togter(id)"+
            ")"
        );

        // Logpunkt Table
        db.execSQL(
            "CREATE TABLE logpunkter (" +
                "id INTEGER," +
                "etape INTEGER," +
                "note TEXT," +
                "roere INTEGER,"+
                "kurs INTEGER,"+
                "vindretning TEXT,"+
                "PRIMARY KEY(id)," +
                "FOREIGN KEY(etape) REFERENCES etaper(id)"+
            ")"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Todo: set this upgrade function up properly, at the momment it does not have much use.
        db.execSQL("DROP TABLE togter");
        db.execSQL("DROP TABLE etaper");
        db.execSQL("DROP TABLE logpunkter");
        this.onCreate(db);
    }
}

