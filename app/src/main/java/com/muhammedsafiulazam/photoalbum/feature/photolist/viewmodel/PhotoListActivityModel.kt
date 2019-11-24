package com.muhammedsafiulazam.photoalbum.feature.photolist.viewmodel

import android.content.Context
import android.text.TextUtils
import com.muhammedsafiulazam.photoalbum.activity.BaseActivityModel
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.event.IEventManager
import com.muhammedsafiulazam.photoalbum.feature.albumlist.model.Album
import com.muhammedsafiulazam.photoalbum.feature.photolist.event.PhotoListEventType
import com.muhammedsafiulazam.photoalbum.network.service.IServiceManager

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

class PhotoListActivityModel : BaseActivityModel() {

    private lateinit var mEventManager: IEventManager
    private lateinit var mServiceManager: IServiceManager
    private lateinit var mAlbum: Album

    override fun onCreateActivity() {
        super.onCreateActivity()

        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        mServiceManager = getAddOn(AddOnType.SERVICE_MANAGER) as IServiceManager

        receiveEvents(true)
    }

    private fun updateLoader(busy: Boolean) {
        val event = Event(PhotoListEventType.UPDATE_LOADER, busy, null)
        mEventManager.send(event)
    }

    private fun updateMessage(error: String?) {
        val event = Event(PhotoListEventType.UPDATE_MESSAGE, error, null)
        mEventManager.send(event)
    }

    fun requestLoadPhotos() {
        // Show loader.
        updateLoader(false)

        responseLoadPhotos(mAlbum.photos)
    }

    private fun responseLoadPhotos(response: Any?) {
        val event = Event(PhotoListEventType.RESPONSE_LOAD_PHOTOS, response, null)
        mEventManager.send(event)
    }

    override fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(PhotoListEventType.REQUEST_LOAD_PHOTOS, event.type)) {
            mAlbum = event.data as Album
            requestLoadPhotos()
        }
    }

    override fun onDestroyActivity() {
        receiveEvents(false)
        super.onDestroyActivity()
    }
}