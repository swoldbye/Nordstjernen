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


public class EtapeDAO {


    // Connector to the database
    private SQLiteConnector connector;
    private Context context;


    public EtapeDAO(Context context) {
        this.context = context;
        connector = new SQLiteConnector(context);
    }


    /**
     * Add an Etape to the database. A new unique ID will be generated for
     * it, and saved to the 'id' field.
     * The Etape will be connected to the given Togt, such that following calls
     * getEtaper(...) will include the Etape in the returned list.
     *
     * The Togt must already be saved to the database using the {@link TogtDAO#addTogt(Togt)} method.
     *
     * @param togt The Togt to add the Etape to
     * @param etape The Etape to add to the
     */
    public void addEtape( Togt togt, Etape etape ){

        TogtDAO togtDAO = new TogtDAO(context);

        if( !togtDAO.togtExists(togt) )
            throw new DAOException("Togt with ID "+togt.getId()+" doesn't exist in database");

        SQLiteDatabase database = connector.getWritableDatabase();

        ContentValues row = new ContentValues();
        row.put("togt", togt.getId());
        if( etape.getStartDate() != null )
            row.put( "startDate", etape.getStartDate().getTime() );
        if( etape.getEndDate() != null )
            row.put( "endDate", etape.getEndDate().getTime() );

        long id = database.insert("etaper", "endDate", row);
        etape.setId(id);
        etape.setTogtId(togt.getId());

        togtDAO.togtUpdated(etape.getTogtId());
    }


    /**
     * Loads all Etape objects from the database for the given Togt.
     *
     * @param togt The Togt to fetch the Logpunkter for
     * @return A list of Etape objects. The list is empty if none exists for the given Togt
     */
    public List<Etape> getEtaper(Togt togt ){

        // Check if Togt exists
        if( !new TogtDAO(context).togtExists(togt) )
            throw new DAOException("Togt with ID "+togt.getId() + " doesn't exist in the database");

        SQLiteDatabase database = connector.getReadableDatabase();
        LinkedList<Etape> etaper = new LinkedList<>();

        // Create cursor for database rows
        Cursor cursor = database.rawQuery("SELECT * FROM etaper WHERE togt="+togt.getId()+";",null);
        // Load data from each row
        while( cursor.moveToNext() ){

            int column = -1;

            long id = cursor.getInt( cursor.getColumnIndex("id"));
            long togtId = cursor.getInt( cursor.getColumnIndex("togt"));

            Date startDate = null;
            column = cursor.getColumnIndex("startDate");
            if( !cursor.isNull(column) ){
                startDate = new Date(cursor.getLong(column));
            }

            Date endDate = null;
            column = cursor.getColumnIndex("endDate");
            if( !cursor.isNull(column) ){
                endDate = new Date(cursor.getLong(column));
            }

            etaper.add( new Etape(id, togtId, startDate, endDate) );
        }
        cursor.close();

        return etaper;
    }



    /**
     * Update an Etape already added to the database, to values
     * of the given object's fields.
     * The Etape must already have been saved to the database using
     * the {@link EtapeDAO#addEtape(Togt, Etape)} method.
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

        new TogtDAO(context).togtUpdated(etape.getTogtId());
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


}