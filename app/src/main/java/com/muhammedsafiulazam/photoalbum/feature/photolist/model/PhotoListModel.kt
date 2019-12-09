package com.muhammedsafiulazam.photoalbum.feature.photolist.model

import android.text.TextUtils
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.database.IDatabaseManager
import com.muhammedsafiulazam.photoalbum.database.photo.event.PhotoDatabaseEventType
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.event.IEventManager
import com.muhammedsafiulazam.photoalbum.feature.albumlist.event.AlbumListEventType
import com.muhammedsafiulazam.photoalbum.feature.albumlist.model.Album
import com.muhammedsafiulazam.photoalbum.feature.photolist.event.PhotoListEventType
import com.muhammedsafiulazam.photoalbum.network.model.Error
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.muhammedsafiulazam.photoalbum.network.service.IServiceManager
import com.muhammedsafiulazam.photoalbum.network.service.photo.event.PhotoServiceEventType
import com.muhammedsafiulazam.photoalbum.view.BaseModel

class PhotoListModel : BaseModel() {

    private lateinit var mEventManager: IEventManager

    override fun onCreate() {
        super.onCreate()

        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager

        // Enable receiving events.
        receiveEvents(true)
    }

    /**
     * Response with photos.
     * @param photos photos.
     * @param error error
     */
    private fun responseLoadPhotos(photos: List<Photo>, error: Error?) {
        val event = Event(PhotoListEventType.MODEL_RESPONSE_LOAD_PHOTOS, photos, error)
        mEventManager.send(event)
    }

    /**
     * Receive and handle events.
     * @param event event
     */
    @Suppress("UNCHECKED_CAST")
    override fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(PhotoListEventType.MODEL_REQUEST_LOAD_PHOTOS, event.type)) {
            responseLoadPhotos((event.data as Album).photos, event.error)
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