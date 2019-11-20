package com.muhammedsafiulazam.photoalbum.database

import com.muhammedsafiulazam.photoalbum.addon.IAddOn
import com.muhammedsafiulazam.photoalbum.database.photo.IPhotoDatabase

interface IDatabaseManager : IAddOn {
    fun getPhotoDatabase() : IPhotoDatabase
}