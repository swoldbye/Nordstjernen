package com.example.skibslogapp.datalayer.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.skibslogapp.model.Togt;

import java.util.LinkedList;
import java.util.List;

public class TogtDAO {

    // Connector to the database
    private SQLiteConnector connector;
    private Context context;

    public TogtDAO(Context context){
        this.context = context;
        connector = new SQLiteConnector(context);
    }


    /**
     * Add a Togt to the database, using the object field values.
     * A new unique ID will be generated for given object,
     * and saved to the 'id' field.
     *
     * @param togt The Togt to save to the database
     */
    public void addTogt(Togt togt){
        SQLiteDatabase database = connector.getWritableDatabase();

        ContentValues row = new ContentValues();
        row.put("name", togt.getName());

        long id = database.insert("togter", null, row);
        togt.setId(id);
    }


    /**
     * Loads all Togt objects from the the local database.
     *
     * @return A list of the loaded Togt objects. The list is empty (not null) if no Togt exists in the database.
     */
    public List<Togt> getTogter(){

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

    /**
     * Checks if the given Togt exists in the database
     * based on the togt ID.
     *
     * @param togt The Togt to check if exists in the database
     * @return True if it exists, false if not
     */
    public boolean togtExists(Togt togt){
        if( togt.getId() == -1 ) return false;

        SQLiteDatabase database = connector.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM togter WHERE id="+togt.getId()+";", null);
        int rowCount = cursor.getCount();
        cursor.close();
        return rowCount > 0;
    }


}
