package ioichack2017.github.httpscontactgsuraj.iop;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;

/**
 * Created by root on 05/02/17.
 */

public class PillowSocket {
    private BluetoothSocket bluetoothSocket;

    public PillowSocket() {
        bluetoothSocket = null;
    }

    public void setBluetoothSocket(BluetoothSocket bluetoothSocket) {
        this.bluetoothSocket = bluetoothSocket;
        notifyAll();
    }

    private synchronized byte[] getDataFromSocket(String message) throws java.lang.InterruptedException, IOException{
        while(bluetoothSocket == null ) {
            wait();
        }
        byte[] bMessage = message.getBytes();
        bluetoothSocket.getOutputStream().write(bMessage);
        byte[] buff = new byte[1024];
        int len = bluetoothSocket.getInputStream().read(buff);
        byte[] res = new byte[len];
        System.arraycopy(buff, 0, res, 0, len);
        return res;
    }

    public boolean sleepingDetected() throws java.lang.InterruptedException, IOException{
        byte[] pillowData = getDataFromSocket("IS_SLEEPING");
        return pillowData[0] != 0;
    }
}
