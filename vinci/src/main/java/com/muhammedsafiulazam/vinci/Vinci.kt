package com.muhammedsafiulazam.vinci

import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import java.io.InputStream
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * Vinci allows downloading images with caching support.
 * It uses minimum threads and lazy loading to download images.
 */

object Vinci {

    /**
     * Thread pool size.
     * Please keep it minimum.
     */
    private const val THREAD_POOL_SIZE: Int = 2

    /**
     * Executor which uses minimum pool size.
     */
    private val mExecutorService: ExecutorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE)

    /**
     * Handler to invoke callbacks in main thread.
     * Downloads are done in separate threads.
     */
    private val mHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    /**
     * Load image from online or from cache.
     * @param url associate url
     * @param imageView associate view
     * @param vinciCallback associate callback
     * @return cancellable task
     */
    fun load(url: String, imageView: ImageView?, vinciCallback: VinciCallback?) : VinciTask {
        // Future task reference.
        var future: Future<*>? = null

        if (VinciCache.isExists(url)) { // Exists in cache.
            // Get from cache.
            val bitmap = VinciCache.getBitmap(url)
            // Set in image view.
            imageView?.setImageBitmap(bitmap)
            // Invoke callback.
            vinciCallback?.onSuccess(url, bitmap!!)
        } else { // Doesn't exist in cache.

            // Add to queue.
            future = mExecutorService.submit {
                // Before execution.
                if (VinciCache.isExists(url)) { // Exists in cache.
                    mHandler.post {
                        // Get from cache.
                        val bitmap = VinciCache.getBitmap(url)
                        // Set in image view.
                        imageView?.setImageBitmap(bitmap)
                        // Invoke callback.
                        vinciCallback?.onSuccess(url, bitmap!!)
                    }
                } else { // Doesn't exist in cache.
                    try {
                        // Read content from online.
                        val inputStream = URL(url).content as InputStream
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        mHandler.post {
                            // Add to cache.
                            VinciCache.addBitmap(url, bitmap)
                            // Set in image view.
                            imageView?.setImageBitmap(bitmap)
                            // Invoke callback.
                            vinciCallback?.onSuccess(url, bitmap)
                        }
                    } catch (e: Exception) { // Exception.
                        mHandler.post {
                            // Invoke callback.
                            vinciCallback?.onFailure(url, e.cause!!)
                        }
                    }
                }
            }
        }

        // Return task.
        return VinciTask(url, future)
    }
}