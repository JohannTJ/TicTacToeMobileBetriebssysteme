package com.example.myapplication2.netzwerk;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.ArrayAdapter;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication2.View.bluetooth.BluetoothFragment;
import com.example.myapplication2.control.Spielcontroller;
import com.example.myapplication2.control.SpielcontrollerImpl;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Klasse implementiert BluetoothHandler und dient als Verbindungsknoten für diese Komponente
 */
public class BluetoothHandlerImpl implements BluetoothHandler {


    private BluetoothDevice[] btArray;
    private BluetoothAdapter bluetoothAdapter;
    private SendReceive sendReceive;
    private ScheduledExecutorService executorStatusChange;
    private ScheduledExecutorService executorMessageReceived;
    private byte[] message;
    private ServerClass serverClass;
    private ClientClass clientClass;
    private String statusResult;



    /**
     * Konstruktor
     */
    public BluetoothHandlerImpl() {

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBTState();
    }


    /**
     * NUR FÜR TESTS BENÖTIGT
     * --> getDefaultAdapter wirft Exception
     * @param bluetoothAdapter
     */
    public BluetoothHandlerImpl(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
        checkBTState();
    }

    @Override
    public ArrayAdapter<String> handleDevicesButton(BluetoothFragment bluetoothFragment) {
        try {
            serverClass = new ServerClass(bluetoothFragment, bluetoothAdapter, this);
            serverClass.start();

            sendReceive = serverClass.getSendReceive();
        }catch (Exception e){
            Log.e("zweiteAnmeldung","handleDevicesButton" + e);

        }


        return ShowPaired_device(bluetoothFragment);

    }


    @Override
    public String listViewClick(int i, BluetoothFragment bluetoothFragment) {
        try {

            clientClass = new ClientClass(btArray[i], bluetoothFragment, this);
            clientClass.start();
        } catch (Exception e) {
            Log.e("zweiteAnmeldung", "listViewClick: " + e);
        }
        String statusChange = getStatusChange();
        return statusChange;
    }


    private String getStatusChange() {
        if (executorStatusChange == null || executorStatusChange.isShutdown()) {
            executorStatusChange = Executors.newScheduledThreadPool(1);
        }

        // Erstelle eine CountDownLatch mit einer Anzahl von 1
        CountDownLatch latch = new CountDownLatch(1);

        try {

            executorStatusChange.scheduleAtFixedRate(() -> {

                // Rückgabe des Byte-Arrays der Nachricht
                String message = checkingForStatusChanges();
                if (message != null) {
                    clientClass.clearStatusMsg();
                    // Setze das Ergebnis der Statusänderung
                    statusResult = message;
                    // Zähler der CountDownLatch herabsetzen
                    latch.countDown();
                    // Den Executor beenden
                    executorStatusChange.shutdown();
                }
            }, 0, 1, TimeUnit.SECONDS);

            // Warten, bis eine Statusänderung stattgefunden hat oder das Timeout abgelaufen ist
            latch.await();

        } catch (Exception e) {
        }

        // Rückgabe des Ergebnisses der Statusänderung
        return statusResult;
    }


    private String checkingForStatusChanges() {

        return clientClass.getStatusMsg();
    }

    @Override
    public void sendMessage(byte[] messageBytes) {
        try {
            sendReceive.write(messageBytes);
        }catch (Exception e){

        }
    }


    @Override
    @SuppressLint("MissingPermission")
    public void stoppConnection() {
        try {
            if (sendReceive != null) {
                sendReceive.cancel();
            }
            if (serverClass != null) {
                serverClass.cancel();
            }
            if (clientClass != null) {
                clientClass.cancel();
            }
        } catch (Exception e) {
        } finally {
            sendReceive = null;
            serverClass = null;
            clientClass = null;
        }
    }


    @Override
    public void setRunning(boolean b) {
        try {
            serverClass.getSendReceive().setRunning(b);
            clientClass.getSendReceive().setRunning(b);
            this.sendReceive.setRunning(b);

        }catch (Exception e){

        }
    }

    @Override
    public void setSendReceive(SendReceive sendReceive) {
        this.sendReceive = sendReceive;
    }

    @Override
    public byte[] messageReceived() {
        byte[] bytes =  monitorForNewMessages();
        return bytes;
    }

    private byte[] monitorForNewMessages() {

        if (executorMessageReceived == null || executorMessageReceived.isShutdown()) {
            executorMessageReceived = Executors.newScheduledThreadPool(1);
        }
        CountDownLatch latch = new CountDownLatch(1);

        try {
            executorMessageReceived.scheduleAtFixedRate(() -> {

                byte[] messageReceived = checkingForMessages();
                if (messageReceived != null) {
                    sendReceive.clearMessageReceived();
                    message = messageReceived;
                    latch.countDown();
                    executorMessageReceived.shutdown();
                }

            }, 0, 1, TimeUnit.SECONDS);
            // Warten, bis eine Statusänderung stattgefunden hat oder das Timeout abgelaufen ist
            latch.await();
        } catch (Exception e) {
        }

        return message;
    }

    private byte[] checkingForMessages() {
        return sendReceive.getMessageReceived();
    }



    @Override
    public String checkBTState() {

        if (bluetoothAdapter == null) {
            return "1";
        } else {
            if (bluetoothAdapter.isEnabled()) {
                return "2";

            } else {
                return "3";
            }
        }
    }

    @Override
    public boolean getRunning() {
        return sendReceive.getRunning();
    }


    /**
     * zeigt alle gekoppelten Bluetooth-Geräte in einer Liste an
     */
    private ArrayAdapter<String> ShowPaired_device(BluetoothFragment bluetoothFragment) {
        if (ContextCompat.checkSelfPermission(bluetoothFragment.getContext(), android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= 31) {
                ActivityCompat.requestPermissions(bluetoothFragment.getActivity(), new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 100);
                return null;
            }
        }
        ArrayAdapter<String> arrayAdapter = null;
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        String[] strings = new String[pairedDevices.size()];
        String[] strings2 = new String[pairedDevices.size()];
        btArray = new BluetoothDevice[pairedDevices.size()];
        int index = 0;
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                btArray[index] = device;
                strings[index] = device.getName();
                strings2[index] = device.getAddress();
                index++;
            }
            arrayAdapter = new ArrayAdapter<String>(bluetoothFragment.getContext().getApplicationContext(), android.R.layout.simple_list_item_1, strings);
        }
        return arrayAdapter;
    }




    // für Tests
    public ServerClass getServerClass(){
        return serverClass;
    }
    public ClientClass getClientClass(){
        return clientClass;
    }
    public SendReceive getSendReceive(){
        return sendReceive;
    }

    public void setServerClass(ServerClass newServerClass){
        this.serverClass = newServerClass;
    }


    public void setClientClass(ClientClass newClientClass){
        this.clientClass = newClientClass;
    }

}
