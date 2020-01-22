package com.example.skibslogapp.datalayer.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.model.Togt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


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
     * <p>
     * The Togt must already be saved to the database using the {@link TogtDAO#addTogt(Togt)} method.
     *
     * @param togt  The Togt to add the Etape to
     * @param etape The Etape to add to the
     */
    public void addEtape(Togt togt, Etape etape) {
        TogtDAO togtDAO = new TogtDAO(context);

        if (!togtDAO.togtExists(togt))
            throw new DAOException("Togt with ID " + togt.getId() + " doesn't exist in database");

        SQLiteDatabase database = connector.getWritableDatabase();

        ContentValues row = new ContentValues();
        row.put("togt", togt.getId());
        if (etape.getStartDate() != null)
            row.put("startDate", etape.getStartDate().getTime());
        if (etape.getEndDate() != null)
            row.put("endDate", etape.getEndDate().getTime());
        if (etape.getStartDestination() != null)
            row.put("startDestination", etape.getStartDestination());
        if (etape.getSlutDestination() != null)
            row.put("slutDestination", etape.getSlutDestination());
        if (etape.getSkipper() != null)
            row.put("skipper", etape.getSkipper());
        row.put("status", etape.getStatus());
        row.put("besaetning", besaetningToString(etape.getBesaetning()));


        long id = database.insert("etaper", "endDate", row);
        etape.setId(id);
        etape.setTogtId(togt.getId());

        database.close();
        togtDAO.togtUpdated(etape.getTogtId());
    }


    /**
     * Loads all Etape objects from the database for the given Togt.
     *
     * @param togt The Togt to fetch the Logpunkter for
     * @return A list of Etape objects. The list is empty if none exists for the given Togt
     */
    public List<Etape> getEtaper(Togt togt) {

        // Check if Togt exists
        if (!new TogtDAO(context).togtExists(togt))
            throw new DAOException("Togt with ID " + togt.getId() + " doesn't exist in the database");

        SQLiteDatabase database = connector.getReadableDatabase();
        LinkedList<Etape> etaper = new LinkedList<>();

        // Create cursor for database rows
        Cursor cursor = database.rawQuery("SELECT * FROM etaper WHERE togt=" + togt.getId() + ";", null);
        // Load data from each row
        while (cursor.moveToNext()) {
            etaper.add(buildEtape(cursor));
        }
        cursor.close();
        database.close();

        return etaper;
    }

    /**
     * Loads the Etape with the given Etape ID from the database.
     *
     * @param id ID of the Etape in the database (larger than 1)
     * @return New Etape object with information from database
     * @throws DAOException If the Etape with the given ID doesn't exist in the database
     */
    public Etape getEtape(long id) {
        SQLiteDatabase database = connector.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM etaper WHERE id=" + id + ";", null);

        Etape etape = null;
        if (cursor.moveToNext()) {
            etape = buildEtape(cursor);
        } else {
            throw new DAOException(String.format(Locale.US, "Couldn't find Etape with ID %d in the database", id));
        }
        cursor.close();
        database.close();

        return etape;
    }


    /**
     * Build an Etape object from a cursor, which contains points to an Etape
     * row from the database.
     */
    private Etape buildEtape(Cursor cursor) {

        Etape etape = new Etape();
        etape.setId(cursor.getInt(cursor.getColumnIndex("id")));
        etape.setTogtId(cursor.getInt(cursor.getColumnIndex("togt")));

        int column;

        column = cursor.getColumnIndex("startDate");
        if (!cursor.isNull(column)) {
            etape.setStartDate(new Date(cursor.getLong(column)));
        }

        column = cursor.getColumnIndex("endDate");
        if (!cursor.isNull(column)) {
            etape.setEndDate(new Date(cursor.getLong(column)));
        }

        column = cursor.getColumnIndex("startDestination");
        if (!cursor.isNull(column)) {
            etape.setStartDestination(cursor.getString(column));
        }

        column = cursor.getColumnIndex("slutDestination");
        if (!cursor.isNull(column)) {
            etape.setSlutDestination(cursor.getString(column));
        }

        column = cursor.getColumnIndex("skipper");
        if (!cursor.isNull(column)) {
            etape.setSkipper(cursor.getString(column));
        }

        column = cursor.getColumnIndex("status");
        if (!cursor.isNull(column)) {
            // Converting integer value to boolean value
            etape.setStatus(cursor.getInt(column));
        }

        etape.setBesaetning(besaetningToList(cursor.getString(cursor.getColumnIndex("besaetning"))));

        return etape;
    }

    /**
     * Update an Etape already added to the database, to values
     * of the given object's fields.
     * The Etape must already have been saved to the database using
     * the {@link EtapeDAO#addEtape(Togt, Etape)} method.
     *
     * @param etape Etape to update
     */
    public void updateEtape(Etape etape) {

        if (!etapeExists(etape))
            throw new DAOException("Etape with ID " + etape.getId() + " doesn't exist in database");

        // Create cursor for database rows
        SQLiteDatabase database = connector.getReadableDatabase();

        ContentValues row = new ContentValues();
        if (etape.getStartDate() != null)
            row.put("startDate", etape.getStartDate().getTime());
        if (etape.getEndDate() != null)
            row.put("endDate", etape.getEndDate().getTime());
        if (etape.getStartDestination() != null)
            row.put("startDestination", etape.getStartDestination());
        if (etape.getSlutDestination() != null)
            row.put("slutDestination", etape.getSlutDestination());
        if (etape.getSkipper() != null)
            row.put("skipper", etape.getSkipper());
        row.put("status", etape.getStatus());

        row.put("besaetning", besaetningToString(etape.getBesaetning()));

        database.update("etaper", row, "id=" + etape.getId(), null);
        database.close();

        new TogtDAO(context).togtUpdated(etape.getTogtId());
    }


    /**
     * Deletes an Etape from the database, including all the Logpunkter for that
     * etape.
     *
     * @param etape Etape to delete (compares ID with ID in the database)
     * @throws DAOException if the Etape doesn't exist in the database
     */
    public void deleteEtape(Etape etape) throws DAOException {

        // Check if etape exist
        if (!etapeExists(etape))
            throw new DAOException(String.format("Couldn't find Etape with ID %d in the database", etape.getId()));

        // Deletes logpunkter for the Etape
        LogpunktDAO logpunktDAO = new LogpunktDAO(context);
        for (Logpunkt logpunkt : logpunktDAO.getLogpunkter(etape)) {
            logpunktDAO.deleteLogpunkt(logpunkt);
        }

        // Delete Etape
        SQLiteDatabase database = connector.getReadableDatabase();
        database.delete("etaper", "id=" + etape.getId(), null);
        database.close();

        new TogtDAO(context).togtUpdated(etape.getTogtId());
    }


    /**
     * Checks if the given Etape exists in the database
     * based on the etape ID.
     *
     * @param etape The etape to check if exists in the database
     * @return True if it exists, false if not
     */
    public boolean etapeExists(Etape etape) {
        if (etape.getId() == -1) return false;

        SQLiteDatabase database = connector.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM etaper WHERE id=" + etape.getId() + ";", null);
        int rowCount = cursor.getCount();
        cursor.close();
        database.close();
        return rowCount > 0;
    }


    // Besaetnings conversion -------------------------------------------------
    // Methods are public for testing

    public static final String BESAETNING_SEPERATOR = ";";

    /**
     * This function converts a "Besaetnings" list to
     *
     * @param besaetning The
     * @return
     */
    public String besaetningToString(List<String> besaetning) {
        StringBuilder besaetningString = new StringBuilder();
        boolean isFirst = true;
        for (String navn : besaetning) {
            if (isFirst){
                isFirst = false;
            } else{
                besaetningString.append(BESAETNING_SEPERATOR);
            }
            besaetningString.append(navn);
        }
        return besaetningString.toString();
    }

    /**
     *
     *
     * @param besaetningString
     * @return
     */
    public List<String> besaetningToList(String besaetningString) {
        String[] besaetningUnsorted = besaetningString.split(BESAETNING_SEPERATOR);
        List<String> besaetningSorted = new ArrayList<>();

        for (String navn : besaetningUnsorted) {
            if (navn.length() > 0) {
                besaetningSorted.add(navn);
            }
        }
        return besaetningSorted;
    }
}
