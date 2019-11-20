package com.muhammedsafiulazam.photoalbum.feature.albumlist

import android.text.TextUtils
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.activity.BaseActivityModel
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.database.IDatabaseManager
import com.muhammedsafiulazam.photoalbum.database.photo.event.PhotoDatabaseEventType
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.event.IEventManager
import com.muhammedsafiulazam.photoalbum.feature.albumlist.event.AlbumListEventType
import com.muhammedsafiulazam.photoalbum.feature.albumlist.model.Album
import com.muhammedsafiulazam.photoalbum.network.service.photo.event.PhotoServiceEventType
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.muhammedsafiulazam.photoalbum.network.service.IServiceManager

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

class AlbumListActivityModel : BaseActivityModel() {
    private lateinit var mEventManager: IEventManager
    private lateinit var mServiceManager: IServiceManager
    private lateinit var mDatabaseManager: IDatabaseManager

    override fun onCreateActivity() {
        super.onCreateActivity()

        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        mServiceManager = getAddOn(AddOnType.SERVICE_MANAGER) as IServiceManager
        mDatabaseManager = getAddOn(AddOnType.DATABASE_MANAGER) as IDatabaseManager

        receiveEvents(true)
    }

    private fun updateLoader(busy: Boolean) {
        val event = Event(AlbumListEventType.UPDATE_LOADER, busy, null)
        mEventManager.send(event)
    }

    private fun updateMessage(error: String?) {
        val event = Event(AlbumListEventType.UPDATE_MESSAGE, error, null)
        mEventManager.send(event)
    }

    fun requestLoadAlbums() {
        // Show loader.
        updateLoader(true)

        // Load photos.
        mServiceManager.getPhotoService().getPhotos()
    }

    private fun responseLoadAlbums(photoList: List<Photo>?) {
        var albumList: MutableMap<String, Album> = mutableMapOf()

        // Parse album.
        photoList?.forEach { photo ->
            if (!albumList.containsKey(photo.albumId)) {
                albumList.put(photo.albumId!!, Album(photo.albumId, mutableListOf()))
            }
            val album: Album = albumList.get(photo.albumId) as Album
            album.photos.add(photo)
        }

        val event = Event(AlbumListEventType.RESPONSE_LOAD_ALBUMS, albumList.values.toList(), null)
        mEventManager.send(event)
    }

    override fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(AlbumListEventType.REQUEST_LOAD_ALBUMS, event.type)) {
            requestLoadAlbums()
        } else if (TextUtils.equals(PhotoServiceEventType.GET_PHOTOS, event.type)) {
            // Hide loader.
            updateLoader(false)

            if (event.error != null) {
                // Hide loader.
                updateLoader(true)

                // Load from database.
                mDatabaseManager.getPhotoDatabase().getPhotos()
            } else {
                val photoList: List<Photo>? = event.data as? List<Photo>

                // Save in database.
                if (!photoList.isNullOrEmpty()) {
                    mDatabaseManager.getPhotoDatabase().savePhotos(photoList)
                }

                // Send reponse.
                responseLoadAlbums(photoList)
            }
        } else if (TextUtils.equals(PhotoDatabaseEventType.GET_PHOTOS, event.type)) {
            // Hide loader.
            updateLoader(false)

            if (event.error != null) {
                // Show message.
                updateMessage(getActivity()?.getString(R.string.albumlist_error_load))
            } else {
                // Send reponse.
                responseLoadAlbums(event.data as? List<Photo>)
            }
        }
    }

    override fun onDestroyActivity() {
        receiveEvents(false)
        super.onDestroyActivity()
    }
}