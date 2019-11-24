package com.muhammedsafiulazam.photoalbum.database

import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.core.BaseUnitTest
import com.muhammedsafiulazam.photoalbum.database.photo.IPhotoDatabase
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