package com.muhammedsafiulazam.photoalbum.utils

import android.content.Context
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.context.IContextManager
import com.muhammedsafiulazam.photoalbum.database.photo.PhotoDB
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */

object DatabaseUtils {
    // Photo database driver.
    val PHOTO_DB_DRIVER: SqlDriver by lazy {
        val contextManager: IContextManager? = AddOnManager.getAddOn(AddOnType.CONTEXT_MANAGER) as IContextManager?
        val context: Context? = contextManager?.getContext()
        AndroidSqliteDriver(PhotoDB.Schema, context!!, "Photo.db")
    }
}