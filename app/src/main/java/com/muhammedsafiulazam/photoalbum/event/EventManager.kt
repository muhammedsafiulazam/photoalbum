package com.muhammedsafiulazam.photoalbum.event

import com.muhammedsafiulazam.photoalbum.addon.AddOn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class EventManager : AddOn(), IEventManager {
    @ExperimentalCoroutinesApi
    private val mChannel = BroadcastChannel<Any>(Channel.CONFLATED)

    /**
     * Send event.
     * @param event sent event
     * @param context use context
     */
    @ExperimentalCoroutinesApi
    override fun send(event: Event, context: CoroutineContext) {
        CoroutineScope(context).launch {
            mChannel.send(event)
        }
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    private inline fun <reified T> newChannel(): ReceiveChannel<T> {
        return mChannel.openSubscription().filter { it is T }.map { it as T }
    }

    /**
     * Subscribe to receiving mChannel.
     * @return receiving mChannel
     */
    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun subscribe() : ReceiveChannel<Event> {
        val channel = newChannel<Event>()
        return channel
    }

    /**
     * Subscribe to receiving mChannel.
     * @param callback event callback
     * @param context use context
     * @return receiving mChannel
     */
    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun subscribe(callback: (event: Event) -> Unit, context: CoroutineContext) : ReceiveChannel<Event> {
        val channel = newChannel<Event>()
        CoroutineScope(context).launch {
            for(event in channel) {
                callback(event)
            }
        }
        return channel
    }

    /**
     * Unsubscribe from receiving mChannel.
     * @param receiveChannel receiving mChannel
     */
    override fun unsubscribe(receiveChannel: ReceiveChannel<Event>?) {
        receiveChannel?.cancel()
    }
}