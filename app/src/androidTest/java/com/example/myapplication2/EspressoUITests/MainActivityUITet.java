package com.example.myapplication2.EspressoUITests;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.myapplication2.R;
import com.example.myapplication2.View.bluetooth.BluetoothActivity;
import com.example.myapplication2.View.main.MainActivity;
import com.example.myapplication2.View.spiel.Spielactivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityUITet {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void clickEinzelspielerButton() {
        // Click the Einzelspieler button
        Espresso.onView(withId(R.id.einzelspielerButton)).perform(ViewActions.click());

        // Verify if Spielactivity is opened
        Intents.intended(IntentMatchers.hasComponent(Spielactivity.class.getName()));
    }

    @Test
    public void clickMehrspielerButton() {
        // Click the Bluetooth Lobby button
        Espresso.onView(withId(R.id.mehrspielerButton)).perform(ViewActions.click());

        // Verify if BluetoothActivity is opened
        Intents.intended(IntentMatchers.hasComponent(BluetoothActivity.class.getName()));
    }


}
