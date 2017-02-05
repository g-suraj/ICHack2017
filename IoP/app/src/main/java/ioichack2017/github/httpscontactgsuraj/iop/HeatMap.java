package ioichack2017.github.httpscontactgsuraj.iop;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

public class HeatMap extends AppCompatActivity {
    private Handler handler = new Handler();
    private int flipper = 0;
    private int denum = 1;
    private int[] base = new int[8];
    private int redHigh = 255;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Insert custom code here

            try {
                PillowSocket ps = PillowSocket.getInstance();
                PillowState pstate = ps.getPillowState();
                boolean arr[] = pstate.getState();

                TextView t = (TextView) findViewById(R.id.hu1);
                // Starts with (1 - (base[0]/denum)
                if (arr[0]) {
                    base[0]++;
                }  else {
                    base[0]--;
                }
                int g = 255 - base[0];
                if (g < 0)
                    g = 0;
                if (g > 255)
                    g = 255;
                t.setBackgroundColor(Color.rgb(redHigh, g, g));

                //if (arr[1]) {
                //    base[1]++;
                //}
                //t = (TextView) findViewById(R.id.hu2);
                //t.setBackgroundColor(Color.parseColor("#" + String.valueOf((redHigh)*(denum/base[1]))));

                //if (arr[2]) {
                //    base[2]++;
                //}
                //t = (TextView) findViewById(R.id.hu3);
                //t.setBackgroundColor(Color.parseColor("#" + String.valueOf((redHigh)*(denum/base[2]))));

                //if (arr[3]) {
                //    base[3]++;
                //}
                //t = (TextView) findViewById(R.id.hu4);
                //t.setBackgroundColor(Color.parseColor("#" + String.valueOf((redHigh)*(denum/base[3]))));

                //if (arr[4]) {
                //    base[4]++;
                //}
                //t = (TextView) findViewById(R.id.hd1);
                //t.setBackgroundColor(Color.parseColor("#" + String.valueOf((redHigh)*(denum/base[4]))));

                //if (arr[5]) {
                //    base[5]++;
                //}
                //t = (TextView) findViewById(R.id.hd2);
                //t.setBackgroundColor(Color.parseColor("#" + String.valueOf((redHigh)*(denum/base[5]))));

                //if (arr[6]) {
                //    base[6]++;
                //}
                //t = (TextView) findViewById(R.id.hd3);
                //t.setBackgroundColor(Color.parseColor("#" + String.valueOf((redHigh)*(denum/base[6]))));

                //if (arr[7]) {
                //    base[7]++;
                //}
                //t = (TextView) findViewById(R.id.hd4);
                //t.setBackgroundColor(Color.parseColor("#" + String.valueOf((redHigh)*(denum/base[7]))));

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Repeat every 1 seconds
            handler.postDelayed(runnable, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map);
    }


    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < base.length; i++) {
            base[i] = 0;
        }
        handler.post(runnable);
    }
}
