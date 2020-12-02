package com.biplav.socialmedia;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.biplav.socialmedia.strictMode.StrictModeClass;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {
    @Rule
    public ActivityTestRule<MainActivity>
            testRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void checkLogin()
    {
        onView(withId(R.id.editTextUser))
                .perform(typeText(""));
        onView(withId(R.id.editTextPass))
                .perform(typeText(""));
        onView(withId(R.id.textViewLogin))
                .perform(click());
        onView(withId(R.id.nav_home))
                .check((matches(isDisplayed())));

    }


}
