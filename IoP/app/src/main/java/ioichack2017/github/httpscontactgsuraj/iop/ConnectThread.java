package ioichack2017.github.httpscontactgsuraj.iop;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static ioichack2017.github.httpscontactgsuraj.iop.BluetoothPairing.mBluetoothAdapter;

class ConnectThread extends Thread {
    private boolean BYPASS = true;
    private String TAG = "CONNECT THREAD";
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private BluetoothPairing bp;
    //private static final UUID MY_UUID = UUID.fromString("636F3F8F-6491-4BEE-95F7-D8CC64A863B5");
    //private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //636F3F8F-6491-4BEE-95F7-D8CC64A863B5
    //private Handler mHandler; // handler that gets info from Bluetooth service

    public ConnectThread(BluetoothDevice device, BluetoothPairing clasc_c) {
        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        bp = clasc_c;
        BluetoothSocket tmp = null;
        mmDevice = device;

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
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it otherwise slows down the connection.
        mBluetoothAdapter.cancelDiscovery();
        if (BYPASS) {
            bp.connectionSuccess();
            return;
        }

        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            mmSocket.connect();
            bp.connectionSuccess();

        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        try {
            manageMyConnectedSocket(mmSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void manageMyConnectedSocket(BluetoothSocket mmSocket) throws IOException, InterruptedException {
        // wait 500ms
        // Poll HELLO
        // Read message
        // Record it and store it.
        //while (true) {
        Thread.sleep(500);
        byte[] b = {72, 69, 76, 76, 79};
        mmSocket.getOutputStream().write(b);
        //}
        //System.out.println("OUTPUT");
        //byte[] mmBuffer = new byte[1024];
        //int num = mmSocket.getInputStream().read(mmBuffer);
        //System.out.println(num);
        //String str1 = new String(mmBuffer);
        //System.out.println("str1 >> " + str1);

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
