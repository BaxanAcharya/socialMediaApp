package com.biplav.socialmedia;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class NavMore {
    @Rule
    public ActivityTestRule<DashboardActivity>
            testRule = new ActivityTestRule<>(DashboardActivity.class);

    @Test
    public void NavMore(){

        onView(withId(R.id.nav_more))
                .perform(click());
    }
}
