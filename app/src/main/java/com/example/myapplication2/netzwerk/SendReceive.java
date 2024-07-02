package com.example.myapplication2.netzwerk;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SendReceive extends Thread {

    private final BluetoothSocket bluetoothSocket;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private boolean running = true;
    private BluetoothHandler bluetoothHandler;
    private byte[] messageReceived;

    public SendReceive(BluetoothSocket socket, BluetoothHandler bluetoothHandler) {
        this.bluetoothSocket = socket;
        this.bluetoothHandler = bluetoothHandler;
        InputStream tempIn = null;
        OutputStream tempOut = null;

        try {
            tempIn = bluetoothSocket.getInputStream();
            tempOut = bluetoothSocket.getOutputStream();
        } catch (IOException e) {

        }

        inputStream = tempIn;
        outputStream = tempOut;

        if (this.bluetoothHandler == null) {

        } else {
            this.bluetoothHandler.setSendReceive(this);
        }
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        int bytes;

        while (running) {
            try {
                bytes = inputStream.read(buffer);
                if (bytes != -1) {
                    byte[] msg = new byte[bytes];
                    System.arraycopy(buffer, 0, msg, 0, bytes);
                    messageReceived = msg;
                }
            } catch (IOException e) {

            }
        }
    }

    public void write(byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
        }
    }

    public void setRunning(boolean b) {
        running = b;
    }

    public void cancel() {
        running = false;
        try {
            if (bluetoothSocket != null) bluetoothSocket.close();
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
        } catch (IOException e) {
        }
    }

    public boolean getRunning() {
        return running;
    }

    public byte[] getMessageReceived() {
        return messageReceived;
    }

    public void clearMessageReceived() {
        messageReceived = null;
    }

    public void setMessageReceived(byte[] testData) {
        messageReceived = testData;
    }
}

