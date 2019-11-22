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

    private val NAME_PHOTO_DB: String = "Photo.db"

    private val mPhotoDB: PhotoDB by lazy {
        val contextManager: IContextManager? = AddOnManager.getAddOn(AddOnType.CONTEXT_MANAGER) as IContextManager?
        val sqlDriver: SqlDriver = AndroidSqliteDriver(PhotoDB.Schema, contextManager!!.getContext(), NAME_PHOTO_DB)
        PhotoDB(sqlDriver)
    }

    private val mPhotoDatabase: IPhotoDatabase by lazy {
        val photoDatabase = PhotoDatabase(mPhotoDB)
        photoDatabase.addAddOns(getAddOns())
        photoDatabase
    }

    override fun getPhotoDatabase(): IPhotoDatabase {
        return mPhotoDatabase
    }
}