package com.example.universityquizapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<mainScreenActivity> activityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.universityquizapp", appContext.getPackageName());
    }

    @Test
    public void enter_username() {
        onView(withId(R.id.usernameText)).perform(typeText("zaky"));
    }

    @Test
    public void enter_password() {
        onView(withId(R.id.passwordText)).perform(typeText("password"));
    }

    @Test
    public void enter_confirm_password() {
        onView(withId(R.id.passwordConfirmText)).perform(typeText("password"));
    }

    @Test
    public void register() {
        onView(withId(R.id.usernameText)).perform(typeText("zaky123"));
        onView(withId(R.id.passwordText)).perform(typeText("password"));
        onView(withId(R.id.passwordConfirmText)).perform(typeText("password"));
        //onView(withId(R.id.registerBtn)).perform(click());
        //onView(withId(R.id.highScoreTxt)).check(matches(withText("High Score: 0")));
    }
}
