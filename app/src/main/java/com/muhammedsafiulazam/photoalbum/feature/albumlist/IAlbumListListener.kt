package com.muhammedsafiulazam.photoalbum.feature.albumlist

import com.muhammedsafiulazam.photoalbum.feature.albumlist.model.Album

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

interface IAlbumListListener {
    fun onClickAlbum(album: Album)
}