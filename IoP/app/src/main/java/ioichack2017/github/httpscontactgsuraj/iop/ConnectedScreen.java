package ioichack2017.github.httpscontactgsuraj.iop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

public class ConnectedScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView textView = (TextView) findViewById(R.id.Connection);
        TextView textView2 = (TextView) findViewById(R.id.inquire);
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);

        textView.startAnimation(fadeOut);
        fadeOut.setDuration(2400);
        fadeOut.setFillAfter(true);
        textView2.startAnimation(fadeIn);
        fadeIn.setDuration(2000);
    }


    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(ConnectedScreen.this, LoginScreen.class);
        ConnectedScreen.this.startActivity(myIntent);
    }

}
