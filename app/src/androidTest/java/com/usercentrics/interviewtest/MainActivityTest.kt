package com.usercentrics.interviewtest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.usercentrics.interviewtest.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testTextViewsDisplayed() {
        // Check if TextViews are displayed
        onView(withId(R.id.tvCalculatedResult)).check(matches(isDisplayed()))
        onView(withText(R.string.consent_score)).check(matches(isDisplayed()))
    }

    @Test
    fun testButtonDisplayed() {
        // Check if Button is displayed
        onView(withId(R.id.btnCalculateCentricsScore)).check(matches(isDisplayed()))
    }

    @Test
    fun testButtonText() {
        // Check if Button text is correct
        onView(withId(R.id.btnCalculateCentricsScore)).check(matches(withText(R.string.show_content_banner)))
    }

    @Test
    fun testButtonClick() {
        // Perform click on the Button
        onView(withId(R.id.btnCalculateCentricsScore)).perform(click())
        onView(withId(R.id.tvCalculatedResult))
            .check(matches(isDisplayed()))
    }
}
