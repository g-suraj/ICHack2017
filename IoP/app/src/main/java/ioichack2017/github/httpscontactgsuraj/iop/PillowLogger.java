package ioichack2017.github.httpscontactgsuraj.iop;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.os.SystemClock;

/**
 * Created by root on 05/02/17.
 */

public class PillowLogger extends Thread {
    private PillowStateDbHelper pillowStateDbHelper;

    public PillowLogger(Context ctx) {
        this.pillowStateDbHelper = new PillowStateDbHelper(ctx);
    }

    public void run() {
        while(true) {
            SystemClock.sleep(10000);
            try {
                PillowState state = PillowSocket.getInstance().getPillowState();
                log(state);
            } catch (Exception e) {
                //Ignore if a log goes wrong
            }

        }
    }

    public static class PillowStateEntry implements BaseColumns {
        public static final String TABLE_NAME = "PillowState";
        public static final String U1 = "U1";
        public static final String U2 = "U2";
        public static final String U3 = "U3";
        public static final String U4 = "U4";
        public static final String D1 = "D1";
        public static final String D2 = "D2";
        public static final String D3 = "D3";
        public static final String D4 = "D4";
        public static final String TIME_STAMP = "timeStamp";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PillowStateEntry.TABLE_NAME + " (" +
                    PillowStateEntry._ID + " INTEGER PRIMARY KEY," +
                    PillowStateEntry.U1 + " TEXT," +
                    PillowStateEntry.U2 + " TEXT," +
                    PillowStateEntry.U3 + " TEXT," +
                    PillowStateEntry.U4 + " TEXT," +
                    PillowStateEntry.D1 + " TEXT," +
                    PillowStateEntry.D2 + " TEXT," +
                    PillowStateEntry.D3 + " TEXT," +
                    PillowStateEntry.D4 + " TEXT," +
                    PillowStateEntry.TIME_STAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PillowStateEntry.TABLE_NAME;

    private class PillowStateDbHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "PillowState.db";

        public PillowStateDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }


    private void log(PillowState pillowState) {
        // Gets the data repository in write mode
        SQLiteDatabase db = pillowStateDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        boolean[] state = pillowState.getState();
        values.put(PillowStateEntry.U1, state[0]);
        values.put(PillowStateEntry.U2, state[1]);
        values.put(PillowStateEntry.U3, state[2]);
        values.put(PillowStateEntry.U4, state[3]);
        values.put(PillowStateEntry.D1, state[4]);
        values.put(PillowStateEntry.D2, state[5]);
        values.put(PillowStateEntry.D3, state[6]);
        values.put(PillowStateEntry.D4, state[7]);
        values.put(PillowStateEntry.TIME_STAMP, " time('now') ");

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(PillowStateEntry.TABLE_NAME, null, values);
    }

}
