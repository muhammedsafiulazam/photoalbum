package com.muhammedsafiulazam.photoalbum.network.server

import com.muhammedsafiulazam.photoalbum.addon.AddOn
import com.muhammedsafiulazam.photoalbum.network.server.photo.IPhotoServer
import com.muhammedsafiulazam.photoalbum.network.server.photo.PhotoServer

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class ServerManager : AddOn(), IServerManager {

    private val mPhotoServer: IPhotoServer by lazy {
        val photoServer = PhotoServer()
        photoServer.addAddOns(getAddOns())
        photoServer
    }

    /**
     * Get photo server.
     * @return photo server
     */
    override fun getPhotoServer(): IPhotoServer {
        return mPhotoServer
    }

    /**
     * Clear addons.
     */
    override fun clearAddOns() {
        mPhotoServer.clearAddOns()
        super.clearAddOns()
    }
}