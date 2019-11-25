package com.muhammedsafiulazam.photoalbum.context

import android.content.Context
import com.muhammedsafiulazam.photoalbum.addon.IAddOn

interface IContextManager : IAddOn {
    /**
     * Get context.
     * @param context context
     */
    fun getContext() : Context
}