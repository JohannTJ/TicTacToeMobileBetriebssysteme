package com.example.myapplication2;


import com.example.myapplication2.control.SpielcontrollerImpl;

public class SpielcontrollerSingleton {
    private static SpielcontrollerImpl instance;

    private SpielcontrollerSingleton() {
        // private constructor to prevent instantiation
    }

    public static SpielcontrollerImpl getInstance() {
        if (instance == null) {
            instance = new SpielcontrollerImpl();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;  // to reset the controller if needed
    }
}
