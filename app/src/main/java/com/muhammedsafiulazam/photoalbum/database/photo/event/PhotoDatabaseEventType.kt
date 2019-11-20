package com.muhammedsafiulazam.photoalbum.database.photo.event

object PhotoDatabaseEventType {
    // Tag.
    private const val TAG = "PHOTO_DATABASE_EVENT_TYPE_"

    // Types
    const val GET_PHOTOS: String = TAG + "GET_PHOTOS"
    const val SAVE_PHOTOS: String = TAG + "SAVE_PHOTOS"
    const val CLEAN_PHOTOS: String = TAG + "CLEAN_PHOTOS"
}