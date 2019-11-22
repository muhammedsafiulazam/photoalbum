package com.muhammedsafiulazam.photoalbum.database

import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.database.photo.event.PhotoDatabaseEventType
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.event.IEventManager
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.muhammedsafiulazam.photoalbum.utils.TestUtils
import com.tyro.oss.arbitrater.arbitraryInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.asserter

class PhotoDatabaseUnitTest {

    @Before
    fun beforeTest() {
        TestUtils.setup()
    }

    @Test
    fun getPhotos() = runBlocking {
        var e: Event? = null

        val eventManager: IEventManager = AddOnManager.getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        val databaseManager: IDatabaseManager = AddOnManager.getAddOn(AddOnType.DATABASE_MANAGER) as IDatabaseManager

        eventManager.subscribe(callback = { event: Event -> Unit
            if (event.type.equals(PhotoDatabaseEventType.GET_PHOTOS)) {
                e = event
            } else if (event.type.equals(PhotoDatabaseEventType.SAVE_PHOTOS)) {
                databaseManager.getPhotoDatabase().getPhotos()
            }
        })

        databaseManager.getPhotoDatabase().savePhotos(createDummyPhotos())

        delay(1000)
        asserter.assertTrue("", e != null && e!!.data != null)
    }

    @Test
    fun savePhotos() = runBlocking {
        var e: Event? = null

        val eventManager: IEventManager = AddOnManager.getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        val databaseManager: IDatabaseManager = AddOnManager.getAddOn(AddOnType.DATABASE_MANAGER) as IDatabaseManager

        eventManager.subscribe(callback = { event: Event -> Unit
            if (event.type.equals(PhotoDatabaseEventType.SAVE_PHOTOS)) {
                e = event
            }
        })

        databaseManager.getPhotoDatabase().savePhotos(createDummyPhotos())

        delay(1000)
        asserter.assertTrue("", e != null)
    }

    @Test
    fun cleanPhotos() = runBlocking {
        var e: Event? = null

        val eventManager: IEventManager = AddOnManager.getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        val databaseManager: IDatabaseManager = AddOnManager.getAddOn(AddOnType.DATABASE_MANAGER) as IDatabaseManager

        eventManager.subscribe(callback = { event: Event -> Unit
            if (event.type.equals(PhotoDatabaseEventType.CLEAN_PHOTOS)) {
                e = event
            } else if (event.type.equals(PhotoDatabaseEventType.SAVE_PHOTOS)) {
                databaseManager.getPhotoDatabase().cleanPhotos()
            }
        })

        databaseManager.getPhotoDatabase().savePhotos(createDummyPhotos())

        delay(1000)
        asserter.assertTrue("", e != null && e!!.data == null)
    }

    private fun createDummyPhotos() : List<Photo> {
        val photos: MutableList<Photo> = mutableListOf()
        for(i in 0..5) {
            photos.add(i, Photo::class.arbitraryInstance())
        }
        return photos
    }

    @After
    fun afterTest() {

    }
}