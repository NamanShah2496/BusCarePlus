package ca.codingcomrades.it.buscareplus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HelpActivityTest {
    @Rule
    public IntentsTestRule<HelpActivity> intentsTestRule =
            new IntentsTestRule<>(HelpActivity.class);

    @Test
    public void activityResult_DisplaysContactsPhoneNumber() {
        // Build the result to return when the activity is launched.
        Intent resultData = new Intent();
        String phoneNumber = "123-456-7890";
        resultData.putExtra("phone", phoneNumber);
        ActivityResult result =
                new ActivityResult(Activity.RESULT_OK, resultData);

        // Set up result stubbing when an intent sent to "contacts" is seen.
        intending(toPackage("ca.codingcomrades.it.buscareplus.HelpActivity")).respondWith(result);
        onView(withId(R.id.call_fab)).perform(click());

    }


}
