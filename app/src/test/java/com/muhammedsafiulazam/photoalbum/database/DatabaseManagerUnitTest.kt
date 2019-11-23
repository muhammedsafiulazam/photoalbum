package com.muhammedsafiulazam.photoalbum.database

import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.core.BaseUnitTest
import com.muhammedsafiulazam.photoalbum.database.IDatabaseManager
import com.muhammedsafiulazam.photoalbum.database.photo.IPhotoDatabase
import com.muhammedsafiulazam.photoalbum.database.photo.event.PhotoDatabaseEventType
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.event.IEventManager
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.tyro.oss.arbitrater.arbitraryInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.asserter

class DatabaseManagerUnitTest : BaseUnitTest() {

    @Test
    fun accessPhotoDatabase() {
        val databaseManager: IDatabaseManager = AddOnManager.getAddOn(AddOnType.DATABASE_MANAGER) as IDatabaseManager
        val photoDatabase: IPhotoDatabase? = databaseManager.getPhotoDatabase()
        asserter.assertTrue("accessPhotoDatabase", photoDatabase != null)
    }
}