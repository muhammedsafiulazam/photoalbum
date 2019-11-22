package com.muhammedsafiulazam.photoalbum

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AddOnManager.initialize(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onTerminate() {
        AddOnManager.clearAddOns()
        super.onTerminate()
    }
}