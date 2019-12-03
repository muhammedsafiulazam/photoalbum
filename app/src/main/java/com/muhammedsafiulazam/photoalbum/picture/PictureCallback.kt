package com.muhammedsafiulazam.photoalbum.picture

import android.graphics.Bitmap

interface PictureCallback {
    /**
     * On success callback.
     * @param url associate url
     * @param bitmap associate bitmap
     */
    fun onSuccess(url: String, bitmap: Bitmap)

    /**
     * On failure callback.
     * @param url associate url
     * @param throwable associate throwable
     */
    fun onFailure(url: String, throwable: Throwable)
}