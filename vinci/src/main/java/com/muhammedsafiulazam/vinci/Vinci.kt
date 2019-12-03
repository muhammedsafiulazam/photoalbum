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

object Vinci {

    private const val THREAD_POOL_SIZE: Int = 5
    private val mExecutorService: ExecutorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE)
    private val mHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    fun load(url: String, imageView: ImageView?, callback: Callback?) : Task {
        var future: Future<*>? = null
        if (Cache.isExists(url)) {
            val bitmap = Cache.getBitmap(url)
            imageView?.setImageBitmap(bitmap)
            callback?.onSuccess(url, bitmap!!)
        } else {
            future = mExecutorService.submit {
                if (Cache.isExists(url)) {
                    mHandler.post {
                        val bitmap = Cache.getBitmap(url)
                        imageView?.setImageBitmap(bitmap)
                        callback?.onSuccess(url, bitmap!!)
                    }
                } else {
                    try {
                        val inputStream = URL(url).content as InputStream
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        mHandler.post {
                            Cache.addBitmap(url, bitmap)
                            imageView?.setImageBitmap(bitmap)
                            callback?.onSuccess(url, bitmap)
                        }
                    } catch (e: Exception) {
                        mHandler.post {
                            callback?.onFailure(url, e.cause!!)
                        }
                    }
                }
            }
        }
        return Task(url, future)
    }
}