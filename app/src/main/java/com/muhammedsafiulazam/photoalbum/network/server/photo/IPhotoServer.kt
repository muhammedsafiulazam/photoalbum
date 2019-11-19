package com.muhammedsafiulazam.photoalbum.network.server.photo

import com.muhammedsafiulazam.photoalbum.addon.IAddOn

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

interface IPhotoServer : IAddOn {
    /**
     * Get photo call.
     * @return photo call
     */
    fun getPhotoCall() : IPhotoCall
}