package com.surkhojb.architectmovies.ui.main.top_rated

import android.Manifest
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.surkhojb.architectmovies.R
import com.surkhojb.architectmovies.data.remote.retrofit.MovieDb
import com.surkhojb.architectmovies.ui.MainActivity
import com.surkhojb.architectmovies.utils.MockWebServerRule
import com.surkhojb.architectmovies.utils.fromJson
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.koin.test.KoinTest
import org.koin.test.get

class TopRatedFragmentTest: KoinTest {

    private val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val mRuntimePermissionRule =
        GrantPermissionRule.grant(Manifest.permission.ACCESS_COARSE_LOCATION)

    @get:Rule
    val testRule: RuleChain = RuleChain
        .outerRule(mockWebServerRule)
        .around(
            GrantPermissionRule.grant(
                "android.permission.ACCESS_COARSE_LOCATION"
            )
        )
        .around(ActivityScenarioRule(MainActivity::class.java))

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("test_movies.json"),
        )

        val resource = OkHttp3IdlingResource.create("OkHttp", get<MovieDb>().okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @After
    fun tearDown(){
        mockWebServerRule.server.shutdown()
    }


    @Test
    fun clickATopRatedMovieNavigatesToDetail() {
        //FYI -> Database return movies by id
        onView(withId(R.id.list_top_rated)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                4,
                click()
            )
        )

        onView(withId(R.id.detail_title)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_title)).check(matches(withText("12 Angry Men")))

    }
}