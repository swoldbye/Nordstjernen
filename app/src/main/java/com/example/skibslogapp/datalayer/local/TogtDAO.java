package com.example.skibslogapp.datalayer.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.model.Togt;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class TogtDAO {

    // Connector to the database
    private SQLiteConnector connector;
    private Context context;

    private static LinkedList<TogtObserver> togtObservers = new LinkedList<>();

    public TogtDAO(Context context) {
        connector = new SQLiteConnector(context);
        this.context = context;
    }

    /**
     * Add a Togt to the database, using the object field values.
     * A new unique ID will be generated for given object,
     * and saved to the 'id' field.
     *
     * @param togt The Togt to save to the database
     */
    public void addTogt(Togt togt) {
        SQLiteDatabase database = connector.getWritableDatabase();

        ContentValues row = new ContentValues();
        row.put("name", togt.getName());

        if (togt.getSkib() != null) row.put("skib", togt.getSkib());
        if (togt.getSkipper() != null) row.put("skipper", togt.getSkipper());
        if (togt.getStartDestination() != null)
            row.put("startDestination", togt.getStartDestination());

        long id = database.insert("togter", null, row);
        togt.setId(id);
        togtUpdated(id);

        database.close();
    }


    /**
     * Retrieves a Togt based on the ID. Not meant to be used
     * outside of the class.
     */
    public Togt getTogt(long id) {
        SQLiteDatabase database = connector.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM togter WHERE id=" + id + ";", null);

        Togt togt = null;
        while (cursor.moveToNext()) {
            togt = new Togt(cursor.getString(1));
            togt.setId(cursor.getInt(0));
        }

        cursor.close();
        database.close();
        return togt;
    }

    /**
     * Loads all Togt objects from the the local database.
     *
     * @return A list of the loaded Togt objects. The list is empty (not null) if no Togt exists in the database.
     */
    public List<Togt> getTogter() {

        // Create cursor for database rows
        SQLiteDatabase database = connector.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM togter;", null);
        //Cursor cursor = database.query("togter", null, null, null,null, null, null);
        LinkedList<Togt> togter = new LinkedList<>();

        // Load data from each row
        while (cursor.moveToNext()) {
            Togt togt = new Togt(cursor.getString(cursor.getColumnIndex("name")));
            togt.setId(cursor.getInt(cursor.getColumnIndex("id")));

            if (!cursor.isNull(cursor.getColumnIndex("skib")))
                togt.setSkib(cursor.getString(cursor.getColumnIndex("skib")));

            if (!cursor.isNull(cursor.getColumnIndex("skipper")))
                togt.setSkipper(cursor.getString(cursor.getColumnIndex("skipper")));

            if (!cursor.isNull(cursor.getColumnIndex("startDestination")))
                togt.setStartDestination(cursor.getString(cursor.getColumnIndex("startDestination")));
            togter.add(togt);
        }
        cursor.close();

        database.close();
        return togter;
    }

    /**
     * Deletes a Togt from the local database, including all the Etaper and
     * Logpunkter for that Togt.
     *
     * @param togt The Togt to be deleted (the method matches the ID of the Togt object with an ID in the database)
     */
    public void deleteTogt(Togt togt) throws DAOException {
        if (!togtExists(togt)) {
            throw new DAOException(String.format(Locale.US, "Togt with id %d doesn't exist in database", togt.getId()));
        }

        // Deletes Etaper for the Togt
        EtapeDAO etapeDAO = new EtapeDAO(context);
        for (Etape etape : etapeDAO.getEtaper(togt)) {
            etapeDAO.deleteEtape(etape);
        }

        SQLiteDatabase database = connector.getReadableDatabase();
        database.delete("togter", "id=" + togt.getId(), null);

        database.close();
    }


    /**
     * Checks if the given Togt exists in the database
     * based on the togt ID.
     *
     * @param togt The Togt to check if exists in the database
     * @return True if it exists, false if not
     */
    public boolean togtExists(Togt togt) {
        if (togt.getId() == -1) return false;

        SQLiteDatabase database = connector.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM togter WHERE id=" + togt.getId() + ";", null);
        int rowCount = cursor.getCount();
        cursor.close();
        database.close();
        return rowCount > 0;
    }


    // TOGT OBSERVER ----------------------------------------------------------------------------------------------

    /**
     * Add an observer to be notified whenever a togt has been updated. */
    public static void addTogtObserver(TogtObserver observer) {
        togtObservers.add(observer);
    }

    /**
     * Remove the observer from being notified when a Togt is updated */
    public static void removeTogtObserver(TogtObserver observer) {
        togtObservers.remove(observer);
    }

    /**
     * TogtObserver may be notified by the TogtDAO whenever a togt is updated.
     * The Togt parameter is the togt which was updated
     */
    public interface TogtObserver {
        void onUpdate(Togt togt);
    }

    /**
     * Inform that a Togt has been updated, by notifying all TogtObservers
     * @param id The ID of the Togt
     */
    void togtUpdated(long id) {
        Togt togt = getTogt(id);

        if (togt == null)
            throw new DAOException("No Togt with ID " + id + " exists in the database");

        for (TogtObserver observer : togtObservers) {
            observer.onUpdate(togt);
        }
    }


}
