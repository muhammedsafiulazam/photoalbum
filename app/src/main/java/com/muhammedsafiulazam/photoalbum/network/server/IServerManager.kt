package com.muhammedsafiulazam.photoalbum.network.server

import com.muhammedsafiulazam.photoalbum.addon.IAddOn
import com.muhammedsafiulazam.photoalbum.network.server.photo.IPhotoServer

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IServerManager : IAddOn {
    /**
     * Get photo server.
     * @return photo server
     */
    fun getPhotoServer() : IPhotoServer
}