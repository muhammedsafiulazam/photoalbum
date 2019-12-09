package com.muhammedsafiulazam.photoalbum.view

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
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

open class BaseView : AppCompatActivity(), IAddOn {
    companion object {
        const val KEY_DATA: String = "KEY_DATA"
    }

    private var mReceiveChannel: ReceiveChannel<Event>? = null
    private var mActivityModel: BaseViewModel? = null
    private val mAddOn: AddOn = AddOn()

    private var isViewReady: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Essential addons.
        addAddOn(AddOnType.VIEW_MANAGER, AddOnManager.getAddOn(AddOnType.VIEW_MANAGER)!!)
        addAddOn(AddOnType.EVENT_MANAGER, AddOnManager.getAddOn(AddOnType.EVENT_MANAGER)!!)

        isViewReady = false
    }

    override fun onStart() {
        super.onStart()

        if (!isViewReady) {
            isViewReady = true
            mActivityModel?.onCreate()
        }

        mActivityModel?.onStart()
        onActivateActivity()
    }

    override fun onResume() {
        super.onResume()
        mActivityModel?.onResume()
        onActivateActivity()
    }

    override fun onPause() {
        onDeactivateActivity()
        mActivityModel?.onPause()
        super.onPause()
    }

    override fun onStop() {
        onDeactivateActivity()
        mActivityModel?.onStop()
        super.onStop()
    }

    /**
     * Get associated data.
     * @return associated data
     */
    fun getData() : Parcelable? {
        return intent?.getParcelableExtra(KEY_DATA)
    }

    /**
     * Get associated activity model.
     * @return associated activity model
     */
    fun getActivityModel() : BaseViewModel? {
        return mActivityModel
    }

    /**
     * Set associated activity model.
     * @param activityModel associated activity model
     */
    fun setActivityModel(activityModel: Class<out BaseViewModel>) {
        mActivityModel = ViewModelProviders.of(this).get(activityModel)
    }

    private fun onActivateActivity() {
        val activityManager: IViewManager? = getAddOn(AddOnType.VIEW_MANAGER) as IViewManager?
        activityManager?.onStartView(this)
    }

    private fun onDeactivateActivity() {
        val activityManager: IViewManager? = getAddOn(AddOnType.VIEW_MANAGER) as IViewManager?
        activityManager?.onStopView(this)
    }

    override fun onDestroy() {
        clearAddOns()
        mActivityModel?.onDestroy()
        receiveEvents(false)
        super.onDestroy()
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