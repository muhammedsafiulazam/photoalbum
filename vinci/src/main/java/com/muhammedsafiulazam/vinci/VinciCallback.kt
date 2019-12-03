package com.muhammedsafiulazam.vinci

import android.graphics.Bitmap

/**
 * VinciCallback allows to result with callback.
 */

interface VinciCallback {
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