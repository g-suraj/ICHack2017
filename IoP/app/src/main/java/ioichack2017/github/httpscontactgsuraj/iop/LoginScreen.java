package ioichack2017.github.httpscontactgsuraj.iop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
    }

    /** Called when the user clicks the Sign In button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent myIntent = new Intent(LoginScreen.this, BluetoothPairing.class);
        LoginScreen.this.startActivity(myIntent);
    }

}
