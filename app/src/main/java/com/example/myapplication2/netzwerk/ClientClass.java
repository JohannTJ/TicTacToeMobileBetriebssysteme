package com.example.myapplication2.netzwerk;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Message;
import android.os.strictmode.ExplicitGcViolation;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.myapplication2.View.bluetooth.BluetoothFragment;
import com.example.myapplication2.control.Spielcontroller;

import java.io.IOException;
import java.util.UUID;

/**
 * Klasse implementiert einen Bluetooth-Client und versucht eine Verbindung herzustellen
 */
public class ClientClass extends Thread {

    // default UUID to create the global variable
    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothFragment bluetoothFragment;
    private SendReceive sendReceive;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private String statusMsg;
    private BluetoothHandler bluetoothHandler;

    /**
     * Konstruktor
     * @param device1
     */
    public ClientClass(BluetoothDevice device1, BluetoothFragment bluetoothFragment, BluetoothHandler bluetoothHandler) {
        try {
            device = device1;
            this.bluetoothFragment = bluetoothFragment;
            this.bluetoothHandler = bluetoothHandler;

            try {
                if (ActivityCompat.checkSelfPermission(bluetoothFragment.getContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= 31) {
                        ActivityCompat.requestPermissions(bluetoothFragment.getActivity(), new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 100);
                        return;
                    }
                }
                socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            Log.e("zweiteAnmeldung","Clientclass : " + e);

        }


    }


    /**
     * wird ausgeführt, wenn ClientClass als Thread gestartet wird
     */
    public void run() {
        try {
            try {
                if (ActivityCompat.checkSelfPermission(bluetoothFragment.getContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= 31) {
                        ActivityCompat.requestPermissions(bluetoothFragment.getActivity(), new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 100);
                        return;
                    }
                }
                socket.connect();

                statusMsg = "Connected";

                sendReceive = new SendReceive(socket,bluetoothHandler);
                sendReceive.start();
                bluetoothHandler.setSendReceive(sendReceive);


            } catch (IOException e) {

                statusMsg = "Connection Failed";

            }
        }catch (Exception e){
            Log.e("zweiteAnmeldung","run ClientClass : " + e);

        }

    }

    /**
     * gibt sendReceive zurück
     * @return Sendreceive
     */
    public SendReceive getSendReceive(){
        return sendReceive;
    }



    public String getStatusMsg(){
        return statusMsg;
    }

    public void clearStatusMsg(){
        statusMsg = null;
    }


    public void cancel() {
        try {
            if (sendReceive != null) {
                sendReceive.cancel();
                sendReceive.join();  // Warten bis der Thread tatsächlich beendet ist
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

