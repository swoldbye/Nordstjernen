package com.example.skibslogapp.datalayer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;


class TogtDb extends SQLiteOpenHelper {
    static final int VERSION = 2;
    static final String DATABASE ="database.db";

    static final String TABELLog = "LogPunkter";
    static final String TABELetape = "etape";
    static final String TABELtogt = "etape";

    //Global REF
    static final String ETAPEID = "etape_id";
    static final String TOGTID = "TOGT_id";


    //Local REF
    static final String ID = "_id";
    static final String NAVN = "navn";
    static final String NOTE= "note";
    static final String KURS= "kurs";
    static final String ANTALRORE = "antalRore";
    static final String VINDRETNING = "vindretning";

    public TogtDb(Context context) {
        super(context, DATABASE, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABELLog + " ("
                + ID + " integer primary key, "
                + NOTE + " text not null, " + ANTALRORE + " INTEGER, "
                +KURS +" text not null, " +VINDRETNING +" text not null,"
                +ETAPEID +" INTEGER," +
                "FOREIGN KEY("+ETAPEID+") REFERENCES LogPunkter(id))");

        db.execSQL("CREATE TABLE etape (\n" +
                "                        id INTEGER PRIMARY KEY,\n" +
                "                        name TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Todo: set this upgrade function up properly, at the momment it does not have much use.
        db.execSQL("drop table " + TABELLog);
        this.onCreate(db);
    }
}

public class BenytSQLiteOpenHelper extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.append("Herunder resultatet af en forespørgsel på en SQLite-database\n\n");
        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(textView);
        setContentView(scrollView);


        // Oprettelse af database
        TogtDb kundeDb = new TogtDb(this);
        SQLiteDatabase db = kundeDb.getWritableDatabase();

        // Oprette en række
        ContentValues række = new ContentValues();
        række.put(TogtDb.NOTE, "Jacob Nordfalk");
        række.put(TogtDb.ANTALRORE, 500);
        række.put(TogtDb.KURS, "something");
        række.put(TogtDb.VINDRETNING, "whatDo I know");
        række.put(TogtDb.ETAPEID, 1);

        db.insert(TogtDb.TABELLog, null, række);


        // Søgning
        String[] kolonner = {TogtDb.ID, TogtDb.NOTE, TogtDb.ANTALRORE,kundeDb.KURS,kundeDb.VINDRETNING,kundeDb.ETAPEID};
        String valg = "antalRore > 100"; // WHERE
        String sortering = "antalRore ASC"; // ORDER BY
        Cursor cursor = db.query(TogtDb.TABELLog, kolonner, valg, null, null, null, sortering);

        String res="";
        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            String note = cursor.getString(1);
            int aRore = cursor.getInt(2);
            String kurs = cursor.getString(3);
            String vind = cursor.getString(4);
            int etape = cursor.getInt(5);

            res += id + " | " + note + " | " + aRore +" | "+ kurs +" | "+vind+" | "+etape+" | "+"\n";
        }
        textView.append(res);
        cursor.close();

        db.close();
    }
}

