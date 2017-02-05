package ioichack2017.github.httpscontactgsuraj.iop;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;

/**
 * Created by root on 05/02/17.
 */

public class PillowSocket {
    private BluetoothSocket bluetoothSocket;
    private static PillowSocket instance = null;

    protected PillowSocket() {
        bluetoothSocket = null;
    }

    public static PillowSocket getInstance() {
        if(instance == null) {
            instance = new PillowSocket();
        }
        return instance;
    }

    public synchronized void setBluetoothSocket(BluetoothSocket bluetoothSocket) {
        this.bluetoothSocket = bluetoothSocket;
        notifyAll();
    }

    private synchronized byte[] getDataFromSocket(String message) throws java.lang.InterruptedException, IOException {
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

    public boolean sleepingDetected() throws java.lang.InterruptedException, IOException {
        byte[] pillowData = getDataFromSocket("IS_SLEEPING");
        return pillowData[0] != (int) '0';
    }

    public PillowState getPillowState() throws java.lang.InterruptedException, IOException {
        byte[] pillowData = getDataFromSocket("HELLO");
        boolean[] state = new boolean[8];
        for(int i = 0; i < 8; i++) {
            state[i] = pillowData[i] != 0;
        }
        return new PillowState(state);
    }
}
