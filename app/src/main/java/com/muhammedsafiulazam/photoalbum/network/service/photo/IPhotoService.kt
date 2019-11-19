package com.muhammedsafiulazam.photoalbum.network.service.photo

import com.muhammedsafiulazam.photoalbum.addon.IAddOn

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

interface IPhotoService : IAddOn {
    /**
     * Get photos.
     */
    fun getPhotos()
}