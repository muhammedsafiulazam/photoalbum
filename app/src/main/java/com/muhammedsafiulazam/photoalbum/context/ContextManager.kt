package com.muhammedsafiulazam.photoalbum.context

import android.content.Context
import com.muhammedsafiulazam.photoalbum.addon.AddOn

class ContextManager(context: Context) : AddOn(), IContextManager {

    private var mContext: Context

    init {
        mContext = context
    }

    /**
     * Get context.
     * @param context context
     */
    override fun getContext() : Context {
        return mContext
    }
}