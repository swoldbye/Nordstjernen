package com.example.skibslogapp.datalayer.sheets_db;

import android.database.sqlite.SQLiteDatabase;

public class SQLconnection {
    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir()+"/database.db",null);

    public void OpretTabel(){

    }

}
