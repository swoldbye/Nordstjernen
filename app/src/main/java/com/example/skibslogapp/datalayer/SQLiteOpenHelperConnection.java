package com.example.skibslogapp.datalayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author  Claes
 * the purpose of this class is to help, update the database,
 * whenever the app is updated or changed.
 *//*
public class kSqlDBSupport extends SQLiteOpenHelper {
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




    public SqlDBSupport(Context context) {
        super(context,DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABELLog + " ("
                + ID + " integer primary key, "
                + NOTE + " text not null, " + ANTALRORE + " INTEGER, "
                +KURS +"TEXT NOT NULL, " +VINDRETNING +"TEXT NOT NULL,"
                +ETAPEID +" INTEGER," +
                "FOREIGN KEY("+ETAPEID+") REFERENCES LogPunkter(id))");
        /*
        // Oprette etape tabel tabel
        db.execSQL("DROP TABLE IF EXISTS etape;");
        db.execSQL("CREATE TABLE etape (\n" +
                "                        id INTEGER PRIMARY KEY,\n" +
                "                        name TEXT NOT NULL);");*/

    /*}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Todo: set this upgrade function up properly, at the momment it does not have much use.
        db.execSQL("drop table " + TABELLog);
        this.onCreate(db);
    }
}
public class SQLiteOpenHelperConnection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*TextView textView = new TextView(this);
        textView.append("Herunder resultatet af en forespørgsel på en SQLite-database\n\n");
        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(textView);
        setContentView(scrollView);*/


        // Oprettelse af database
/*
        SqlDBSupport appDB = new SqlDBSupport(this);
        SQLiteDatabase db = SqlDBSupport.getWritableDatabase();

        // Oprette en række
        ContentValues række = new ContentValues();
        række.put(SqlDBSupport.NOTE, "Jacob Nordfalk");
        række.put(SqlDBSupport.ANTALRORE, 500);
        db.insert(SqlDBSupport.TABELLog, null, række);

        db.execSQL("INSERT INTO kunder (navn, kredit) VALUES ('Troels Nordfalk', 400);");

        // Søgning
        //Cursor cursor = db.rawQuery("SELECT * from kunder WHERE kredit > 100 ORDER BY kredit ASC;", null);
        String[] kolonner = {SqlDBSupport.ID, SqlDBSupport.NOTE, SqlDBSupport.ANTALRORE};
        String valg = "kredit > 100"; // WHERE
        String sortering = "kredit ASC"; // ORDER BY
        Cursor cursor = db.query(SqlDBSupport.TABELLog, kolonner, valg, null, null, null, sortering);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            String navn = cursor.getString(1);
            int kredit = cursor.getInt(2);
            textView.append(id + "  " + navn + " " + kredit + "\n");
        }
        cursor.close();

        db.close();
    }
}*/