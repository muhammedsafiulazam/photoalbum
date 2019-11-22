package com.muhammedsafiulazam.photoalbum.feature.albumlist

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
import com.muhammedsafiulazam.photoalbum.feature.albumlist.event.AlbumListEventType
import com.muhammedsafiulazam.photoalbum.utils.RecyclerViewAssertion.withItemCount
import org.hamcrest.Matchers.greaterThan
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
class AlbumListUITest : BaseUITest() {

    @Rule @JvmField
    var mActivityTestRule: ActivityTestRule<AlbumListActivity> = ActivityTestRule(AlbumListActivity::class.java, true, false)

    @Before
    fun beforeTest() {
    }

    @Test
    fun checkSuccess_loadAlbums() {
        wait(AlbumListEventType.RESPONSE_LOAD_ALBUMS, object : IBeforeWait {
            override fun beforeWait() {
                val intent = Intent(getContext(), AlbumListActivity::class.java)
                mActivityTestRule.launchActivity(intent)

                onView(withId(R.id.albumlist_pgb_loader)).check(matches(isDisplayed()))
                onView(withId(R.id.albumlist_ryv_items)).check(withItemCount(0))
            }

        }, object : IAfterWait {
            override fun afterWait(events: List<Event>) {

                onView(withId(R.id.albumlist_pgb_loader)).check(matches(not(isDisplayed())))
                onView(withId(R.id.albumlist_ryv_items)).check(withItemCount(greaterThan(0)))
            }
        })
    }

    @After
    fun afterTest() {

    }
}