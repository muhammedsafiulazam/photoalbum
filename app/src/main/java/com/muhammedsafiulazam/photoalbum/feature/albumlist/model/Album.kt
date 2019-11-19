package com.muhammedsafiulazam.photoalbum.feature.albumlist.model

import android.os.Parcelable
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

@Parcelize
data class Album (
    @field:Json(name = "id") val id: String,
    @field:Json(name = "photos") val photos: MutableList<Photo>
) : Parcelable