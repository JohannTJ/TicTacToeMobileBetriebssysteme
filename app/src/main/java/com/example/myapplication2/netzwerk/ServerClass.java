package com.example.myapplication2.netzwerk;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Message;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.myapplication2.View.bluetooth.BluetoothFragment;
import com.example.myapplication2.control.Spielcontroller;

import java.io.IOException;
import java.util.UUID;


/**
 * Klasse implementiert Bluetooth-Server und wartet auf eingehende Verbindungen
 * public wegen Tests
 */
public class ServerClass extends Thread {


    // default UUID to create the global variable
    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static final String TAG = "bluetooth1";

    private BluetoothServerSocket serverSocket;
    private SendReceive sendReceive;
    private BluetoothHandler bluetoothHandler;





    /**
     * Konstruktor
     */
    public ServerClass(BluetoothFragment bluetoothFragment, BluetoothAdapter BA, BluetoothHandler bluetoothHandler){

        try {
            this.bluetoothHandler = bluetoothHandler;

            try {
                if (ActivityCompat.checkSelfPermission(bluetoothFragment.getContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= 34) {
                        ActivityCompat.requestPermissions(bluetoothFragment.getActivity(), new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 100);
                        return;
                    }
                }

                // Erstellung BluetoothServerSocket
                serverSocket = BA.listenUsingRfcommWithServiceRecord(TAG, MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e ){
            Log.e("zweiteAnmeldung","ServerClass : " + e);
        }

    }

    /**
     * wird gestartet, wenn ServerClass als Thread gestartet wird
     */
    public void run() {
        try {
            BluetoothSocket socket = null;

            // Schleife, die auf Client wartet
            while (socket == null) {
                try {

                    socket = serverSocket.accept();

                } catch (IOException e) {
                    e.printStackTrace();

                }

                if (socket != null) {

                    sendReceive = new SendReceive(socket, bluetoothHandler);
                    sendReceive.start();
                    bluetoothHandler.setSendReceive(sendReceive);

                    break;
                }
            }
        }catch (Exception e){
            Log.e("zweiteAnmeldung","run ServerClass : " + e);

        }

    }

    /**
     * gibt sendReceive zurück
     * @return Sendreceive
     */
    public SendReceive getSendReceive(){
        return sendReceive;
    }

    public void cancel() {
        try {
            if (sendReceive != null) {
                sendReceive.cancel();
                sendReceive.join();  // Warten bis der Thread tatsächlich beendet ist
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}