package com.example.skibslogapp.datalayer;

import android.database.sqlite.SQLiteDatabase;

//db.execSQL();

public class SQLconnection {
    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("Todo: fix getFilesDir()"+"/database.db",null);

    public void OpretTabel(String tabelNavn){
        db.execSQL("DROP TABLE IF EXISTS "+tabelNavn+";");
        db.execSQL("CREATE TABLE "+tabelNavn+" (_id INTEGER PRIMARY KEY AUTOINCREMENT ) ");

    }

}
