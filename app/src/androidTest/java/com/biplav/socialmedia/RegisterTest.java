package com.biplav.socialmedia;

import androidx.test.espresso.ViewAction;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class RegisterTest {
    @Rule
    public ActivityTestRule<RegisterActivity>
            testRule = new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void Register()
    {
        onView(withId(R.id.etfname))
                .perform(typeText("fgdgd"));
        onView(withId(R.id.etlname))
                .perform(typeText("sfgsdgf"));
        onView(withId(R.id.etemailRegister))
                .perform(typeText("jgjh@gmail.com"));
        onView(withId(R.id.etpasswordRegister))
                .perform(typeText("sjkdfhsdhfjdsf"));


    }

}
