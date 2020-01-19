package com.example.skibslogapp.datalayer.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Position.Position;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.model.Togt;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class LogpunktDAO {

    // Connector to the database
    private SQLiteConnector connector;
    private Context context;

    public LogpunktDAO(Context context) {
        this.context = context;
        connector = new SQLiteConnector(context);
    }


    /**
     * Add a Logpunkt to the database. A new unique ID will be generated for
     * the Logpunkt, and set to the 'id' field.
     * The Logpunkt will be connected to the Etape, such that following calls to
     * getEtaper(...) will include the Logpunkt in the returned list.
     *
     * @param etape The Etape to add the Logpunkt to
     * @param logpunkt The Logpunkt to add to the database
     */
    public void addLogpunkt( Etape etape, Logpunkt logpunkt ){

        // Error checking
        if( logpunktExists(logpunkt) )
            throw new DAOException("Logpunkt with ID "+logpunkt.getId()+" already exists in database");

        if( !new EtapeDAO(context).etapeExists(etape) )
            throw new DAOException("Etape with ID "+etape.getId()+" doesn't exist in database");

        // Create row for databse
        SQLiteDatabase database = connector.getWritableDatabase();
        ContentValues row = new ContentValues();

        row.put( "etape", etape.getId() );
        row.put( "dato", logpunkt.getDate().getTime() );
        row.put( "dato_opret", logpunkt.getDate().getTime() );
        row.put( "breddegrad", logpunkt.getPosition() != null ? logpunkt.getPosition().getBreddegrad() : 0 );
        row.put( "laengdegrad", logpunkt.getPosition() != null ? logpunkt.getPosition().getLaengdegrad() : 0 );
        row.put( "note", logpunkt.getNote() );
        row.put( "vindretning", logpunkt.getVindretning() );
        row.put( "vindhastighed", logpunkt.getVindhastighed());
        row.put( "sejlfoering", logpunkt.getSejlfoering() );
        row.put( "mandOverBord", logpunkt.getMandOverBord() );
        row.put( "sejlstilling", logpunkt.getSejlstilling() );
        row.put( "stroemretning", logpunkt.getStroemRetning() );
        row.put( "stroemhastighed", logpunkt.getStroemhastighed());

        /* Only add these elements if they've been manually set ( != 1 ) */
        if( logpunkt.getRoere() >= 0 )
            row.put( "roere", logpunkt.getRoere() );

        if( logpunkt.getKurs() >= 0 )
            row.put( "kurs", logpunkt.getKurs() );

        if( logpunkt.getHals() >= 0 )
            row.put( "hals", logpunkt.getHals() );

        long id = database.insert("logpunkter", null, row);
        logpunkt.setId(id);
        logpunkt.setEtapeId(etape.getId());
        logpunkt.setTogtId(etape.getTogtId());

        logpunktUpdated(logpunkt);
    }


    /**
     * Loads all logpunkter from the database for the given Etape.
     *
     * @param etape The Etape to fetch the Logpunkter for
     * @return A list of Logpunkt. The list is empty if none exists for the Etape
     */
    public List<Logpunkt> getLogpunkter(Etape etape ){

        // Check if etape exists
        if( !new EtapeDAO(context ).etapeExists(etape))
            throw new DAOException("Etape with ID "+ etape.getId()+" doesn't exist in database");

        // Fetch from database
        SQLiteDatabase database = connector.getReadableDatabase();
        List<Logpunkt> logpunkter = new LinkedList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM logpunkter WHERE etape="+etape.getId()+";", null);

        // Create logpunkter
        while( cursor.moveToNext() ){
            Logpunkt logpunkt = new Logpunkt( new Date( cursor.getLong(cursor.getColumnIndex("dato"))));

            logpunkt.setId( cursor.getInt( cursor.getColumnIndex("id")));
            logpunkt.setEtapeId(etape.getId());
            logpunkt.setTogtId(etape.getTogtId());
            logpunkt.setCreationDate( new Date( cursor.getLong(cursor.getColumnIndex("dato_opret"))) );

            // Getting position
            double laengde = cursor.getDouble(cursor.getColumnIndex("laengdegrad"));
            double bredde = cursor.getDouble(cursor.getColumnIndex("breddegrad"));
            logpunkt.setPosition( laengde != 0 && bredde != 0 ? new Position(bredde, laengde) : null );

            logpunkt.setVindretning( cursor.getString( cursor.getColumnIndex("vindretning") ));
            logpunkt.setVindhastighed( cursor.getInt( cursor.getColumnIndex("vindhastighed")));
            logpunkt.setStroemRetning( cursor.getString( cursor.getColumnIndex("stroemretning") ));
            logpunkt.setStroemhastighed( cursor.getInt( cursor.getColumnIndex("stroemhastighed")));
            logpunkt.setSejlfoering( cursor.getString( cursor.getColumnIndex("sejlfoering") ));
            logpunkt.setSejlstilling( cursor.getString( cursor.getColumnIndex("sejlstilling") ));
            logpunkt.setNote( cursor.getString( cursor.getColumnIndex("note") ));
            logpunkt.setMandOverBord( cursor.getInt(cursor.getColumnIndex("mandOverBord")) != 0 );

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
     * Deletes a Logpunkt from the database
     *
     * @param logpunkt The Logpunkt to delete from the database (compares the ID with id in database)
     * @throws DAOException If the Logpunkt doesn't exist in the database
     */
    public void deleteLogpunkt(Logpunkt logpunkt) throws DAOException {
        if( !logpunktExists(logpunkt) )
            throw new DAOException(String.format("Couldn't find Logpunkt with ID %d in the database", logpunkt.getId()));
        SQLiteDatabase database = connector.getReadableDatabase();
        database.delete("logpunkter", "id="+logpunkt.getId(), null);
        logpunktUpdated(logpunkt);
    }



    public void updateLogpunkt(Logpunkt logpunkt) throws DAOException {
        if( !logpunktExists(logpunkt) )
            throw new DAOException(String.format("Couldn't find Logpunkt with ID %d in the database", logpunkt.getId()));

        // Create cursor for database rows
        SQLiteDatabase database = connector.getReadableDatabase();

        // Create row for databse
        ContentValues row = new ContentValues();

        row.put( "dato", logpunkt.getDate().getTime() );
        row.put( "dato_opret", logpunkt.getDate().getTime() );
        row.put( "breddegrad", logpunkt.getPosition() != null ? logpunkt.getPosition().getBreddegrad() : 0 );
        row.put( "laengdegrad", logpunkt.getPosition() != null ? logpunkt.getPosition().getLaengdegrad() : 0 );
        row.put( "note", logpunkt.getNote() );
        row.put( "vindretning", logpunkt.getVindretning() );
        row.put( "vindhastighed", logpunkt.getVindhastighed());
        row.put( "sejlfoering", logpunkt.getSejlfoering() );
        row.put( "mandOverBord", logpunkt.getMandOverBord() );
        row.put( "sejlstilling", logpunkt.getSejlstilling() );
        row.put( "stroemretning", logpunkt.getStroemRetning() );
        row.put( "stroemhastighed", logpunkt.getStroemhastighed());

        /* Only add these elements if they've been manually set ( != -1 ) */
        if( logpunkt.getRoere() >= 0 )
            row.put( "roere", logpunkt.getRoere() );

        if( logpunkt.getKurs() >= 0 )
            row.put( "kurs", logpunkt.getKurs() );

        if( logpunkt.getHals() >= 0 )
            row.put( "hals", logpunkt.getHals() );

        database.update("logpunkter", row, "id="+logpunkt.getId(), null );


        logpunktUpdated(logpunkt);
    }



    public void logpunktUpdated(Logpunkt logpunkt){
        Etape etape = new EtapeDAO(context).getEtape(logpunkt.getEtapeId());
        TogtDAO togtDAO = new TogtDAO(context);
        togtDAO.togtUpdated(etape.getTogtId());
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
