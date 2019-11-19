package com.muhammedsafiulazam.photoalbum.feature.photolist

import com.muhammedsafiulazam.photoalbum.feature.albumlist.model.Album
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

interface IPhotoListListener {
    fun onClickPhoto(photo: Photo)
}