package com.muhammedsafiulazam.photoalbum.utils

import android.content.Context
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.database.photo.PhotoDB
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import io.mockk.mockk

object TestUtils {

    fun setup() {
        AddOnManager.initialize(mockk<Context>())
        CoroutineUtils.DISPATCHER_MAIN = CoroutineUtils.DISPATCHER_IO
        DatabaseUtils.PHOTO_DB_DRIVER = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
            PhotoDB.Schema.create(this)
        }
    }
}