package com.muhammedsafiulazam.photoalbum.feature.basket

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.core.BaseUITest
import com.muhammedsafiulazam.photoalbum.core.IAfterWait
import com.muhammedsafiulazam.photoalbum.core.IBeforeWait
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.feature.basket.event.BasketEventType
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Muhammed Safiul Azam on 29/07/2019.
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class BasketUITest : BaseUITest() {

    @Rule @JvmField
    var mActivityTestRule: ActivityTestRule<BasketActivity> = ActivityTestRule(BasketActivity::class.java, true, false)

    @Before
    fun beforeTest() {
    }

    @Test
    fun checkSuccess_loadProducts() {
        wait(BasketEventType.UPDATE_DATA, object : IBeforeWait {
            override fun beforeWait() {
                val intent = Intent(getContext(), BasketActivity::class.java)
                mActivityTestRule.launchActivity(intent)

                onView(withId(R.id.basket_pgb_loader)).check(matches(isDisplayed()))
            }

        }, object : IAfterWait {
            override fun afterWait(events: List<Event>) {

                onView(withId(R.id.basket_pgb_loader)).check(matches(not(isDisplayed())))
            }
        })
    }

    @After
    fun afterTest() {

    }
}