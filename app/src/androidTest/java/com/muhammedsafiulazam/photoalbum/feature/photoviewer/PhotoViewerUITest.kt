package com.muhammedsafiulazam.photoalbum.feature.photoviewer

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.view.BaseView
import com.muhammedsafiulazam.photoalbum.core.BaseUITest
import com.muhammedsafiulazam.photoalbum.feature.photoviewer.view.PhotoViewerActivity
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.muhammedsafiulazam.photoalbum.utils.ImageViewMatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Muhammed Safiul Azam on 23/11/2019.
 */

@RunWith(AndroidJUnit4ClassRunner::class)
class PhotoViewerUITest : BaseUITest() {

    val DUMMY_PHOTO_URL: String = "https://via.placeholder.com/600/92c952"
    val DUMMY_PHOTO_THUMBNAIL_URL: String = "https://via.placeholder.com/150/92c952"

    @Rule @JvmField
    var mActivityTestRule: ActivityTestRule<PhotoViewerActivity> = ActivityTestRule(
        PhotoViewerActivity::class.java, true, false)

    @Test
    /**
     * Load photo.
     */
    fun loadPhoto() {
        val intent = Intent(getContext(), PhotoViewerActivity::class.java)
        intent.putExtra(BaseView.KEY_DATA, createDummyPhoto())
        mActivityTestRule.launchActivity(intent)

        runBlocking {
            delay(DELAY_AVERAGE)

            onView(withId(R.id.photoviewer_phv_photo)).check(matches(not(ImageViewMatcher.noDrawable())))
            onView(withId(R.id.photoviewer_txv_message)).check(matches(not(isDisplayed())))
            onView(withId(R.id.photoviewer_btn_retry)).check(matches(not(isDisplayed())))
        }
    }

    private fun createDummyPhoto() : Photo {
        return Photo("0", "0", "", DUMMY_PHOTO_URL, DUMMY_PHOTO_THUMBNAIL_URL)
    }
}