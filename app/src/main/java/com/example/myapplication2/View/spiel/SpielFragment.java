package com.example.myapplication2.View.spiel;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication2.R;
import com.example.myapplication2.SpielcontrollerSingleton;
import com.example.myapplication2.View.main.MainActivity;
import com.example.myapplication2.control.Spielcontroller;
import com.example.myapplication2.control.SpielcontrollerImpl;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpielFragment} factory method to
 * create an instance of this fragment.
 */
public class SpielFragment extends Fragment {


    /**
     * Spielbuttons, die vom Spieler gedrückt
     */
    private Button [] spielButton = new Button[9];

    /**
     * spiellogik --> Control
     */
    private Spielcontroller spielcontroller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spiel, container, false);

        Log.d("zweiteAnmeldung", "SpielFragment onCreate");

        try {
            // Get the singleton instance of SpielcontrollerImpl
            Log.d("zweiteAnmeldung", "SpielFragment spielcontroller: " + spielcontroller);

            Log.d("zweiteAnmeldung", "Test1");

            spielcontroller.setMyturn(true);
            spielcontroller.setSpielFragment(this);

            Log.d("zweiteAnmeldung", "Test2");

            // Set the fragment instance to the controller
            spielcontroller.setSpielFragment(this);
        } catch (Exception e) {
            Log.d("zweiteAnmeldung", "SpielFragment Exception: " + e);
        }

        Log.d("zweiteAnmeldung", "Test3");

        return view;
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spielButton[0] = view.findViewById(R.id.spielbutton1);
        spielButton[1] = view.findViewById(R.id.spielbutton2);
        spielButton[2] = view.findViewById(R.id.spielbutton3);
        spielButton[3] = view.findViewById(R.id.spielbutton4);
        spielButton[4] = view.findViewById(R.id.spielbutton5);
        spielButton[5] = view.findViewById(R.id.spielbutton6);
        spielButton[6] = view.findViewById(R.id.spielbutton7);
        spielButton[7] = view.findViewById(R.id.spielbutton8);
        spielButton[8] = view.findViewById(R.id.spielbutton9);


        spielButton[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spielcontroller.handleSpielbuttonOnClickMethode(0);
            }
        });


        spielButton[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spielcontroller.handleSpielbuttonOnClickMethode(1);

            }
        });

        spielButton[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spielcontroller.handleSpielbuttonOnClickMethode(2);

            }
        });

        spielButton[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spielcontroller.handleSpielbuttonOnClickMethode(3);

            }
        });

        spielButton[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spielcontroller.handleSpielbuttonOnClickMethode(4);

            }
        });

        spielButton[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spielcontroller.handleSpielbuttonOnClickMethode(5);

            }
        });

        spielButton[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spielcontroller.handleSpielbuttonOnClickMethode(6);

            }
        });

        spielButton[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spielcontroller.handleSpielbuttonOnClickMethode(7);

            }
        });

        spielButton[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spielcontroller.handleSpielbuttonOnClickMethode(8);

            }
        });

        Button abbruch = view.findViewById(R.id.abbruch_button);
        abbruch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    spielcontroller.handleAbbruchbuttonOnClickMethode();

                }catch (Exception e){
                    Log.d("AbbruchProblem","Exception AbbruchOnClick: " +e);
                }
            }
        });
    }

    public void paintButton(int buttonNumber, int color) {
        switch (buttonNumber){
            case 0: spielButton[0].setBackgroundColor(color);
                break;
            case 1: spielButton[1].setBackgroundColor(color);
                break;
            case 2: spielButton[2].setBackgroundColor(color);
                break;
            case 3: spielButton[3].setBackgroundColor(color);
                break;
            case 4: spielButton[4].setBackgroundColor(color);
                break;
            case 5: spielButton[5].setBackgroundColor(color);
                break;
            case 6: spielButton[6].setBackgroundColor(color);
                break;
            case 7: spielButton[7].setBackgroundColor(color);
                break;
            case 8: spielButton[8].setBackgroundColor(color);
                break;
        }
    }


    public void showToast(){

        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Dein Gegner hat aufgegeben!", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void changeToFirstFragment() {

        try {
            // Create Intent for Spielactivity
            Intent intent = new Intent(getContext(), MainActivity.class);

            // Starte die Spielactivity mit dem Intent
            startActivity(intent);
        }catch (Exception e) {
            Log.d("zweiteAnmeldung", "Mainactivity start " + e);
        }
    }

    public void setSpielcontroller(){
        spielcontroller = SpielcontrollerSingleton.getInstance();

    }

    public void setMehrspieler(boolean mehrspieler) {

        spielcontroller.setMehrspieler(mehrspieler);
    }
}