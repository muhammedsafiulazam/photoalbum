package com.muhammedsafiulazam.photoalbum.feature.photolist

import android.content.Intent
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
import com.muhammedsafiulazam.photoalbum.utils.RecyclerViewAssertion.withItemCount
import com.muhammedsafiulazam.photoalbum.utils.UITestUtils
import com.squareup.picasso.Picasso
import com.tyro.oss.arbitrater.arbitraryInstance
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
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
class PhotoListUITest : BaseUITest() {

    @Rule @JvmField
    var mActivityTestRule: ActivityTestRule<PhotoListActivity> = ActivityTestRule(PhotoListActivity::class.java, true, false)

    fun loadData() = runBlocking {
        val serviceManager: IServiceManager = AddOnManager.getAddOn(AddOnType.SERVICE_MANAGER) as IServiceManager
        serviceManager.getPhotoService().getPhotos()
        delay(3000)
    }

    @Test
    fun loadPhotos() {

        val photo: Photo = Photo::class.arbitraryInstance()
        val album: Album = Album(photo.albumId!!, Arrays.asList(photo))

        wait(PhotoListEventType.RESPONSE_LOAD_PHOTOS, object : IBeforeWait {
            override fun beforeWait() {
                val intent = Intent(getContext(), PhotoListActivity::class.java)
                intent.putExtra(BaseActivity.KEY_DATA, album)

                mActivityTestRule.launchActivity(intent)

                onView(withId(R.id.photolist_txv_message)).check(matches(not(isDisplayed())))
            }

        }, object : IAfterWait {
            override fun afterWait(events: List<Event>) {

                onView(withId(R.id.photolist_ryv_items)).check(withItemCount(greaterThan(0)))
            }
        })
    }
}