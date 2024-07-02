package com.example.myapplication2.View.spiel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication2.R;
import com.example.myapplication2.control.Spielcontroller;
import com.example.myapplication2.control.SpielcontrollerImpl;

public class Spielactivity extends AppCompatActivity {

    private ViewPager viewPager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spielactivity);
        Log.d("zweiteAnmeldung", "Spielactivity start " );

        // Empfange den Boolean-Wert aus dem Intent
        boolean mehrspieler = getIntent().getBooleanExtra("mehrspieler", false);

        Log.d("zweiteAnmeldung", "Spielactivity mehrspieler: " + mehrspieler);


        openSpielFragment(mehrspieler);
    }

    private void openSpielFragment(boolean mehrspieler) {
        Log.d("zweiteAnmeldung", "openSpielFragment" );
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            Log.d("zweiteAnmeldung", "openSpielFragment FragmentManager" );

            SpielFragment spielFragment = new SpielFragment();
            Log.d("zweiteAnmeldung", "openSpielFragment new SpielFragment()" );


            // Füge das BluetoothFragment zum Container hinzu
            transaction.replace(R.id.spielActivity, spielFragment);
            transaction.addToBackStack(null);  // Optional, um den Back-Stack zu verwalten
            transaction.commit();
            spielFragment.setSpielcontroller();
            spielFragment.setMehrspieler(mehrspieler);
        }catch (Exception e){
            Log.d("zweiteAnmeldung", "openSpielFragment " + e);

        }
    }
}
