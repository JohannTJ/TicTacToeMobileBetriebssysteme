package com.example.myapplication2.netzwerk;


import android.widget.ArrayAdapter;

import com.example.myapplication2.View.bluetooth.BluetoothFragment;
import com.example.myapplication2.control.SpielcontrollerImpl;

/**
 * Interface dient als Schnittstelle für andere Komponenten
 */
public interface BluetoothHandler {
    /**
     * gibt eine Liste an Geräten zurück, mit denen sich das Gerät in der Vergangenheit verbunden hat
     * @param bluetoothFragment BluetoothFragment --> wird für PermissionChecks benötigt
     * @return ArrayAdapter<String>--> Liste der Geräte
     */
    ArrayAdapter<String> handleDevicesButton(BluetoothFragment bluetoothFragment);
    /**
     * Versucht eine Verbindung mit einem Gerät im ListView herzustellen
     * @param i int --> Index, mit welchem Gerät der Liste eine Verbindung hergestellt werden soll
     * @param bluetoothFragment BluetoothFragment --> wird für PermissionChecks gebraucht
     * @return String --> Status der Verbindung
     */
    String listViewClick(int i, BluetoothFragment bluetoothFragment);
    /**
     * schicke Nachricht an Gegner
     * @param messageBytes Nachricht in Byteform
     */
    void sendMessage(byte[] messageBytes);
    /**
     * unterbricht die Bluetooth-Verbindung
     */
    void stoppConnection();
    /**
     * Definiere running neu --> wichtig, damit SendReceive nicht ununterbrochen nach Nachrichten scannt
     * @param b boolean --> ob App nach Nachrichten scannen soll
     */
    void setRunning(boolean b);
    /**
     * Definiert sendReceive neu
     * @param sendReceive SendReceive
     */
    void setSendReceive(SendReceive sendReceive);
    /**
     * Prüft so lange ob eine Nachricht erhalten wurde, bis Nachricht empfangen und zurückgegeben
     * return byte[] --> Message
     */
    byte[] messageReceived();
    /**
     * Überprüft, ob Gerät die Bluetootheinstellung eingeschalten, ausgeschalten oder ob diese vorhanden ist
     * @return String --> Nachricht über Ergebnis der Prüfung
     */
    String checkBTState();
    /**
     * gibt boolean running zurück
     * @return boolean
     */
    boolean getRunning();
}
