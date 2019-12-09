package com.muhammedsafiulazam.photoalbum.feature.photolist.viewmodel

import android.text.TextUtils
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.view.BaseViewModel
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.event.IEventManager
import com.muhammedsafiulazam.photoalbum.feature.albumlist.model.Album
import com.muhammedsafiulazam.photoalbum.feature.photolist.event.PhotoListEventType
import com.muhammedsafiulazam.photoalbum.feature.photolist.model.PhotoListModel
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.muhammedsafiulazam.photoalbum.network.service.IServiceManager

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

class PhotoListActivityModel : BaseViewModel() {

    private lateinit var mEventManager: IEventManager
    private lateinit var mAlbum: Album

    override fun onCreate() {
        super.onCreate()

        setModel(PhotoListModel::class.java)

        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager

        // Enable receiving events.
        receiveEvents(true)
    }

    /**
     * Update loader.
     * @param show flag
     */
    private fun updateLoader(show: Boolean) {
        val event = Event(PhotoListEventType.VIEW_UPDATE_LOADER, show, null)
        mEventManager.send(event)
    }

    /**
     * Update message.
     * @param message message
     */
    private fun updateMessage(message: Any?) {
        val event = Event(PhotoListEventType.VIEW_UPDATE_MESSAGE, message, null)
        mEventManager.send(event)
    }

    /**
     * Request to load photos.
     */
    fun requestLoadPhotos(album: Album) {
        // Show loader.
        updateLoader(false)

        val event = Event(PhotoListEventType.MODEL_REQUEST_LOAD_PHOTOS, album, null)
        mEventManager.send(event)
    }

    /**
     * Response with photos.
     * @param response response
     */
    private fun responseLoadPhotos(photos: List<Photo>?) {
        val event = Event(PhotoListEventType.VIEWMODEL_RESPONSE_LOAD_PHOTOS, photos, null)
        mEventManager.send(event)
    }

    /**
     * Receive and handle events.
     * @param event event
     */
    override fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(PhotoListEventType.VIEWMODEL_REQUEST_LOAD_PHOTOS, event.type)) {
            mAlbum = event.data as Album
            requestLoadPhotos(mAlbum)
        } else if (TextUtils.equals(PhotoListEventType.MODEL_RESPONSE_LOAD_PHOTOS, event.type)) {
            // Hide loader.
            updateLoader(false)

            if (event.error != null) {
                // Show message.
                updateMessage(R.string.photolist_error_load)
            } else {
                // Send reponse.
                responseLoadPhotos(event.data as List<Photo>?)
            }
        }
    }

    /**
     * On destroy, clean.
     */
    override fun onDestroy() {
        // Disable receiving events.
        receiveEvents(false)
        super.onDestroy()
    }
}