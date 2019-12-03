package com.muhammedsafiulazam.photoalbum.picture

import android.graphics.Bitmap
import android.widget.ImageView
import com.muhammedsafiulazam.photoalbum.addon.IAddOn

interface IPictureManager : IAddOn {
    /**
     * Specify type of loader
     * @param loader loader type
     */
    fun useLoader(loader: Int)

    /**
     * Load image from url.
     * @param url associate url
     * @param imageView associate image view
     * @param callback associate callback
     */
    fun load(url: String, imageView: ImageView?, callback: PictureCallback?)
}