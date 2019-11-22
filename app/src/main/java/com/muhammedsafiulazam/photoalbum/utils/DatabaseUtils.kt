package com.muhammedsafiulazam.photoalbum.utils

import android.content.Context
import com.muhammedsafiulazam.photoalbum.activity.IActivityManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.database.photo.PhotoDB
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */

object DatabaseUtils {
    // Driver.
    var PHOTO_DB_DRIVER: SqlDriver? = AndroidSqliteDriver(PhotoDB.Schema, getContext()!!, "photo.db")

    private fun getContext() : Context? {
        val activityManager: IActivityManager = AddOnManager.getAddOn(AddOnType.ACTIVITY_MANAGER) as IActivityManager
        return activityManager.getCurrentActivity()?.applicationContext
    }
}