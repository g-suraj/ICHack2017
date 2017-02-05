package ioichack2017.github.httpscontactgsuraj.iop;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.provider.AlarmClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

public class AlarmActivity extends AppCompatActivity {

    private AlarmManager am;
    private PendingIntent pendingIntent;

    /*
        Here we try to integrate an alarm clock with the pillow.
        To check if you are sleeping on the pillow, we ping server with code
        IS_SLEEPING. Here is what I'm trying to accomplish in this class.

        1. Turning off the alarm when IS_SLEEPING, does not turn alarm off.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        //am = new AlarmManager();
        Intent alarmIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, alarmIntent, 0);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: replace with socket monitor for IS_SLEEPING
        //this.startActivity(ACTION_SET_ALARM);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showTimePickerDialog(View v) {
       // AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
       // int interval = 1000 * 60 * 20;

       // TimePickerFragment timeFragment = new TimePickerFragment();
       // DialogFragment newFragment = timeFragment;
       // newFragment.show(getSupportFragmentManager(), "timePicker");
       // Calendar cal = Calendar.getInstance();
       // int hours = timeFragment.getHours();
       // int mins = timeFragment.getMins();
       // cal.setTimeInMillis(System.currentTimeMillis());
       // cal.set(Calendar.HOUR_OF_DAY, 7);
       // cal.set(Calendar.MINUTE, 19);

       // manager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
       //         interval, pendingIntent);
        //am.setAndAllowWhileIdle(am.RTC_WAKEUP, millis,  );
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 20;

        /* Set the alarm to start at 10:30 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 22);

        /* Repeating on every 20 minutes interval */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, pendingIntent);
    }
}
