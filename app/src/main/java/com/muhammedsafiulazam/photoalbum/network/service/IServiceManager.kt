package com.muhammedsafiulazam.photoalbum.network.service

import com.muhammedsafiulazam.photoalbum.addon.IAddOn
import com.muhammedsafiulazam.photoalbum.network.service.photo.IPhotoService

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IServiceManager : IAddOn {
    /**
     * Get photo service.
     * @return photo service
     */
    fun getPhotoService() : IPhotoService
}