package com.surkhojb.architectmovies.ui.main.search

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.ui.MainActivity
import com.surkhojb.architectmovies.utils.MockWebServerRule
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

class SearchFragmentTest : KoinTest {
    private val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @After
    fun tearDown(){
        mockWebServerRule.server.shutdown()
    }

    @Test
    fun mainActivityShouldBeOnView(){
        onView(withId(R.id.nav_host_fragment_container))
            .check(matches(isDisplayed()))
    }

    @Test
    fun bottomViewClickNewestIconShouldLoadNewestFragment(){
        onView(withId(R.id.fab)).perform(ViewActions.click())
        onView(withId(R.id.list_search))
            .check(matches(isDisplayed()))

    }

    @Test
    fun afterLoadNewestFragmentSearchViewShowBeVisible(){
        onView(withId(R.id.fab)).perform(ViewActions.click())
        onView(withId(R.id.list_search))
            .check(matches(isDisplayed()))
        onView(withId(R.id.search_view)).check(matches(isDisplayed()))

    }
}