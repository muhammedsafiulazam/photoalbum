package com.muhammedsafiulazam.photoalbum.feature.albumlist.model

import android.text.TextUtils
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.database.IDatabaseManager
import com.muhammedsafiulazam.photoalbum.database.photo.event.PhotoDatabaseEventType
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.event.IEventManager
import com.muhammedsafiulazam.photoalbum.feature.albumlist.event.AlbumListEventType
import com.muhammedsafiulazam.photoalbum.network.model.Error
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.muhammedsafiulazam.photoalbum.network.service.IServiceManager
import com.muhammedsafiulazam.photoalbum.network.service.photo.event.PhotoServiceEventType
import com.muhammedsafiulazam.photoalbum.view.BaseModel

class AlbumListModel : BaseModel() {

    private lateinit var mEventManager: IEventManager
    private lateinit var mServiceManager: IServiceManager
    private lateinit var mDatabaseManager: IDatabaseManager

    override fun onCreate() {
        super.onCreate()

        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        mServiceManager = getAddOn(AddOnType.SERVICE_MANAGER) as IServiceManager
        mDatabaseManager = getAddOn(AddOnType.DATABASE_MANAGER) as IDatabaseManager

        // Enable receiving events.
        receiveEvents(true)
    }

    /**
     * Response with albums.
     * @param photos photos.
     * @param error error
     */
    private fun responseLoadAlbums(photos: List<Photo>?, error: Error?) {

        var albums: MutableMap<String, Album> = mutableMapOf()

        // Parse albums.
        photos?.forEach { photo ->
            if (!albums.containsKey(photo.albumId)) {
                albums.put(photo.albumId!!, Album(photo.albumId, mutableListOf()))
            }
            val album: Album = albums.get(photo.albumId) as Album
            album.photos.add(photo)
        }

        val event = Event(AlbumListEventType.MODEL_RESPONSE_LOAD_ALBUMS, albums.values.toList(), error)
        mEventManager.send(event)
    }

    /**
     * Receive and handle events.
     * @param event event
     */
    @Suppress("UNCHECKED_CAST")
    override fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(AlbumListEventType.MODEL_REQUEST_LOAD_ALBUMS, event.type)) {
            mServiceManager.getPhotoService().getPhotos()
        } else if (TextUtils.equals(PhotoServiceEventType.GET_PHOTOS, event.type)) {
            if (event.error != null) {
                // Load from database.
                mDatabaseManager.getPhotoDatabase().getPhotos()
            } else {
                val photoList: List<Photo>? = event.data as List<Photo>?

                // Save in database.
                if (!photoList.isNullOrEmpty()) {
                    mDatabaseManager.getPhotoDatabase().savePhotos(photoList)
                }

                // Send reponse.
                responseLoadAlbums(photoList, event.error)
            }
        } else if (TextUtils.equals(PhotoDatabaseEventType.GET_PHOTOS, event.type)) {
            // Send reponse.
            responseLoadAlbums(event.data as List<Photo>?, event.error)
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