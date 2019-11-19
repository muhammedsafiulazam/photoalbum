package com.muhammedsafiulazam.photoalbum.network.queue

import com.muhammedsafiulazam.photoalbum.addon.AddOn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class QueueManager : AddOn(), IQueueManager {

    companion object {
        private const val THREAD_POOL_SIZE: Int = 5
    }

    private val mExecutorService: ExecutorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE)

    /**
     * Execute call and response via callback.
     * @param call execute call
     * @param callback callback for response
     */
    override fun execute(call: Call<Any>, callback: (response: Response<Any>) -> Unit) {
        mExecutorService.execute(Runnable {
            val response: Response<Any> = call.execute()
            callback(response)
        })
    }

    /**
     * Clear addons.
     */
    override fun clearAddOns() {
        mExecutorService.shutdown()
        super.clearAddOns()
    }
}