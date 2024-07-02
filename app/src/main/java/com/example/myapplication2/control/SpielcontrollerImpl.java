package com.example.myapplication2.control;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;


import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication2.R;
import com.example.myapplication2.SpielcontrollerSingleton;
import com.example.myapplication2.View.bluetooth.BluetoothFragment;
import com.example.myapplication2.View.main.FirstFragment;
import com.example.myapplication2.View.main.MainActivity;
import com.example.myapplication2.netzwerk.BluetoothHandler;
import com.example.myapplication2.netzwerk.BluetoothHandlerImpl;
import com.example.myapplication2.View.spiel.SpielFragment;
import com.example.myapplication2.model.TicTacToeModel;
import com.example.myapplication2.model.TicTacToeModelImpl;

import java.nio.ByteBuffer;

/**
 * Klasse implement Interface Spielcontoller und dient als Controller im MVC-Pattern
 */
public class SpielcontrollerImpl implements Spielcontroller {

    private BluetoothHandler bluetoothHandler;
    private TicTacToeModel daten;
    private SpielFragment spielFragment;
    private BluetoothFragment bluetoothFragment;


    /**
     * Konstruktor
     */
    public SpielcontrollerImpl(){
        this.daten = new TicTacToeModelImpl();
    }


    /**
     * NUR FÜR TESTS RELEVANT!!!
     */
    public SpielcontrollerImpl(SpielFragment spielFragment, TicTacToeModel daten){
        this.daten = daten;
        this.spielFragment = spielFragment;
    }


    /**
     * prüft, ob Spiel entschieden ist --> setzt entschieden im Zweifelsfall neu
     * @param spieler Spieler --> welcher Spieler den Zug gemacht hat
     */
    private void pruefeGamestate(boolean spieler) {
        if (spieler){
            if (daten.pruefeGewinn()) {
                paintAll(Color.RED);
                if (daten.getMehrspieler()){
                    stopSearchingForMessages();
                }
            }
        }

        else {
            if (daten.pruefeGewinn()){
                paintAll(Color.BLUE);
                daten.setEntschieden(true);
                if (daten.getMehrspieler()){
                    stopSearchingForMessages();
                }

            }
            if (daten.pruefeUnendschieden()){
                paintAll(Color.BLACK);
                daten.setEntschieden(true);
                if (daten.getMehrspieler()){
                    stopSearchingForMessages();
                }
            }
        }
    }

    private void stopSearchingForMessages() {
        bluetoothHandler.setRunning(false);
    }

    /**
     * befielt dem Spielfragment, dass alle Buttons eine bestimmte Farbe haben müssen
     * @param color Farbe, in der die Buttons sein sollen
     */
    private void paintAll( int color){
        for (int i = 0; i < 9; i++) {
            spielFragment.paintButton(i, color);
        }
    }



    @Override
    public void handleSpielbuttonOnClickMethode(int buttonNumber) {


        if (daten.getMyturn()){
            if (!daten.getMehrspieler() && daten.getButtonStatus(buttonNumber) == 0){
                try {
                    // Einzelspieler

                    daten.setButton(buttonNumber, 1);

                    spielFragment.paintButton(buttonNumber, Color.BLUE);

                    pruefeGamestate(false);

                    if (!daten.getEntschieden()){
                        int computerZug = daten.naechstesFeld();
                        daten.setButton(computerZug, 2);

                        spielFragment.paintButton(computerZug, Color.RED);
                        pruefeGamestate(true);
                    }
                }catch (Exception e){

                }

            }
            else if (daten.getButtonStatus(buttonNumber) == 0){

                //Mehrspieler
                daten.setMyturn(false);
                daten.setButton(buttonNumber, 1);
                spielFragment.paintButton(buttonNumber, Color.BLUE);

                //Gegner benachrichtigen
                sendMessageToOpponent(buttonNumber);

                pruefeGamestate(false);

            }
        }
    }

    @Override
    public void sendMessageToOpponent(int message) {

        //bluetoothHandler.setRunning(true);


        byte[] messageBytes = ByteBuffer.allocate(4).putInt(message).array();
        // Schicke das byte-Array über den BluetoothHandler

        bluetoothHandler.sendMessage(messageBytes);

    }

    @Override
    public void setMehrspieler(boolean mehrspieler) {
        daten.setMehrspieler(mehrspieler);

        if (mehrspieler){
            try {
                lookForMessages();

            }catch (Exception e){
                Log.d("zweiteAnmeldung", "setMehrspieler Exception" + e);

            }
        }
    }



