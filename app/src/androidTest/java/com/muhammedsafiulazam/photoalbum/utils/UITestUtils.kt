package com.muhammedsafiulazam.photoalbum.utils

import com.muhammedsafiulazam.photoalbum.addon.AddOn
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.database.IDatabaseManager
import com.squareup.picasso.Picasso
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.objectMockk


object UITestUtils {

    fun online() {
        mockkObject(ConnectivityUtils)
        every { ConnectivityUtils.isOnline() } returns true

        val databaseManager: IDatabaseManager = AddOnManager.getAddOn(AddOnType.DATABASE_MANAGER) as IDatabaseManager
        databaseManager.getPhotoDatabase().cleanPhotos()
    }

    fun offline() {
        mockkObject(ConnectivityUtils)
        every { ConnectivityUtils.isOnline() } returns false

        val databaseManager: IDatabaseManager = AddOnManager.getAddOn(AddOnType.DATABASE_MANAGER) as IDatabaseManager
        databaseManager.getPhotoDatabase().cleanPhotos()
    }
}