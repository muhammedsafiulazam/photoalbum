package com.muhammedsafiulazam.photoalbum.view

import android.util.Log
import androidx.lifecycle.ViewModel
import com.muhammedsafiulazam.photoalbum.addon.AddOn
import com.muhammedsafiulazam.photoalbum.addon.AddOnManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.addon.IAddOn
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.event.IEventManager
import kotlinx.coroutines.channels.ReceiveChannel

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

open class BaseViewModel : ViewModel(), IAddOn {

    private var mReceiveChannel: ReceiveChannel<Event>? = null
    private var mModel: BaseModel? = null
    private val mAddOn: AddOn = AddOn()

    /**
     * Get associated model.
     * @return associated model
     */
    fun getModel() : BaseModel? {
        return mModel
    }

    /**
     * Set associated model.
     * @param model associated model
     */
    fun setModel(model: Class<out BaseModel>) {
        mModel = model.newInstance()
        mModel?.onCreate()
    }

    open fun onCreate() {
        // Essential addons for activity model.
        addAddOn(AddOnType.EVENT_MANAGER, AddOnManager.getAddOn(AddOnType.EVENT_MANAGER)!!)
    }

    open fun onStart() {
        mModel?.onStart()
    }

    open fun onResume() {
        mModel?.onResume()
    }

    open fun onPause() {
        mModel?.onPause()
    }

    open fun onStop() {
        mModel?.onStop()
    }

    open fun onDestroy() {
        clearAddOns()
        receiveEvents(false)
        mModel?.onDestroy()
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

    // Addons related methods.

    /**
     * Get addon.
     * @param type type of addon
     * @return addon for type
     */
    override fun getAddOn(type: String) : IAddOn? {
        return mAddOn.getAddOn(type)
    }

    /**
     * Get addons.
     * @return map of addons
     */
    override fun getAddOns() : Map<String, IAddOn> {
        return mAddOn.getAddOns()
    }

    /**
     * Add addon.
     * @param type type of addon
     * @param addOn addon to be added
     */
    override fun addAddOn(type: String, addOn: IAddOn) {
        mAddOn.addAddOn(type, addOn)
    }

    /**
     * Add addons.
     * @param addons map of addons
     */
    override fun addAddOns(addons: Map<String, IAddOn>) {
        mAddOn.addAddOns(addons)
    }

    /**
     * Remove addon.
     * @param type type of addon
     */
    override fun removeAddOn(type: String) {
        mAddOn.removeAddOn(type)
    }

    /**
     * Remove addons.
     * @param types types of addons
     */
    override fun removeAddOns(types: List<String>) {
        mAddOn.removeAddOns(types)
    }

    /**
     * Clear addons.
     */
    override fun clearAddOns() {
        mAddOn.clearAddOns()
    }
}