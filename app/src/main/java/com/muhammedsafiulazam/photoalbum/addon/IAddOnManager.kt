package com.muhammedsafiulazam.photoalbum.addon

import android.content.Context

interface IAddOnManager : IAddOn {

    /**
     * Initialize with context.
     * @param context application context
     */
    fun initialize(context: Context)
}