package ca.codingcomrades.it.buscareplus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

//import android.support.*;

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
        onView(withText(R.string.feedback)).perform(click());
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
