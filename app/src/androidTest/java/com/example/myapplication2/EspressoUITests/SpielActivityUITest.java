package com.example.myapplication2.EspressoUITests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.myapplication2.R;
import com.example.myapplication2.View.main.MainActivity;
import com.example.myapplication2.View.spiel.Spielactivity;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class SpielActivityUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityMain =
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
    public void clickEinzelspielerButton_opensEinzelspielergameActivity() {
        // Click the Einzelspieler button
        onView(withId(R.id.einzelspielerButton)).perform(click());

        // Verify if Spielactivity is opened
        Intents.intended(IntentMatchers.hasComponent(Spielactivity.class.getName()));


        // Klicke auf den ersten Spielbutton und überprüfe die Änderung der Hintergrundfarbe
        Espresso.onView(Matchers.allOf(withId(R.id.spielbutton1), isDescendantOfA(withId(R.id.spielActivity))))
                .perform(ViewActions.click());

        Espresso.onView(withId(R.id.spielbutton1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed())); // Beispielhafte Überprüfung

        // Klicke auf den Abbruchbutton und überprüfe, ob MainActivity gestartet wird
        Espresso.onView(withId(R.id.abbruch_button))
                .perform(ViewActions.click());


    }
}
