package ioichack2017.github.httpscontactgsuraj.iop;

import android.content.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by root on 05/02/17.
 * Contains a state of the pillow as a boolean array of size 8 with each index represents wether
 * a sensor is held down or not.
 */

public class PillowState {
    public static final int NO_SENSORS = 8;
    private boolean[] state;

    public PillowState(boolean[] state) {
        this.state = state;
    }

    public boolean[] getState() {
        return state;
    }

}
