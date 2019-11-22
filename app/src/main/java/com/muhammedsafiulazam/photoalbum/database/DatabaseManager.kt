package com.muhammedsafiulazam.photoalbum.database

import android.content.Context
import com.muhammedsafiulazam.photoalbum.addon.AddOn
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.context.IContextManager
import com.muhammedsafiulazam.photoalbum.database.photo.IPhotoDatabase
import com.muhammedsafiulazam.photoalbum.database.photo.PhotoDB
import com.muhammedsafiulazam.photoalbum.database.photo.PhotoDatabase
import com.muhammedsafiulazam.photoalbum.utils.DatabaseUtils
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class DatabaseManager : AddOn(), IDatabaseManager {
    private val mPhotoDatabase: IPhotoDatabase by lazy {
        val photoDatabase = PhotoDatabase(PhotoDB(DatabaseUtils.PHOTO_DB_DRIVER))
        photoDatabase.addAddOns(getAddOns())
        photoDatabase
    }

    override fun getPhotoDatabase(): IPhotoDatabase {
        return mPhotoDatabase
    }
}