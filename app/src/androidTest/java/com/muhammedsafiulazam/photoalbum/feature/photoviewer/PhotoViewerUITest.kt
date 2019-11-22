package com.muhammedsafiulazam.photoalbum.feature.photoviewer

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.activity.BaseActivity
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.core.BaseUITest
import com.muhammedsafiulazam.photoalbum.core.IAfterWait
import com.muhammedsafiulazam.photoalbum.core.IBeforeWait
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.feature.albumlist.event.AlbumListEventType
import com.muhammedsafiulazam.photoalbum.feature.albumlist.model.Album
import com.muhammedsafiulazam.photoalbum.feature.photolist.event.PhotoListEventType
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.muhammedsafiulazam.photoalbum.network.service.IServiceManager
import com.muhammedsafiulazam.photoalbum.utils.ConnectivityUtils
import com.muhammedsafiulazam.photoalbum.utils.CoroutineUtils
import com.muhammedsafiulazam.photoalbum.utils.ImageViewMatcher
import com.muhammedsafiulazam.photoalbum.utils.RecyclerViewAssertion.withItemCount
import com.muhammedsafiulazam.photoalbum.utils.UITestUtils
import com.squareup.picasso.Picasso
import com.tyro.oss.arbitrater.arbitraryInstance
import io.mockk.Matcher
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mockito
import java.util.*

/**
 * Created by Muhammed Safiul Azam on 29/07/2019.
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class PhotoViewerUITest : BaseUITest() {

    val DUMMY_PHOTO_URL: String = "https://via.placeholder.com/600/92c952"
    val DUMMY_PHOTO_THUMBNAIL_URL: String = "https://via.placeholder.com/150/92c952"

    @Rule @JvmField
    var mActivityTestRule: ActivityTestRule<PhotoViewerActivity> = ActivityTestRule(PhotoViewerActivity::class.java, true, false)

    @Test
    fun loadPhoto_Online() {
        val intent = Intent(getContext(), PhotoViewerActivity::class.java)
        intent.putExtra(BaseActivity.KEY_DATA, createDummyPhoto())
        mActivityTestRule.launchActivity(intent)

        onView(withId(R.id.photoviewer_phv_photo)).check(matches(ImageViewMatcher.noDrawable()))
        onView(withId(R.id.photoviewer_txv_message)).check(matches(not(isDisplayed())))
        onView(withId(R.id.photoviewer_btn_retry)).check(matches(not(isDisplayed())))

        runBlocking {
            delay(DELAY_AVERAGE)

            onView(withId(R.id.photoviewer_phv_photo)).check(matches(not(ImageViewMatcher.noDrawable())))
            onView(withId(R.id.photoviewer_txv_message)).check(matches(not(isDisplayed())))
            onView(withId(R.id.photoviewer_btn_retry)).check(matches(not(isDisplayed())))
        }
    }

    @Test
    fun loadPhoto_Offline() {

        // Enable offline.
        UITestUtils.offline()

        val intent = Intent(getContext(), PhotoViewerActivity::class.java)
        intent.putExtra(BaseActivity.KEY_DATA, createDummyPhoto())
        mActivityTestRule.launchActivity(intent)

        onView(withId(R.id.photoviewer_phv_photo)).check(matches(ImageViewMatcher.noDrawable()))
        onView(withId(R.id.photoviewer_txv_message)).check(matches(not(isDisplayed())))
        onView(withId(R.id.photoviewer_btn_retry)).check(matches(not(isDisplayed())))

        runBlocking {
            delay(DELAY_AVERAGE)

            onView(withId(R.id.photoviewer_phv_photo)).check(matches(ImageViewMatcher.noDrawable()))
            onView(withId(R.id.photoviewer_txv_message)).check(matches(isDisplayed()))
            onView(withId(R.id.photoviewer_btn_retry)).check(matches(isDisplayed()))
        }
    }

    private fun createDummyPhoto() : Photo {
        return Photo("0", "0", "", DUMMY_PHOTO_URL, DUMMY_PHOTO_THUMBNAIL_URL)
    }
}