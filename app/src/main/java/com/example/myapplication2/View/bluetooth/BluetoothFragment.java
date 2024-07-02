package com.example.myapplication2.View.bluetooth;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.example.myapplication2.SpielcontrollerSingleton;
import com.example.myapplication2.View.spiel.Spielactivity;
import com.example.myapplication2.control.Spielcontroller;
import com.example.myapplication2.control.SpielcontrollerImpl;
import com.example.myapplication2.netzwerk.BluetoothHandler;
import com.example.myapplication2.netzwerk.BluetoothHandlerImpl;


public class BluetoothFragment extends Fragment {

    private Spielcontroller spielcontroller;

    private Button mPairedBtn;
    private TextView status;
    private ListView listView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bluetooth, container, false);

        Log.d("zweiteAnmeldung", "BluetoothFragment ");

        findViewByIdes(view);
        implementListeners();


        spielcontroller = SpielcontrollerSingleton.getInstance();
        spielcontroller.setBluetoothFragment(this);

        Log.d("zweiteAnmeldung", "BluetoothFragment Spielcontroller erstellt ");

        BluetoothHandler bluetoothHandler = new BluetoothHandlerImpl();
        spielcontroller.setbluetoothHandler((BluetoothHandlerImpl) bluetoothHandler);

        Log.d("zweiteAnmeldung", "BluetoothFragment BluetoothHandler erstellt ");

        checkBTState();


        return view;
    }

    private void checkBTState() {
        try {
            spielcontroller.checkBTState();
        }catch (Exception e){
            Log.e("bluetoothWeg", "BluetoothFragment CheckBTState Exception: " + e);
        }
    }


    /**
     * initialisiert Click-Listener
     */
    private void implementListeners() {

        mPairedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setListView(spielcontroller.handleDevicesButton());


            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("zweiteAnmeldung", "BluetoothFragment listViewClick");

                try {
                    spielcontroller.listViewClick(i);
                }catch (Exception e){
                    Log.e("zweiteAnmeldung", "BluetoothFragment listViewClick + " + e);

                }
            }
        });

    }


    /**
     * initialisiert View-Elemente der GUI
     */
    private void findViewByIdes(View view) {
        mPairedBtn = view.findViewById(R.id.OldDevices);
        status= view.findViewById(R.id.status);
        listView= view.findViewById(R.id.lv);
    }


    public void setListView(ArrayAdapter<String> arrayAdapter){
        listView.setAdapter(arrayAdapter);
    }

    /**
     * zeigt Fehlermeldung und beendet Aktivity
     * @param title
     * @param message
     */
    public void errorExit(String title, String message) {
        Toast.makeText(getContext(), title + " - " + message, Toast.LENGTH_LONG).show();
    }

    /**
     * erzeugt Toast-Nachricht
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }


    public void setStatus(String msg){
        status.setText(msg);
    }


    public void switchToSpielActivityWithHandler() {
        Log.d("zweiteAnmeldung", "switchToSpielActivityWithHandler");

        try {
            // Create Intent for Spielactivity
            Intent intent = new Intent(getContext(), Spielactivity.class);

            // Setze den Boolean-Wert als Extra im Intent
            intent.putExtra("mehrspieler", true); // Hier den gewünschten Boolean-Wert einsetzen

            // Starte die Spielactivity mit dem Intent
            startActivity(intent);

        } catch (Exception e) {
            Log.e("zweiteAnmeldung", "BluetoothFragment Exception: " + e);
        }
    }
}