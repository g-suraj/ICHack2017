package ioichack2017.github.httpscontactgsuraj.iop;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class WatchActivity extends AppCompatActivity {
    //private Timer timer;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Insert custom code here

            try {
                PillowSocket ps = PillowSocket.getInstance();
                PillowState pstate = ps.getPillowState();
                boolean arr[] = pstate.getState();

                if (arr[0]) {
                    TextView t = (TextView) findViewById(R.id.u1);
                    t.setBackgroundColor(Color.RED);
                } else {
                    TextView t = (TextView) findViewById(R.id.u1);
                    t.setBackgroundColor(Color.WHITE);
                }

                if (arr[1]) {
                    TextView t = (TextView) findViewById(R.id.u2);
                    t.setBackgroundColor(Color.RED);
                } else {
                    TextView t = (TextView) findViewById(R.id.u2);
                    t.setBackgroundColor(Color.WHITE);
                }

                if (arr[2]) {
                    TextView t = (TextView) findViewById(R.id.u3);
                    t.setBackgroundColor(Color.RED);
                } else {
                    TextView t = (TextView) findViewById(R.id.u3);
                    t.setBackgroundColor(Color.WHITE);
                }

                if (arr[3]) {
                    TextView t = (TextView) findViewById(R.id.u4);
                    t.setBackgroundColor(Color.RED);
                } else {
                    TextView t = (TextView) findViewById(R.id.u4);
                    t.setBackgroundColor(Color.WHITE);
                }

                if (arr[4]) {
                    TextView t = (TextView) findViewById(R.id.d1);
                    t.setBackgroundColor(Color.RED);
                } else {
                    TextView t = (TextView) findViewById(R.id.d1);
                    t.setBackgroundColor(Color.WHITE);
                }

                if (arr[5]) {
                    TextView t = (TextView) findViewById(R.id.d2);
                    t.setBackgroundColor(Color.RED);
                } else {
                    TextView t = (TextView) findViewById(R.id.d2);
                    t.setBackgroundColor(Color.WHITE);
                }

                if (arr[6]) {
                    TextView t = (TextView) findViewById(R.id.d3);
                    t.setBackgroundColor(Color.RED);
                } else {
                    TextView t = (TextView) findViewById(R.id.d3);
                    t.setBackgroundColor(Color.WHITE);
                }

                if (arr[7]) {
                    TextView t = (TextView) findViewById(R.id.d4);
                    t.setBackgroundColor(Color.RED);
                } else {
                    TextView t = (TextView) findViewById(R.id.d4);
                    t.setBackgroundColor(Color.WHITE);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Repeat every 2 seconds
            handler.postDelayed(runnable, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // This timer task will be executed every 1 sec.
        // Create the Handler

        // Define the code block to be executed

        // Start the Runnable immediately
        handler.post(runnable);
    }
}
