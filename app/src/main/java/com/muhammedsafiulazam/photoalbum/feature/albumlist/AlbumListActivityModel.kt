package com.muhammedsafiulazam.photoalbum.feature.albumlist

import android.text.TextUtils
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.activity.BaseActivityModel
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.event.IEventManager
import com.muhammedsafiulazam.photoalbum.feature.albumlist.event.AlbumListEventType
import com.muhammedsafiulazam.photoalbum.feature.albumlist.model.Album
import com.muhammedsafiulazam.photoalbum.network.event.book.PhotoEventType
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.muhammedsafiulazam.photoalbum.network.service.IServiceManager

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

class AlbumListActivityModel : BaseActivityModel() {
    private lateinit var eventManager: IEventManager
    private lateinit var serviceManager: IServiceManager

    override fun onCreateActivity() {
        super.onCreateActivity()

        eventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        serviceManager = getAddOn(AddOnType.SERVICE_MANAGER) as IServiceManager

        receiveEvents(true)
        requestLoadAlbums()
    }

    private fun updateLoader(busy: Boolean) {
        val event = Event(AlbumListEventType.UPDATE_LOADER, busy, null)
        eventManager.send(event)
    }

    private fun updateMessage(error: String?) {
        val event = Event(AlbumListEventType.UPDATE_MESSAGE, error, null)
        eventManager.send(event)
    }

    fun requestLoadAlbums() {
        // Show loader.
        updateLoader(true)

        // Load photos.
        serviceManager.getPhotoService().getPhotos()
    }

    private fun responseLoadAlbums(response: Any?) {
        var albumList: MutableMap<String, Album> = mutableMapOf()

        // Parse album.
        val photoList: List<Photo>? = response as? List<Photo>
        photoList?.forEach { photo ->
            if (!albumList.containsKey(photo.albumId)) {
                albumList.put(photo.albumId!!, Album(photo.albumId, mutableListOf()))
            }
            val album: Album = albumList.get(photo.albumId) as Album
            album.photos.add(photo)
        }

        val event = Event(AlbumListEventType.RESPONSE_LOAD_ALBUMS, albumList.values.toList(), null)
        eventManager.send(event)
    }

    override fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(AlbumListEventType.REQUEST_LOAD_ALBUMS, event.type)) {
            requestLoadAlbums()
        } else if (TextUtils.equals(PhotoEventType.GET_PHOTOS, event.type)) {
            // Hide loader.
            updateLoader(false)

            if (event.error != null) {
                // Show message.
                updateMessage(getActivity()?.getString(R.string.albumlist_error_load))
            } else {
                // Send reponse.
                responseLoadAlbums(event.data)
            }
        }
    }

    override fun onDestroyActivity() {
        receiveEvents(false)
        super.onDestroyActivity()
    }
}