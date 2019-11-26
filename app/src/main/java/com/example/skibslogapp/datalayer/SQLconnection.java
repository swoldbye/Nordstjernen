package com.example.skibslogapp.datalayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skibslogapp.R;

//db.execSQL();

public class SQLconnection extends AppCompatActivity {
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for debugging===========================
        TextView textView = new TextView(this);
        textView.append("Herunder resultatet af en forespørgsel på en SQLite-database\n\n");
        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(textView);
        setContentView(scrollView);
        //========================================

        // Oprettelse af database
        db = SQLiteDatabase.openOrCreateDatabase(getFilesDir() + "/database.db", null);


        // Oprette logpunkts tabel tabel - foregår via SQL
        db.execSQL("DROP TABLE IF EXISTS LogPunkter;");
        db.execSQL("CREATE TABLE LogPunkter (_id INTEGER PRIMARY KEY, note TEXT NOT NULL, antalRore INTEGER," +
                "kurs TEXT NOT NULL, vindretning TEXT NOT NULL," +
                "etape_id INTEGER, \n" +
                "FOREIGN KEY(etape_id) REFERENCES LogPunkter(id));");

        // Oprette etape tabel tabel
        db.execSQL("DROP TABLE IF EXISTS etape;");
        db.execSQL("CREATE TABLE etape (\n" +
                "                        id INTEGER PRIMARY KEY,\n" +
                "                        name TEXT NOT NULL);");


        //for debugging===========================
        addEtape("geveGodsEtape");

        addLog("Den var god", 22,"nnø","sv",1);
        addLog("Nu Okay da", 7,"øv","nnn",1);

        textView.append(getTogter());
        //========================================

        db.close();

    }
    //_________________________________________
    // Oprette ny data
    public void addLog(String note, int antalRore, String kurs, String vindretning, int etapeID){
        db.execSQL("INSERT INTO LogPunkter (note, antalRore, kurs, vindretning, etape_id) VALUES ('"+note+"', "+antalRore+",'"+kurs+"', '"+vindretning+"','"+etapeID+"');");
    }

    public void addEtape(String name){
        db.execSQL("INSERT INTO etape (name) VALUES ('"+name+"');");
    }

    //_________________________________________
    // Hænt data ud
    public String getTogter(){
        // Søgning
        //Cursor cursor = db.rawQuery("SELECT * from kunder WHERE kredit > 100 ORDER BY kredit ASC;", null);
        String[] kolonner = {"_id", "note", "antalRore", "kurs", "vindretning", "etape_id"};
        String valg = "antalRore > 0"; // WHERE
        String sortering = "antalRore ASC"; // ORDER BY
        Cursor cursor = db.query("LogPunkter", kolonner, valg, null, null, null, sortering);

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
        cursor.close();
        return res;
    }
}
