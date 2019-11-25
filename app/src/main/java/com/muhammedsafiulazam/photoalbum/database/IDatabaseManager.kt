package com.muhammedsafiulazam.photoalbum.database

import com.muhammedsafiulazam.photoalbum.addon.IAddOn
import com.muhammedsafiulazam.photoalbum.database.photo.IPhotoDatabase

interface IDatabaseManager : IAddOn {
    /**
     * Get photo database.
     * @return photo database
     */
    fun getPhotoDatabase() : IPhotoDatabase
}