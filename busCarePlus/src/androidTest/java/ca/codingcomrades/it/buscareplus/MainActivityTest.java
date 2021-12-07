package ca.codingcomrades.it.buscareplus;

import android.app.Activity;
import android.content.Intent;

import android.app.Instrumentation.ActivityResult;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import static androidx.test.espresso.Espresso.onView;
//import android.support.*;
import android.view.Gravity;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.google.android.material.internal.NavigationMenuItemView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void openHelp() throws InterruptedException {
        // Testing whether the help screen is opened.
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.help)).perform(click());
        Thread.sleep(2000);

    }
    @Test
    public void openReview() throws InterruptedException {
        // Testing whether the review screen is opened.
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.review)).perform(click());
        Thread.sleep(2000);

    }
    @Test
    public void openAboutUs() throws InterruptedException {
        // Testing whether the About Us screen is opened.
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.aboutus)).perform(click());
        Thread.sleep(2000);

    }

    @Test
    public void openMyAccount() throws InterruptedException {
        // Testing whether the MyAccount screen is opened.

        onView(withId(R.id.myaccountImage)).perform(click());
        Thread.sleep(2000);
        // intended(hasComponent(HelpActivity.class.getName()));
    }
    @Test
    public void checkSpinner() throws InterruptedException {
        // Testing whether the Spinner is working or not.
        onView(withId(R.id.busoption)).perform(click());
        Thread.sleep(2000);
        // intended(hasComponent(HelpActivity.class.getName()));
    }




}
