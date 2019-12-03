package com.muhammedsafiulazam.vinci

import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache

/**
 * VinciCache is responsible for caching images.
 */

object VinciCache {
    // Tags.
    private const val TAG: String = "VinciCache"

    // Messages.
    private const val MESSAGE_CACHE_MEMORY_LOW: String = "Cache memory is low."

    // Factors.
    private const val FACTOR_CACHE_MEMORY: Int = 5
    private const val FACTOR_KILOBYTES_TO_BYTES: Int = 1024

    // Lru cache.
    private var mCache: LruCache<String, Bitmap>

    init {
        //  Initialize cache.
        mCache = object : LruCache<String, Bitmap>(getCacheMemory()) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                return bitmap.byteCount / FACTOR_KILOBYTES_TO_BYTES
            }
        }
    }

    /**
     * Get max memory.
     * @return max memory in kilobytes
     */
    private fun getMaxMemory() : Int {
        return (Runtime.getRuntime().maxMemory() / FACTOR_KILOBYTES_TO_BYTES).toInt()
    }

    /**
     * Get cache memory.
     * @return cache memory in kilobytes
     */
    private fun getCacheMemory() : Int {
        return getMaxMemory() / FACTOR_CACHE_MEMORY
    }

    /**
     * Get bitmap size.
     * @param bitmap associate bitmap
     * @return bitmap size in kilobytes
     */
    private fun getBitmapSize(bitmap: Bitmap) : Int {
        return bitmap.byteCount / FACTOR_KILOBYTES_TO_BYTES
    }

    /**
     * Verify available memory.
     * @param bitmap associate bitmap
     * @return verification result.
     */
    private fun isCacheable(bitmap: Bitmap) : Boolean {
        val bitmapSize = getBitmapSize(bitmap)
        val freeCacheSize = mCache.maxSize() - mCache.size()
        return freeCacheSize > bitmapSize
    }

    /**
     * Verify cache.
     * @param url associate url
     * @return verification result
     */
    fun isExists(url: String) : Boolean {
        return mCache[url] != null
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
        if (!isExists(url)) {
            if (isCacheable(bitmap)) {
                mCache.put(url, bitmap)
            } else {
                Log.w(TAG, MESSAGE_CACHE_MEMORY_LOW)
            }
        }
    }

    /**
     * Remove bitmap from cache.
     * @param url associate url
     */
    fun removeBitmap(url: String) {
        if (isExists(url)) {
            mCache.remove(url)
        }
    }

    /**
     * Clear cache.
     */
    fun clear() {
        mCache.evictAll()
    }
}