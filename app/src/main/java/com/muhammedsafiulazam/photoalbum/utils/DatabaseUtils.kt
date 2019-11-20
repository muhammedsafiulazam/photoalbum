package com.muhammedsafiulazam.photoalbum.utils

import com.muhammedsafiulazam.photoalbum.database.photo.PhotoDB
import com.squareup.sqldelight.db.SqlDriver

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */

object DatabaseUtils {
    // Schema.
    val PHOTO_DB_SCHEMA: SqlDriver.Schema = PhotoDB.Schema

    // Filename.
    val PHOTO_DB_FILENAME: String = "photo.db"

    // Driver.
    var PHOTO_DB_DRIVER: SqlDriver? = null
}