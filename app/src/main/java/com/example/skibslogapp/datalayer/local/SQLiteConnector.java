package com.example.skibslogapp.datalayer.local;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class is the SQLite database definition used as the
 * local storage for Logbog informations (Togt, Etape, Logpunkt).
 *
 * CHANGING STUFF:
 * If you change the database schema (tables or their columns) you have to increment
 * the version number of the database (VERSION). This will delete the database if it
 * already exists on the device when running the app again, and recreate it
 * using the new updated schema.
 *
 * TEST MODE:
 * Running the static method enableTestMode(true) will make consecutive objects
 * of the SQLiteConnector use a test database rather than the actual database.
 * The method also deletes the test database.
 *
 * DEBUGGING:
 * You can access the database via command line using 'adb':
 *      1. Open a Command Line Interpreter (Command Prompt, PowerShell Bash etc.)
 *      2. Check connected devices:
 *              adb devices
 *      3. Connect to device (note: can't make it work on actual device, only emulator):
 *              adb -s SERIALNUMBER shell   (i.e. 'adb -s emulator-5540 shell')
 *      4  Run as project:
 *              run-as com.example.skibslogapp
 *      4. Open database:
 *              sqlite3 databases/logbog.db
 *      5. You're now in the sqlite3 CLI and can use sql command i.e.
 *              SELECT * FROM etaper;
 *          and commands specific for the CLI:
 *              .tables
 */
public class SQLiteConnector extends SQLiteOpenHelper {

    // Increment version number if you change anything
    private static final int VERSION = 11;

    // Name of database
    private static final String DATABASE ="logbog.db";

    // Name of the TEST database
    private static final String TEST_DATABASE = "test_logbog.db";

    private static String usedName = DATABASE;

    /**
     * Enables the use of test database when creating new SQLiteConnectors.
     * Calling it also resets the current test database.
     */
    public static void enableTestMode(boolean enable, Context context){
        if(enable){
            usedName = TEST_DATABASE;
            context.deleteDatabase(TEST_DATABASE);
        }else{
            usedName = DATABASE;
        }
    }


    public SQLiteConnector(Context context) {
        super(context, usedName, null, VERSION);

    }

    /** This method is called when the database is created (i.e. if it didn't exist when
        creating the SQLiteConnector), and it defines the tables of the database */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Togt Table
        db.execSQL(
            "CREATE TABLE togter (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "skib TEXT," +
                "startDestination TEXT," +
                "skipper TEXT" +
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
                "besaetning TEXT," +
                "PRIMARY KEY(id)," +
                "FOREIGN KEY(togt) REFERENCES togter(id)"+
            ")"
        );

        // Logpunkt Table
        db.execSQL(
            "CREATE TABLE logpunkter (" +
                "id INTEGER," +
                "etape INTEGER NOT NULL," +
                "dato INTEGER NOT NULL," +
                "dato_opret INTEGER NOT NULL," +
                "breddegrad REAL," +
                "laengdegrad REAL," +
                "note TEXT," +
                "roere INTEGER,"+
                "kurs INTEGER,"+
                "vindretning TEXT,"+
                "vindhastighed INTEGER,"+
                "sejlfoering TEXT,"+
                "sejlstilling TEXT,"+
                "stroemretning TEXT,"+
                "stroemhastighed INTEGER,"+
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

