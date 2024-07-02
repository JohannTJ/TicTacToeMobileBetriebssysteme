package com.example.myapplication2.control;

import android.widget.ArrayAdapter;

import com.example.myapplication2.View.bluetooth.BluetoothFragment;
import com.example.myapplication2.View.spiel.SpielFragment;
import com.example.myapplication2.netzwerk.BluetoothHandlerImpl;


/**
 * Interface definiert Methoden, die ein Controller für ein TicTacToe-Spiel benötigt
 */
public interface Spielcontroller {
    /**
     * Stoppt die Bluetooth-Verbindung
     */
    void handleAbbruchbuttonOnClickMethode() throws InterruptedException;
    /**
     * Methode implementiert die Anwendungslogik, die nach einem drücken eines Spielbuttons
     * @param buttonNumber int --> Nummer des Spielbuttons
     */
    void handleSpielbuttonOnClickMethode(int buttonNumber);
    /**
     * Methode versendet Nachricht an Gegner per Bluetooth
     * @param message int --> Nachricht an Gegner
     */
    void sendMessageToOpponent(int message);
    /**
     * definiert die Variable mehrspieler in Daten neu
     * @param mehrspieler boolean --> bestimmt, ob Einzelspieler oder Mehrspieler gespielt wird
     */
    void setMehrspieler(boolean mehrspieler);
    /**
     * definiert die Variable myturn in Daten neu
     * @param myturn boolean --> bestimmt, ob Spieler am Zug ist
     */
    void setMyturn(boolean myturn);
    /**
     * handle Message vom Gegner
     * @param msg Nachricht, die per Bluetooth übermittelt wurde
     */
    void handleBluetoothDaten(byte[] msg);
    /**
     * definiert bluetoothHandler neu
     * @param bluetoothHandler
     */
    void setbluetoothHandler(BluetoothHandlerImpl bluetoothHandler);
    /**
     * gibt eine Liste an Geräten zurück
     * @return ArrayAdapter<String>
     */
    ArrayAdapter<String> handleDevicesButton();
    /**
     * versucht sich mit Gerät auf Liste mit Index i zu verbinden
     * @param i
     */
    void listViewClick(int i);
    /**
     * definiert bluetoothFragment neu
     * @param bluetoothFragment
     */
    void setBluetoothFragment(BluetoothFragment bluetoothFragment);
    /**
     * prüft Bluetootheinstellung
     */
    void checkBTState();
    /**
     * definiert SpielFragment neu
     * @param spielFragment
     */
    void setSpielFragment(SpielFragment spielFragment);
}
