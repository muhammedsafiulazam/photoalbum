package com.muhammedsafiulazam.photoalbum.utils

import android.content.Context
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.database.photo.PhotoDB
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.objectMockk

object UnitTestUtils {

    fun setup() {
        AddOnManager.initialize(mockk<Context>())
        CoroutineUtils.DISPATCHER_MAIN = CoroutineUtils.DISPATCHER_IO

        objectMockk(DatabaseUtils).mock()
        every { DatabaseUtils.PHOTO_DB_DRIVER } returns JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
            PhotoDB.Schema.create(this)
        }
    }
}