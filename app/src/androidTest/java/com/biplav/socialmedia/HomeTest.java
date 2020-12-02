package com.biplav.socialmedia;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class HomeTest {
    @Rule
    public ActivityTestRule<DashboardActivity>
            testRule = new ActivityTestRule<>(DashboardActivity.class);

    @Before
    public void setFragment(){
        testRule.getActivity().getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void AddPost(){
        onView(withId(R.id.etPost))
                .perform(typeText("fgdgd"));
        onView(withId(R.id.btnPost))
                .perform(click());
    }
}
