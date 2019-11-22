package com.muhammedsafiulazam.photoalbum.feature.albumlist

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
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
import com.muhammedsafiulazam.photoalbum.utils.ConnectivityUtils
import com.muhammedsafiulazam.photoalbum.utils.CoroutineUtils
import com.muhammedsafiulazam.photoalbum.utils.RecyclerViewAssertion.withItemCount
import com.muhammedsafiulazam.photoalbum.utils.UITestUtils
import com.squareup.picasso.Picasso
import io.mockk.every
import io.mockk.mockkStatic
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import java.util.*

/**
 * Created by Muhammed Safiul Azam on 29/07/2019.
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class AlbumListUITest : BaseUITest() {

    @Rule @JvmField
    var mActivityTestRule: ActivityTestRule<AlbumListActivity> = ActivityTestRule(AlbumListActivity::class.java, true, false)

    @Test
    fun loadAlbums_Online() {
        wait(AlbumListEventType.RESPONSE_LOAD_ALBUMS, object : IBeforeWait {
            override fun beforeWait() {
                val intent = Intent(getContext(), AlbumListActivity::class.java)
                mActivityTestRule.launchActivity(intent)

                onView(withId(R.id.albumlist_ryv_items)).check(withItemCount(0))
                onView(withId(R.id.albumlist_txv_message)).check(matches(not(isDisplayed())))
                onView(withId(R.id.albumlist_btn_retry)).check(matches(not(isDisplayed())))
            }

        }, object : IAfterWait {
            override fun afterWait(events: List<Event>) {

                onView(withId(R.id.albumlist_ryv_items)).check(withItemCount(greaterThan(0)))
                onView(withId(R.id.albumlist_txv_message)).check(matches(not(isDisplayed())))
                onView(withId(R.id.albumlist_btn_retry)).check(matches(not(isDisplayed())))
            }
        })
    }

    @Test
    fun loadAlbums_Offline() {

        UITestUtils.offline()

        // We are waiting for 2 AlbumListEventType.RESPONSE_LOAD_ALBUMS.
        // As there is no connectivity, activity loads very fast.
        // So after initial response, we are clicking on retry to reload.

        wait(Arrays.asList(AlbumListEventType.RESPONSE_LOAD_ALBUMS, AlbumListEventType.RESPONSE_LOAD_ALBUMS), object : IBeforeWait {
            override fun beforeWait() {
                val intent = Intent(getContext(), AlbumListActivity::class.java)
                mActivityTestRule.launchActivity(intent)

                onView(withId(R.id.albumlist_btn_retry)).perform(click())
            }

        }, object : IAfterWait {
            override fun afterWait(events: List<Event>) {

                onView(withId(R.id.albumlist_ryv_items)).check(withItemCount(0))
                onView(withId(R.id.albumlist_txv_message)).check(matches(isDisplayed()))
                onView(withId(R.id.albumlist_btn_retry)).check(matches(isDisplayed()))
            }
        })
    }
}