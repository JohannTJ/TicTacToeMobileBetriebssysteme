package com.example.myapplication2.EspressoUITests;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myapplication2.R;
import com.example.myapplication2.View.bluetooth.BluetoothActivity;
import com.example.myapplication2.View.main.MainActivity;
import com.example.myapplication2.View.spiel.Spielactivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class BluetoothActivityUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
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
    public void testOldDevicesButton() {

        // Click the Bluetooth Lobby button
        Espresso.onView(withId(R.id.mehrspielerButton)).perform(ViewActions.click());

        // Verify if BluetoothActivity is opened
        Intents.intended(IntentMatchers.hasComponent(BluetoothActivity.class.getName()));

        onView(withId(R.id.OldDevices)).perform(click());

        // Wartezeit für die Ansicht, um vollständig zu laden (optional)
        try {
            Thread.sleep(1000); // Wartezeit, um sicherzustellen, dass die Ansicht vollständig gerendert ist
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Klick auf das erste Element im ListView
        onData(anything())
                .inAdapterView(withId(R.id.lv))
                .atPosition(0)
                .perform(click());


    }

}

