package com.muhammedsafiulazam.photoalbum.network.server.photo

import com.muhammedsafiulazam.photoalbum.addon.AddOn
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.network.retrofit.IRetrofitManager

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

class PhotoServer : AddOn(), IPhotoServer {
    // Book calls.
    private val mPhotoCall : IPhotoCall by lazy {
        val retrofitManager: IRetrofitManager = getAddOn(AddOnType.RETROFIT_MANAGER) as IRetrofitManager
        retrofitManager.getRetrofit().create(IPhotoCall::class.java)
    }

    /**
     * Get repository calls.
     * @return repository calls
     */
    override fun getPhotoCall() : IPhotoCall {
        return mPhotoCall
    }
}