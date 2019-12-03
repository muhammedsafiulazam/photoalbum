package com.muhammedsafiulazam.vinci

import android.graphics.Bitmap

interface Callback {
    /**
     * On success callback.
     * @param url associate url
     * @param bitmap associate bitmap
     */
    fun onSuccess(url: String, bitmap: Bitmap)

    /**
     * On failure callback.
     * @param url associate url
     * @param error associate error
     */
    fun onFailure(url: String, throwable: Throwable)
}