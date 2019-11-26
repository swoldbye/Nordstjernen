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
        TextView textView = new TextView(this);
        textView.append("Herunder resultatet af en forespørgsel på en SQLite-database\n\n");
        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(textView);
        setContentView(scrollView);


        // Oprettelse af database
        db = SQLiteDatabase.openOrCreateDatabase(getFilesDir() + "/database.db", null);


        // Oprette logpunkts tabel tabel - foregår via SQL
        db.execSQL("DROP TABLE IF EXISTS LogPunkter;");
        db.execSQL("CREATE TABLE LogPunkter (_id INTEGER PRIMARY KEY, note TEXT NOT NULL, antalRore INTEGER," +
                "kurs TEXT NOT NULL, vindretning TEXT NOT NULL);");

        // Oprette etape tabel tabel
        db.execSQL("CREATE TABLE IF NOT EXISTS  etape (\n" +
                "                        id INTEGER PRIMARY KEY,\n" +
                "                        reference VARCHAR(32) NOT NULL,\n" +
                "                        name TEXT NOT NULL,\n" +
                "                        log_id INTEGER,\n" +
                "                        FOREIGN KEY(log_id) REFERENCES LogPunkter(id)\n" +
                "                     );");


        addLog("Den var god", 22,"nnø","sv");
        addLog("Nu Okay da", 7,"øv","nnn");


        textView.append(getTogter());


        db.close();

    }

    // Oprette en logpunkt
    public void addLog(String note, int antalRore, String kurs, String vindretning){
        db.execSQL("INSERT INTO LogPunkter (note, antalRore, kurs, vindretning) VALUES ('"+note+"', "+antalRore+",'"+kurs+"', '"+vindretning+"');");
    }

    public String getTogter(){
        // Søgning
        //Cursor cursor = db.rawQuery("SELECT * from kunder WHERE kredit > 100 ORDER BY kredit ASC;", null);
        String[] kolonner = {"_id", "note", "antalRore", "kurs", "vindretning"};
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

            res += id + " | " + note + " | " + aRore +" | "+ kurs +" | "+vind+"\n";
        }
        cursor.close();
        return res;
    }
}
