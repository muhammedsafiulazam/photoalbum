package com.muhammedsafiulazam.photoalbum.network.service

import com.muhammedsafiulazam.photoalbum.addon.AddOn
import com.muhammedsafiulazam.photoalbum.network.service.photo.IPhotoService
import com.muhammedsafiulazam.photoalbum.network.service.photo.PhotoService

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class ServiceManager : AddOn(), IServiceManager {

    private val mPhotoService: IPhotoService by lazy {
        val photoService = PhotoService()
        photoService.addAddOns(getAddOns())
        photoService
    }

    /**
     * Get photo service.
     * @return photo service
     */
    override fun getPhotoService(): IPhotoService {
        return mPhotoService
    }

    /**
     * Clear addons.
     */
    override fun clearAddOns() {
        mPhotoService.clearAddOns()
        super.clearAddOns()
    }
}