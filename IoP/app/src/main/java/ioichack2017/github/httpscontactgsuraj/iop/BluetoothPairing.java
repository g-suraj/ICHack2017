package ioichack2017.github.httpscontactgsuraj.iop;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


public class BluetoothPairing extends AppCompatActivity {

    public BluetoothDevice pillow;
    private int REQUEST_ENABLE_BT = 1;
    private ListView listView;
    private ArrayList<String> mDeviceList = new ArrayList<String>();
    private static final UUID MY_UUID = UUID.fromString("0000110E-0000-1000-8000-00805F9B34FB");


    //BT
    public static BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket mSocket;
    private static Context mContext;


    //the name of the device is Pillow
    //standardised password
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_pairing);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mContext = BluetoothPairing.this;

        // Do something in response to button
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
            builder1.setMessage("Your device does not support bluetooth.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        } else {
            // If bluetooth is turned off
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        boolean pairSuccess = false;
        for (BluetoothDevice device : pairedDevices) {
            String deviceName = device.getName();
            String deviceAddress = device.getAddress();
            if (deviceName.equals("raspberrypi")) {
                pairSuccess = true;
                pillow = device;
            }
        }
        if (mBluetoothAdapter.isEnabled()) {

        if (pairSuccess) {
            pairSuccess();
        } else {
            pairFailure();
        }

        }

    }


    private void pairSuccess() {
        //CHECK IF PILLOW IS CONNECTED
        if (pillow.getBondState() == pillow.BOND_BONDED) {
            Log.d("TAG ??",pillow.getName());
        }
        try {
            mSocket = pillow.createInsecureRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            Log.d("TAG","socket not created");
            e1.printStackTrace();
        }
        try{
            mSocket.connect();
        }
        catch(IOException e){
            try {
                mSocket.close();
                Log.d("TAG","Cannot connect");
            } catch (IOException e1) {
                Log.d("TAG","Socket not closed");
                e1.printStackTrace();
            }
        }
        //Thread runThread  = new Thread(new Runnable() {

        //    @Override
        //    public void run() {
        //        // Always cancel discovery because it will slow down a connection
        //        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();

        //        // Make a connection to the BluetoothSocket
        //        try {
        //            // This is a blocking call and will only return on a
        //            // successful connection or an exception
        //            mSocket.connect();
        //        } catch (IOException e) {
        //            //connection to device failed so close the socket
        //            try {
        //                mSocket.close();
        //            } catch (IOException e2) {
        //                e2.printStackTrace();
        //            }
        //        }
        //  }
        //});
    }

    private void pairFailure() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(BluetoothPairing.this);
        builder1.setMessage("Please pair this device in settings before proceeding.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent myIntent = new Intent(BluetoothPairing.this, LoginScreen.class);
                        BluetoothPairing.this.startActivity(myIntent);
                    }

                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void processFailure(DialogInterface dialog) {
    }

    private void connectionSuccess() {
    }
}
