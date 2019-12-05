package com.example.skibslogapp.datalayer.local;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * This class is the SQLite database definition used as the
 * local storage for Logbog informations (Togt, Etape, Logpunkt).
 *
 * CHANGING STUFF:
 * If you database schema (tables or their columns) you have to increment
 * the version number of the database (VERSION). This will DROP the database if it
 * already exists on the device when running the app again, and recreate it
 * using the new updated schema.
 *
 * DEBUGGING:
 * You can access the database via command line using 'adb':
 *      1. Open a Command Line (Command Prompt, Bash etc.)
 *      2. Check connected devices:
 *              adb devices
 *      3. Connect to device (note: can't make it work on actual device, only emulator):
 *              adb -s SERIALNUMBER shell (i.e. adb -s emulator-5540)
 *      4. cd into:
 *              /data/data/com.example.skibslogapp/databases
 *      5. Open database:
 *              sqlite3 logbog.db
 *      6. You're now in the sqlite3 CLI and can use sql command i.e.
 *              SELECT * FROM etaper;
 *          and commands specific for the CLI:
 *              .tables
 */
class SQLiteConnector extends SQLiteOpenHelper {

    // Increment version number if you change anything
    private static final int VERSION = 6;

    // Name of database
    private static final String DATABASE ="logbog.db";


    public SQLiteConnector(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    /** This method is called when the database is created (i.e. if it didn't exist when
        creating the SQLiteConnector), and it defines the tables of the database */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Togt Table
        db.execSQL(
            "CREATE TABLE togter (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL" +
            ")"
        );

        // Etape Table
        /* We're storing date in long (integer in sql) as UNIX-time, because it's more efficient
            and takes up less storage. Also, the string to date formatting in used Data API in the
            Java code is depcrecated. */
        db.execSQL(
            "CREATE TABLE etaper (" +
                "id INTEGER," +
                "togt INTEGER," +
                "startDate INTEGER NOT NULL," +
                "endDate INTEGER," +
                "PRIMARY KEY(id)," +
                "FOREIGN KEY(togt) REFERENCES togter(id)"+
            ")"
        );

        // Logpunkt Table
        db.execSQL(
            "CREATE TABLE logpunkter (" +
                "id INTEGER," +
                "etape INTEGER," +
                "date INTEGER NOT NULL," +
                "note TEXT," +
                "roere INTEGER,"+
                "kurs INTEGER,"+
                "vindretning TEXT,"+
                "sejlfoering TEXT,"+
                "sejlstilling TEXT,"+
                "hals BIT,"+
                "mandOverBord BIT,"+
                "PRIMARY KEY(id)," +
                "FOREIGN KEY(etape) REFERENCES etaper(id)"+
            ")"
        );
    }


    /**
     * This method runs if the version number of the current database
     * on the device doesn't match the static field version number 'VERSION'.
     * Esentially, it recreates the database (dropping ALL entries).
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Todo: set this upgrade function up properly, at the momment it does not have much use.
        db.execSQL("DROP TABLE togter");
        db.execSQL("DROP TABLE etaper");
        db.execSQL("DROP TABLE logpunkter");
        this.onCreate(db);
    }
}

