package com.example.myapplication2.View.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication2.R;
import com.example.myapplication2.SpielcontrollerSingleton;
import com.example.myapplication2.View.bluetooth.BluetoothActivity;
import com.example.myapplication2.View.spiel.Spielactivity;

public class FirstFragment extends Fragment {
    private Button buttonEinzelspieler;
    private Button btnBluetoothLobby;

    private Button espressoTestbutton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        buttonEinzelspieler = view.findViewById(R.id.einzelspielerButton);
        buttonEinzelspieler.setOnClickListener(this::openEinzelspielergame);

        btnBluetoothLobby = view.findViewById(R.id.mehrspielerButton);
        btnBluetoothLobby.setOnClickListener(this::openBluetoothLobby);


        Log.d("zweiteAnmeldung", "FirstFragment");

        return view;
    }


    public void openEinzelspielergame(View view) {

        // Create an instance of the game controller


        try {
            // Create Intent for Spielactivity
            Intent intent = new Intent(getContext(), Spielactivity.class);

            // Setze den Boolean-Wert als Extra im Intent
            intent.putExtra("mehrspieler", false); // Hier den gewünschten Boolean-Wert einsetzen

            // Starte die Spielactivity mit dem Intent
            startActivity(intent);
        }catch (Exception e){
            Log.d("zweiteAnmeldung", "Spielactivity start " + e);

        }


    }

    public void openBluetoothLobby(View view) {
        Log.d("zweiteAnmeldung", "FirstFragment openBluetoothLobby");

        // Starte die BluetoothActivity, die dann das BluetoothFragment anzeigt
        startActivity(new Intent(getContext(), BluetoothActivity.class));
    }

}