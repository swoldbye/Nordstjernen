package com.example.skibslogapp.datalayer.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


//TODO: Perhaps the DAO should be split into one for each DTO (LogPunkt, Etape, Togt)


public class LogbogDAO {

    private SQLiteConnector connector;

    public LogbogDAO(Context context){
        connector = new SQLiteConnector(context);
    }

    public void addTogt(Togt togt){
        SQLiteDatabase database = connector.getWritableDatabase();

        ContentValues row = new ContentValues();
        row.put("name", togt.getName());

        database.insert("togter", null, row);
    }

    /**
     * Loads all togter from the local storage
     *
     * @return A list of the loaded togter
     */
    public List<Togt> getTogter(){
        // Note: doesn't seem like exceptions are an issue with this solution

        // Create cursor for database rows
        SQLiteDatabase database = connector.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM togter;", null );
        //Cursor cursor = database.query("togter", null, null, null,null, null, null);
        LinkedList<Togt> togter = new LinkedList<>();

        // Load data from each row
        while( cursor.moveToNext() ){
            Togt togt = new Togt(cursor.getString(1));
            togt.setId(cursor.getInt(0));
            togter.add(togt);
        }
        cursor.close();

        return togter;
    }


    public void addEtape( Togt togt, Etape etape ){
        SQLiteDatabase database = connector.getWritableDatabase();

        ContentValues row = new ContentValues();
        if( etape.getStartDate() != null )
            row.put( "startDate", etape.getStartDate().getTime() );
        if( etape.getEndDate() != null )
            row.put( "endDate", etape.getEndDate().getTime() );

        database.insert("etaper", "endDate", row);
    }


    public List<Etape> getEtaper( Togt  togt ){
        // Create cursor for database rows
        SQLiteDatabase database = connector.getReadableDatabase();
        String[] selectionArgs = { Integer.toString(togt.getId()) };
        Cursor cursor = database.rawQuery("SELECT * FROM etaper WHERE togt=?;", selectionArgs );
        //Cursor cursor = database.query("togter", null, null, null,null, null, null);
        LinkedList<Etape> etaper = new LinkedList<>();


        // Load data from each row
        while( cursor.moveToNext() ){
            Etape etape = new Etape(
                    cursor.getInt(0),
                    new Date(cursor.getLong(2)),
                    new Date(cursor.getLong(3))
            );

            etaper.add(etape);
        }
        cursor.close();

        return etaper;
    }



    public void updateEtape( Etape etape ){
        // Create cursor for database rows
        SQLiteDatabase database = connector.getReadableDatabase();

        ContentValues row = new ContentValues();
        if( etape.getStartDate() != null )
            row.put( "startDate", etape.getStartDate().getTime() );
        if( etape.getEndDate() != null )
            row.put( "endDate", etape.getEndDate().getTime() );

        database.update("etaper", row, "id="+etape.getId(), null );
    }
}