    private void lookForMessages() {

        new Thread(() -> {
            while (bluetoothHandler.getRunning()) {
                byte[] receivedMsg = bluetoothHandler.messageReceived();
                if (receivedMsg != null) {
                    handleBluetoothDaten(receivedMsg);
                }
                try {
                    Thread.sleep(100); // Überprüfung alle 0.1 Sekunde
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void setMyturn(boolean myturn) {
        daten.setMyturn(myturn);
    }

    @Override
    public void handleBluetoothDaten(byte[] msg) {

        try {

            // Überprüfen, ob das Byte-Array mindestens 4 Bytes lang ist
            if (msg.length >= 4) {

                // Erstelle ein ByteBuffer mit dem Byte-Array als Datenquelle
                ByteBuffer buffer = ByteBuffer.wrap(msg);
                int intValue = buffer.getInt();

                System.out.println(intValue);

                if (intValue == 20){
                    handleSurrender();
                }else {
                    lookForMessages();
                    daten.setButton(intValue, 2);
                    spielFragment.paintButton(intValue, Color.RED);
                    pruefeGamestate(true);
                    setMyturn(true);
                }

            }
        }catch (Exception e){
        }

    }

    private void handleSurrender() {
        try {
            // Spiel zurücksetzen oder zur Hauptseite navigieren
            spielFragment.showToast();

            bluetoothHandler.stoppConnection();
            bluetoothHandler = null;

            SpielcontrollerSingleton.resetInstance();
            spielFragment.changeToFirstFragment();
        }catch (Exception e){
            Log.d("zweiteAnmeldung","handleSurrender " + e);

        }

    }

    @Override
    public void handleAbbruchbuttonOnClickMethode() throws InterruptedException {
        if (daten.getMehrspieler()){
            byte[] byteArray = ByteBuffer.allocate(4).putInt(20).array();
            bluetoothHandler.sendMessage(byteArray);

            Thread.sleep(1000);

            bluetoothHandler.stoppConnection();
            bluetoothHandler = null;
        }
        spielFragment.changeToFirstFragment();
        SpielcontrollerSingleton.resetInstance();

    }

    @Override
    public void setbluetoothHandler(BluetoothHandlerImpl bluetoothHandler) {

        this.bluetoothHandler = bluetoothHandler;

    }

    /**
     * gibt BluetoothHandler zurück
     * @return BluetoothHandler zurück
     */
    public BluetoothHandler getBluetoothHandler() {
        return bluetoothHandler;
    }

    @Override
    public void setSpielFragment(SpielFragment spielFragment) {
        this.spielFragment = spielFragment;
    }

    @Override
    public ArrayAdapter<String> handleDevicesButton() {
        bluetoothFragment.setStatus("Devices you connected earlier");
        return bluetoothHandler.handleDevicesButton(bluetoothFragment);
    }

    @Override
    public void listViewClick(int i) {

        try {
            new Thread(() -> {
                try {

                    bluetoothFragment.setStatus("Connecting");
                    String statusMessage = bluetoothHandler.listViewClick(i, bluetoothFragment);


                    // Handle the UI update on the main thread
                    bluetoothFragment.getActivity().runOnUiThread(() -> {
                        if (statusMessage == null) {
                            bluetoothFragment.setStatus("Fehler");
                        } else {
                            switch (statusMessage) {
                                case "Connected":
                                    Log.d("zweiteAnmeldung","Thread switch");

                                    bluetoothFragment.switchToSpielActivityWithHandler();

                                    break;
                                case "Connection Failed":
                                    bluetoothFragment.setStatus("Connection Failed");
                                    break;
                                default:
                                    bluetoothFragment.setStatus("Fehler");
                                    break;
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.d("zweiteAnmeldung","Exception: " + e);
                }
            }).start(); // Start the Thread
        }catch (Exception e){
            Log.d("zweiteAnmeldung","Thread Exception: " + e);

        }

    }

    @Override
    public void setBluetoothFragment(BluetoothFragment bluetoothFragment) {
        this.bluetoothFragment = bluetoothFragment;
    }

    @Override
    public void checkBTState() {
        String bluetoothStatus = null;

        try {
            bluetoothStatus = bluetoothHandler.checkBTState();

        }catch (Exception e){
        }


        switch (bluetoothStatus){
            case "1": bluetoothFragment.errorExit("Error","Bluetooth not support");break;
            case "2": bluetoothFragment.showToast("...Bluetooth is ON...");break;
            default: bluetoothFragment.showToast("...Please Turn Bluetooth ON...");break;

        }
    }

}
