package com.muhammedsafiulazam.photoalbum

import android.app.Application
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AddOnManager.initialize(this)
    }

    override fun onTerminate() {
        AddOnManager.clearAddOns()
        super.onTerminate()
    }
}