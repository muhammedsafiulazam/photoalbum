package com.muhammedsafiulazam.photoalbum.database

import com.muhammedsafiulazam.photoalbum.addon.AddOn
import com.muhammedsafiulazam.photoalbum.database.photo.IPhotoDatabase
import com.muhammedsafiulazam.photoalbum.database.photo.PhotoDB
import com.muhammedsafiulazam.photoalbum.database.photo.PhotoDatabase
import com.muhammedsafiulazam.photoalbum.utils.DatabaseUtils
import com.squareup.sqldelight.android.AndroidSqliteDriver

class DatabaseManager : AddOn(), IDatabaseManager {

    private val mPhotoDB: PhotoDB by lazy {
        PhotoDB(DatabaseUtils.PHOTO_DB_DRIVER!!)
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