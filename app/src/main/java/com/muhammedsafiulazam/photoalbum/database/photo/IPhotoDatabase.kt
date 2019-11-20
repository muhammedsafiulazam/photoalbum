package com.muhammedsafiulazam.photoalbum.database.photo

import com.muhammedsafiulazam.photoalbum.addon.IAddOn
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo

interface IPhotoDatabase : IAddOn {
    fun getPhotos()
    fun savePhotos(photos: List<Photo>)
    fun cleanPhotos()
}