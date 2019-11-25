package com.muhammedsafiulazam.photoalbum.utils

import android.content.Context
import android.content.res.Resources
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.database.IDatabaseManager
import com.muhammedsafiulazam.photoalbum.database.photo.PhotoDB
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject

object UnitTestUtils {

    fun setup() {

        val context: Context = mockk<Context>()
        every { context.resources } returns mockk<Resources>()
        // TODO - Quick Solution.
        every { context.resources.getString(any()) } returns ""
        every { context.resources.getInteger(any()) } returns 0
        AddOnManager.initialize(context)

        mockkObject(CoroutineUtils)
        every { CoroutineUtils.DISPATCHER_MAIN } returns CoroutineUtils.DISPATCHER_IO

        mockkObject(DatabaseUtils)
        every { DatabaseUtils.PHOTO_DB_DRIVER } returns JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
            PhotoDB.Schema.create(this)
        }

        // Default.
        UnitTestUtils.online()
    }

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