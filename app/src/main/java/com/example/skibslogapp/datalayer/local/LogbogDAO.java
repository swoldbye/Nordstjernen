package com.example.skibslogapp.datalayer.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.model.Togt;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


//TODO: Perhaps the DAO should be split into one for each DTO (Logpunkt, Etape, Togt)

public class LogbogDAO {

    // Connector to the database
    private SQLiteConnector connector;

    public LogbogDAO(Context context){
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
     * Add an Etape to the database. A new unique ID will be generated for
     * it, and saved to the 'id' field.
     * The Etape will be connected to the given Togt, such that following calls
     * getEtaper(...) will include the Etape in the returned list.
     *
     * The Togt must already be saved to the database using the {@link LogbogDAO#addTogt(Togt)} method.
     *
     * @param togt The Etape to add the Logpunkt to
     * @param etape The Etape to add to the
     */
    public void addEtape( Togt togt, Etape etape ){

        if( !etapeExists(etape) )
            throw new DAOException("Etape with ID "+etape.getId()+" doesn't exist in database");

        SQLiteDatabase database = connector.getWritableDatabase();

        ContentValues row = new ContentValues();
        row.put("togt", togt.getId());
        if( etape.getStartDate() != null )
            row.put( "startDate", etape.getStartDate().getTime() );
        if( etape.getEndDate() != null )
            row.put( "endDate", etape.getEndDate().getTime() );

        long id = database.insert("etaper", "endDate", row);
        etape.setId(id);
    }


    /**
     * Loads all Etape objects from the database for the given Togt.
     *
     * @param togt The Togt to fetch the Logpunkter for
     * @return A list of Etape objects. The list is empty if none exists for the given Togt
     */
    public List<Etape> getEtaper( Togt  togt ){

        // Check if Togt exists
        if( !togtExists(togt) )
            throw new DAOException("Togt with ID "+togt.getId() + " doesn't exist in the database");

        SQLiteDatabase database = connector.getReadableDatabase();
        LinkedList<Etape> etaper = new LinkedList<>();

        // Create cursor for database rows
        Cursor cursor = database.rawQuery("SELECT * FROM etaper WHERE togt="+togt.getId()+";",null);
        // Load data from each row
        while( cursor.moveToNext() ){
            Etape etape = new Etape(
                    cursor.getInt( cursor.getColumnIndex("id") ),
                    new Date(cursor.getLong(cursor.getColumnIndex("startDate"))),
                    new Date(cursor.getLong(cursor.getColumnIndex("endDate")))
            );
            etaper.add(etape);
        }
        cursor.close();

        return etaper;
    }


    /**
     * Update an Etape already added to the database, to values
     * of the given object's fields.
     * The Etape must already have been saved to the database using
     * the {@link LogbogDAO#addEtape(Togt, Etape)} method.
     *
     * @param etape Etape to update
     */
    public void updateEtape( Etape etape ){

        if( !etapeExists(etape) )
            throw new DAOException("Etape with ID "+etape.getId()+" doesn't exist in database");

        // Create cursor for database rows
        SQLiteDatabase database = connector.getReadableDatabase();

        ContentValues row = new ContentValues();
        if( etape.getStartDate() != null )
            row.put( "startDate", etape.getStartDate().getTime() );
        if( etape.getEndDate() != null )
            row.put( "endDate", etape.getEndDate().getTime() );

        database.update("etaper", row, "id="+etape.getId(), null );
    }


    /**
     * Add a Logpunkt to the database. A new unique ID will be generated for
     * the Logpunkt, and set to the 'id' field.
     * The Logpunkt will be connected to the Etape, such that following calls
     * getEtaper(...) will include the Logpunkt in the returned list.
     *
     * @param etape The Etape to add the Logpunkt to
     * @param logpunkt The Logpunkt to add to the database
     */
    public void addLogpunkt( Etape etape, Logpunkt logpunkt ){

        // Error checking
        if( logpunktExists(logpunkt) )
            throw new DAOException("Logpunkt with ID "+logpunkt.getId()+" already exists in database");

        if( !etapeExists(etape) )
            throw new DAOException("Etape with ID "+etape.getId()+" doesn't exist in database");

        // Create row for databse
        SQLiteDatabase database = connector.getWritableDatabase();
        ContentValues row = new ContentValues();

        row.put( "etape", etape.getId() );
        row.put( "date", logpunkt.getDate().getTime() );
        row.put( "note", logpunkt.getNote() );
        row.put( "vindretning", logpunkt.getVindretning() );
        row.put( "sejlfoering", logpunkt.getSejlfoering() );
        row.put( "mandOverBord", logpunkt.getMandOverBord() );
        row.put( "sejlstilling", logpunkt.getSejlstilling() );

        /* Only add these elements if they've been manually set ( != 1 ) */
        if( logpunkt.getRoere() >= 0 )
            row.put( "roere", logpunkt.getRoere() );

        if( logpunkt.getKurs() >= 0 )
            row.put( "kurs", logpunkt.getKurs() );

        if( logpunkt.getHals() >= 0 )
            row.put( "hals", logpunkt.getHals() );

        long id = database.insert("logpunkter", null, row);
        logpunkt.setId(id);
    }


    /**
     * Loads all logpunkter from the database for the given Etape.
     *
     * @param etape The Etape to fetch the Logpunkter for
     * @return A list of Logpunkt. The list is empty if none exists for the Etape
     */
    public List<Logpunkt> getLogpunkter( Etape etape ){

        // Check if etape exists
        if( !etapeExists(etape))
            throw new DAOException("Etape with ID "+ etape.getId()+" doesn't exist in database");

        // Fetch from database
        SQLiteDatabase database = connector.getReadableDatabase();
        List<Logpunkt> logpunkter = new LinkedList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM logpunkter WHERE etape="+etape.getId()+";", null);

        while( cursor.moveToNext() ){
            Logpunkt logpunkt = new Logpunkt( new Date( cursor.getLong(cursor.getColumnIndex("date"))));
            logpunkt.setId( cursor.getInt( cursor.getColumnIndex("id")));

            logpunkt.setVindretning( cursor.getString( cursor.getColumnIndex("vindretning") ));
            logpunkt.setSejlfoering( cursor.getString( cursor.getColumnIndex("sejlfoering") ));
            logpunkt.setSejlstilling( cursor.getString( cursor.getColumnIndex("sejlstilling") ));
            logpunkt.setNote( cursor.getString( cursor.getColumnIndex("note") ));
            logpunkt.setMandOverBord( cursor.getInt( cursor.getColumnIndex("mandOverBord")) != 0 );

            // Integer values should only be set if they are not null in database,
            // otherwise default to -1 (class default value). Same for other int values
            if( !cursor.isNull( cursor.getColumnIndex("hals")) )
                logpunkt.setHals(cursor.getInt(cursor.getColumnIndex("hals")));

            if( !cursor.isNull( cursor.getColumnIndex("roere")) )
                logpunkt.setRoere( cursor.getInt( cursor.getColumnIndex("roere")));

            if( !cursor.isNull( cursor.getColumnIndex("kurs")) )
                logpunkt.setHals(cursor.getInt(cursor.getColumnIndex("kurs")));

            logpunkter.add(logpunkt);
        }

        cursor.close();
        database.close();

        return logpunkter;
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
        Cursor cursor = database.rawQuery("SELECT * FROM togt WHERE id="+togt.getId()+";", null);
        int rowCount = cursor.getCount();
        cursor.close();
        return rowCount > 0;
    }


    /**
     * Checks if the given Etape exists in the database
     * based on the etape ID.
     *
     * @param etape The etape to check if exists in the database
     * @return True if it exists, false if not
     */
    public boolean etapeExists(Etape etape){
        if( etape.getId() == -1 ) return false;

        SQLiteDatabase database = connector.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM etaper WHERE id="+etape.getId()+";", null);
        int rowCount = cursor.getCount();
        cursor.close();
        return rowCount > 0;
    }


    /**
     * Checks if the given Logpunkt exists in the database
     * based on the ID.
     *
     * @param logpunkt The etape to check if exists in the database
     * @return True if it exists, false if not
     */
    public boolean logpunktExists(Logpunkt logpunkt){
        if( logpunkt.getId() == -1 ) return false;

        SQLiteDatabase database = connector.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM logpunkter WHERE id="+logpunkt.getId()+";", null);
        int rowCount = cursor.getCount();
        cursor.close();
        return rowCount > 0;
    }
}
