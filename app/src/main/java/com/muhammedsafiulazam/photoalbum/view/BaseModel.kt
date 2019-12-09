package com.muhammedsafiulazam.photoalbum.view

import com.muhammedsafiulazam.photoalbum.addon.AddOn
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.event.IEventManager
import kotlinx.coroutines.channels.ReceiveChannel

open class BaseModel : AddOn() {

    private var mReceiveChannel: ReceiveChannel<Event>? = null

    open fun onCreate() {
        // Essential addons.
        addAddOn(AddOnType.SERVICE_MANAGER, AddOnManager.getAddOn(AddOnType.SERVICE_MANAGER)!!)
        addAddOn(AddOnType.EVENT_MANAGER, AddOnManager.getAddOn(AddOnType.EVENT_MANAGER)!!)
        addAddOn(AddOnType.DATABASE_MANAGER, AddOnManager.getAddOn(AddOnType.DATABASE_MANAGER)!!)
    }

    open fun onStart() {
    }

    open fun onResume() {
    }

    open fun onPause() {
    }

    open fun onStop() {
    }

    open fun onDestroy() {
        clearAddOns()
        receiveEvents(false)
    }

    /**
     * Enable / disable receiving events
     * @param receive enable/disable flag.
     */
    fun receiveEvents(receive: Boolean) {
        val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?

        if (receive) {
            mReceiveChannel = eventManager?.subscribe(callback = { event: Event ->
                onReceiveEvents(event)
            })
        } else {
            if (mReceiveChannel != null) {
                eventManager?.unsubscribe(mReceiveChannel)
            }
            mReceiveChannel = null
        }
    }

    /**
     * Receive events (callback).
     * @param event received event
     */
    open fun onReceiveEvents(event: Event) {
    }
}