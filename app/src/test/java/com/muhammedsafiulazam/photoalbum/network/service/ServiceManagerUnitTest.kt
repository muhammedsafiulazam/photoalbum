package com.muhammedsafiulazam.photoalbum.network.service

import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.core.BaseUnitTest
import com.muhammedsafiulazam.photoalbum.network.service.photo.IPhotoService
import org.junit.Test
import kotlin.test.asserter

class ServiceManagerUnitTest : BaseUnitTest() {

    @Test
    /**
     * Access photo service.
     */
    fun accessPhotoService() {
        val serviceManager: IServiceManager = AddOnManager.getAddOn(AddOnType.SERVICE_MANAGER) as IServiceManager
        val photoService: IPhotoService? = serviceManager.getPhotoService()
        asserter.assertTrue("accessPhotoService", photoService != null)
    }
}