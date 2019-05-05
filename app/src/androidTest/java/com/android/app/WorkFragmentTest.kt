package com.android.app

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.sjsoft.app.R
import com.sjsoft.app.ui.MainActivity
import com.sjsoft.app.ui.shift.work.WorkFragment
import com.sjsoft.app.view.replaceFragmentInActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class WorkFragmentTest {
    @get:Rule var activityTestRule = ActivityTestRule(MainActivity::class.java)

    private var mMainActivity: MainActivity? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mMainActivity = activityTestRule.activity
    }

    //@Test
    fun testLaunch() {
        mMainActivity?.replaceFragmentInActivity(
            R.id.contentFrame,
            WorkFragment()
        )

        Espresso.onView(ViewMatchers.withId(R.id.button_shift))
            .check(ViewAssertions.matches(ViewMatchers.isEnabled()))

        Espresso.onView(ViewMatchers.withId(R.id.button_shift))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.start_shift)))

        Thread.sleep(2000)
        onView(withId(R.id.button_shift)).perform(click())
        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withId(R.id.button_shift))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.end_shift)))

        Thread.sleep(2000)

        onView(withId(R.id.button_shift)).perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.button_shift))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.start_shift)))

    }

    @Test
    fun shiftButtonIsEnabled() {
        val f = WorkFragment()

        mMainActivity?.replaceFragmentInActivity(R.id.contentFrame, f)

        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withId(R.id.button_shift))
            .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
    }

}
