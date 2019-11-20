package com.muhammedsafiulazam.photoalbum.database.photo

import com.muhammedsafiulazam.photoalbum.addon.AddOn
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.database.photo.event.PhotoDatabaseEventType
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.event.IEventManager
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.squareup.sqldelight.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhotoDatabase(val db: PhotoDB) : AddOn(), IPhotoDatabase {

    override fun getPhotos() {
        GlobalScope.launch(Dispatchers.IO) {
            var photos: ArrayList<Photo> = arrayListOf()

            val photoTableQueries: Query<PhotoTable> = db.photoTableQueries.selectAll()
            photoTableQueries.executeAsList().forEach { photoQuery ->

                val photo = Photo(
                    photoQuery.albumId,
                    photoQuery.id,
                    photoQuery.title,
                    photoQuery.url,
                    photoQuery.thumbnailUrl
                )
                photos.add(photo)
            }

            val event = Event(PhotoDatabaseEventType.GET_PHOTOS, photos, null)
            val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
            eventManager?.send(event)
        }
    }

    override fun savePhotos(photos: List<Photo>) {
        GlobalScope.launch(Dispatchers.IO) {
            photos.forEach { photo ->
                db.photoTableQueries.insert(
                    photo.albumId,
                    photo.id!!,
                    photo.title,
                    photo.url,
                    photo.thumbnailUrl
                )
            }

            val event = Event(PhotoDatabaseEventType.SAVE_PHOTOS, photos, null)
            val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
            eventManager?.send(event)
        }
    }

    override fun cleanPhotos() {
        GlobalScope.launch(Dispatchers.IO) {
            db.photoTableQueries.deleteAll()

            val event = Event(PhotoDatabaseEventType.CLEAN_PHOTOS, null, null)
            val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
            eventManager?.send(event)
        }
    }
}