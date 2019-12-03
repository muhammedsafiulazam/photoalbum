package com.muhammedsafiulazam.vinci

import android.graphics.Bitmap

object Cache {

    private val mCache: MutableMap<String, Bitmap> = mutableMapOf()

    /**
     * Verify in cache.
     * @param url associate url
     * @return verification result
     */
    fun isExists(url: String) : Boolean {
        return mCache.containsKey(url)
    }

    /**
     * Get bitmap from cache.
     * @param url associate url
     * @return associate bitmap
     */
    fun getBitmap(url: String) : Bitmap? {
        return mCache[url]
    }

    /**
     * Add bitmap to cache.
     * @param url associate url
     * @param bitmap associate bitmap
     */
    fun addBitmap(url: String, bitmap: Bitmap) {
        if (!mCache.containsKey(url)) {
            mCache[url] = bitmap
        }
    }

    /**
     * Remove bitmap from cache.
     * @param url associate url
     */
    fun removeBitmap(url: String) {
        if (mCache.containsKey(url)) {
            mCache.remove(url)
        }
    }

    /**
     * Clear cache.
     */
    fun clear() {
        mCache.clear()
    }
}