package com.example.skibslogapp.datalayer;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

//db.execSQL();

public class SQLconnection {
    //in the future consider implementing a getFilesDir() to make sure the db is put where you want it to be.
    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/database.db",null);

    public void OpretTogt(String tabelNavn, String togtNavn){
        //Oprettelse af SQL tabel til til togted
        db.execSQL("DROP TABLE IF EXISTS "+tabelNavn+";");
        db.execSQL("CREATE TABLE "+tabelNavn+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, nanv TEXT NOT NULL) ");

        //indsprøjtning af kendte informationer til db'en.
        ContentValues togt = new ContentValues();
        togt.put("navn", togtNavn);
        db.insert(tabelNavn, null, togt);

    }

}
