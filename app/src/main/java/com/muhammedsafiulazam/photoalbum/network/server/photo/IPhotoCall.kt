package com.muhammedsafiulazam.photoalbum.network.server.photo

import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

interface IPhotoCall {
    /**
     * Get photos.
     * @return list of photo
     */
    @GET("img/shared/technical-test.json")
    fun getBooks() : Call<List<Photo>>
}