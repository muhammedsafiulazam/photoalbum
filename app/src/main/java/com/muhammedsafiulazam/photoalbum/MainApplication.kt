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
        // Enable vector support.
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        // Initialize AddOnManager.
        AddOnManager.initialize(this)
    }

    override fun onTerminate() {
        // Clear AddOnManager.
        AddOnManager.clearAddOns()
        super.onTerminate()
    }
}