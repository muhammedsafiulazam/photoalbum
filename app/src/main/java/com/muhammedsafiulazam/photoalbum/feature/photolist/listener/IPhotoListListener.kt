package com.muhammedsafiulazam.photoalbum.feature.photolist.listener

import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

interface IPhotoListListener {
    fun onClickPhoto(photo: Photo)
}