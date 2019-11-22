package com.muhammedsafiulazam.photoalbum.addon

import android.content.Context

interface IAddOnManager : IAddOn {

    fun initialize(context: Context)
}