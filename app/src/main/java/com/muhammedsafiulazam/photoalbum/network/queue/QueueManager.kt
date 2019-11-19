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
        private const val TIMER_DELAY: Long = 0
        private const val TIMER_PERIOD: Long = 500
        private const val THREAD_POOL_SIZE: Int = 10
    }

    private val mTicker: ReceiveChannel<Unit> = ticker(TIMER_PERIOD, TIMER_DELAY)
    private val mQueue: MutableMap<Call<Any>, (response: Response<Any>) -> Unit> = mutableMapOf()
    private val mExecutorService: ExecutorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE)

    init {
        // TODO
        // Need to improve here.
        GlobalScope.launch(Dispatchers.IO) {
            mTicker.consumeEach {
                checkQueue()
            }
        }
    }

    /**
     * Execute call and response via callback.
     * @param call execute call
     * @param callback callback for response
     */
    override fun execute(call: Call<Any>, callback: (response: Response<Any>) -> Unit) {
        // TODO
        // Remove duplicate calls.
        // Need to keep callbacks though.
        mQueue[call] = callback
    }

    private fun checkQueue() {
        val calls: MutableSet<Call<Any>>? = mQueue.keys

        while(calls != null && !calls.isEmpty()) {
            var call: Call<Any> = calls.first();
            val callback: ((response: Response<Any>) -> Unit) = mQueue.get(call)!!
            mExecutorService.execute(Runnable {
                val response: Response<Any> = call.execute()
                GlobalScope.launch(Dispatchers.IO) {
                    callback(response)
                }
            })
            mQueue.remove(call)
        }
    }

    /**
     * Shutdown manager.
     */
    override fun shutdown() {
        mTicker.cancel()
        mExecutorService?.shutdownNow()
    }

    /**
     * Clear addons.
     */
    override fun clearAddOns() {
        shutdown()
        super.clearAddOns()
    }
}