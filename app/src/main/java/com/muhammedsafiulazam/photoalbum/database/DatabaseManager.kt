package com.muhammedsafiulazam.photoalbum.database

import com.muhammedsafiulazam.photoalbum.addon.AddOn
import com.muhammedsafiulazam.photoalbum.database.photo.IPhotoDatabase
import com.muhammedsafiulazam.photoalbum.database.photo.PhotoDB
import com.muhammedsafiulazam.photoalbum.database.photo.PhotoDatabase
import com.muhammedsafiulazam.photoalbum.utils.DatabaseUtils

class DatabaseManager : AddOn(), IDatabaseManager {
    private val mPhotoDatabase: IPhotoDatabase by lazy {
        val photoDatabase = PhotoDatabase(PhotoDB(DatabaseUtils.PHOTO_DB_DRIVER))
        photoDatabase.addAddOns(getAddOns())
        photoDatabase
    }

    /**
     * Get photo database.
     * @return photo database
     */
    override fun getPhotoDatabase(): IPhotoDatabase {
        return mPhotoDatabase
    }
}