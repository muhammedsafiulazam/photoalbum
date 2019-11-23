package com.muhammedsafiulazam.photoalbum.network.service

import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.core.BaseUnitTest
import com.muhammedsafiulazam.photoalbum.database.IDatabaseManager
import com.muhammedsafiulazam.photoalbum.database.photo.IPhotoDatabase
import com.muhammedsafiulazam.photoalbum.database.photo.event.PhotoDatabaseEventType
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.event.IEventManager
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.muhammedsafiulazam.photoalbum.network.service.photo.IPhotoService
import com.tyro.oss.arbitrater.arbitraryInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.asserter

class ServiceManagerUnitTest : BaseUnitTest() {

    @Test
    fun accessPhotoService() {
        val serviceManager: IServiceManager = AddOnManager.getAddOn(AddOnType.SERVICE_MANAGER) as IServiceManager
        val photoService: IPhotoService? = serviceManager.getPhotoService()
        asserter.assertTrue("accessPhotoService", photoService != null)
    }
}