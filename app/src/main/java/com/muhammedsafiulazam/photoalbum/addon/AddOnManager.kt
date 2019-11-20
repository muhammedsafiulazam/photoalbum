package com.muhammedsafiulazam.photoalbum.addon

import com.muhammedsafiulazam.photoalbum.activity.ActivityManager
import com.muhammedsafiulazam.photoalbum.database.DatabaseManager
import com.muhammedsafiulazam.photoalbum.event.EventManager
import com.muhammedsafiulazam.photoalbum.network.queue.QueueManager
import com.muhammedsafiulazam.photoalbum.network.retrofit.RetrofitManager
import com.muhammedsafiulazam.photoalbum.network.server.ServerManager
import com.muhammedsafiulazam.photoalbum.network.service.ServiceManager

object AddOnManager : IAddOnManager {

    private val mAddOn: IAddOn by lazy {
        val addOn = AddOn()
        addAddOns(addOn)
        addOn
    }

    override fun getAddOn(type: String): IAddOn? {
        return mAddOn.getAddOn(type)
    }

    override fun getAddOns(): Map<String, IAddOn> {
        return mAddOn.getAddOns()
    }

    override fun addAddOn(type: String, addOn: IAddOn) {
        mAddOn.addAddOn(type, addOn)
    }

    override fun addAddOns(addons: Map<String, IAddOn>) {
        mAddOn.addAddOns(addons)
    }

    override fun removeAddOn(type: String) {
        mAddOn.removeAddOn(type)
    }

    override fun removeAddOns(types: List<String>) {
        mAddOn.removeAddOns(types)
    }

    override fun clearAddOns() {
        mAddOn.clearAddOns()
    }

    private fun addAddOns(addOn: IAddOn) {

        val activityManager = ActivityManager()
        val serverManager = ServerManager()
        val serviceManager = ServiceManager()
        val eventManager = EventManager()
        val retrofitManager = RetrofitManager()
        val queueManager = QueueManager()
        val databaseManager = DatabaseManager()

        // Now assign individually.

        // Service manager.
        serviceManager.addAddOn(AddOnType.SERVER_MANAGER, serverManager)
        serviceManager.addAddOn(AddOnType.EVENT_MANAGER, eventManager)
        serviceManager.addAddOn(AddOnType.QUEUE_MANAGER, queueManager)

        // Server manager.
        serverManager.addAddOn(AddOnType.RETROFIT_MANAGER, retrofitManager)

        // Database manager.
        databaseManager.addAddOn(AddOnType.EVENT_MANAGER, eventManager)

        addOn.addAddOn(AddOnType.ACTIVITY_MANAGER, activityManager)
        addOn.addAddOn(AddOnType.SERVER_MANAGER, serverManager)
        addOn.addAddOn(AddOnType.SERVICE_MANAGER, serviceManager)
        addOn.addAddOn(AddOnType.EVENT_MANAGER, eventManager)
        addOn.addAddOn(AddOnType.RETROFIT_MANAGER, retrofitManager)
        addOn.addAddOn(AddOnType.QUEUE_MANAGER, queueManager)
        addOn.addAddOn(AddOnType.DATABASE_MANAGER, databaseManager)
    }
}