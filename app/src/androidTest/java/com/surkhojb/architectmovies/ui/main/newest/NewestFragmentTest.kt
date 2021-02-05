package com.surkhojb.architectmovies.ui.main.newest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.ui.MainActivity
import com.surkhojb.architectmovies.utils.MockWebServerRule
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

class NewestFragmentTest : KoinTest {
    private val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @After
    fun tearDown(){
        mockWebServerRule.server.shutdown()
    }

    @Test
    fun mainActivityShouldBeOnView(){
        onView(ViewMatchers.withId(R.id.nav_host_fragment_container))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun bottomViewClickNewestIconShouldLoadNewestFragment(){
        onView(ViewMatchers.withId(R.id.menu_newest)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.list_newest))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}