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
import com.muhammedsafiulazam.photoalbum.feature.albumlist.view.AlbumListActivity
import com.muhammedsafiulazam.photoalbum.utils.RecyclerViewAssertion.withItemCount
import com.muhammedsafiulazam.photoalbum.utils.UITestUtils
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by Muhammed Safiul Azam on 23/11/2019.
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class AlbumListUITest : BaseUITest() {

    @Rule @JvmField
    var mActivityTestRule: ActivityTestRule<AlbumListActivity> = ActivityTestRule(
        AlbumListActivity::class.java, true, false)

    @Test
    fun loadAlbums() {
        wait(AlbumListEventType.RESPONSE_LOAD_ALBUMS, object : IBeforeWait {
            override fun beforeWait() {
                val intent = Intent(getContext(), AlbumListActivity::class.java)
                mActivityTestRule.launchActivity(intent)
            }

        }, object : IAfterWait {
            override fun afterWait(events: List<Event>) {

                onView(withId(R.id.albumlist_ryv_items)).check(withItemCount(greaterThan(0)))
                onView(withId(R.id.albumlist_txv_message)).check(matches(not(isDisplayed())))
                onView(withId(R.id.albumlist_btn_retry)).check(matches(not(isDisplayed())))
            }
        })
    }
}