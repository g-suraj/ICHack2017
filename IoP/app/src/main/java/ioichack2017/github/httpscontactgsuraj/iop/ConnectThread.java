package ioichack2017.github.httpscontactgsuraj.iop;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import static ioichack2017.github.httpscontactgsuraj.iop.BluetoothPairing.mBluetoothAdapter;

class ConnectThread extends Thread {
    private String TAG = "CONNECT THREAD";
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    //private static final UUID MY_UUID = UUID.fromString("636F3F8F-6491-4BEE-95F7-D8CC64A863B5");
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //636F3F8F-6491-4BEE-95F7-D8CC64A863B5

    public ConnectThread(BluetoothDevice device) {
        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        BluetoothSocket tmp = null;
        mmDevice = device;

        //try {
        // Get a BluetoothSocket to connect with the given BluetoothDevice.
        // MY_UUID is the app's UUID string, also used in the server code.
        //tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        try {
            Method m = mmDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
            try {
                tmp = (BluetoothSocket) m.invoke(mmDevice, 3);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        //} catch (IOException e) {
        //    Log.e(TAG, "Socket's create() method failed", e);
        //}
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it otherwise slows down the connection.
        mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            mmSocket.connect();
            System.out.println("DEBUGGGGGG: Connection");

        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            System.out.println(connectException);
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        //manageMyConnectedSocket(mmSocket);
    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the client socket", e);
        }
    }
}
