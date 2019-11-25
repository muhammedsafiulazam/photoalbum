package com.muhammedsafiulazam.photoalbum.utils

import android.os.Build
import com.muhammedsafiulazam.photoalbum.feature.albumlist.model.Album
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.tyro.oss.arbitrater.arbitraryInstance
import java.util.*

object UnitDummyUtils {

    fun createDummyAlbum() : Album {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            val random = Random()
            val albumId: String = random.nextInt().toString()
            val photos: MutableList<Photo> = mutableListOf()
            for (i in 0..10) {
                photos.add(Photo(albumId, random.nextInt().toString(), random.nextInt().toString(), random.nextInt().toString(), random.nextInt().toString()))
            }
            return Album(albumId, photos)
        }
        return Album::class.arbitraryInstance()
    }

    fun createDummyPhotos() : List<Photo> {
        return createDummyAlbum().photos
    }
}