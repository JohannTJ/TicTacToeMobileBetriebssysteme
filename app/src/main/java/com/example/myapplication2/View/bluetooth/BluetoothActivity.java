package com.example.myapplication2.View.bluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.myapplication2.R;

public class BluetoothActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        openBluetoothFragment();
    }

    private void openBluetoothFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Erstelle eine Instanz des BluetoothFragment
        BluetoothFragment bluetoothFragment = new BluetoothFragment();

        // Füge das BluetoothFragment zum Container hinzu
        transaction.replace(R.id.bluetoothactivity, bluetoothFragment);
        transaction.addToBackStack(null);  // Optional, um den Back-Stack zu verwalten
        transaction.commit();
    }
}
