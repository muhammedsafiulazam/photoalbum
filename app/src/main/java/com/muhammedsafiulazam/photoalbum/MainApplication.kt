package com.muhammedsafiulazam.photoalbum

import android.app.Application
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.utils.DatabaseUtils
import com.squareup.sqldelight.android.AndroidSqliteDriver

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Database driver.
        DatabaseUtils.PHOTO_DB_DRIVER = AndroidSqliteDriver(DatabaseUtils.PHOTO_DB_SCHEMA, this, DatabaseUtils.PHOTO_DB_FILENAME)
    }

    override fun onTerminate() {
        AddOnManager.clearAddOns()
        super.onTerminate()
    }
}