package com.muhammedsafiulazam.photoalbum.network.service.photo

import com.muhammedsafiulazam.photoalbum.addon.AddOn
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.event.IEventManager
import com.muhammedsafiulazam.photoalbum.network.model.Error
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.muhammedsafiulazam.photoalbum.network.queue.IQueueManager
import com.muhammedsafiulazam.photoalbum.network.server.IServerManager
import com.muhammedsafiulazam.photoalbum.network.service.photo.event.PhotoServiceEventType
import com.muhammedsafiulazam.photoalbum.utils.ConnectivityUtils
import com.muhammedsafiulazam.photoalbum.utils.ErrorUtils
import retrofit2.Call
import retrofit2.Response

/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

class PhotoService : AddOn(), IPhotoService {
    /**
     * Get photos.
     */
    override fun getPhotos() {
        // Server manager.
        val serverManager: IServerManager = getAddOn(AddOnType.SERVER_MANAGER) as IServerManager

        // Service call.
        val call: Call<List<Photo>> = serverManager.getPhotoServer().getPhotoCall().getPhotos()

        // Queue manager.
        val queueManager: IQueueManager = getAddOn(AddOnType.QUEUE_MANAGER) as IQueueManager

        if (ConnectivityUtils.isOnline()) {
            // Push in queue.
            queueManager.execute(call as Call<Any>, callback = { response: Response<Any> ->
                var photos: List<Photo>? = null
                var error: Error? = null

                if (response.isSuccessful()) {
                    photos = (response as Response<List<Photo>>).body()
                } else {
                    error = Error(response.code(), response.errorBody()?.toString())
                }

                val event = Event(PhotoServiceEventType.GET_PHOTOS, photos, error)
                val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
                eventManager!!.send(event)
            })
        } else {
            val event = Event(PhotoServiceEventType.GET_PHOTOS, null, ErrorUtils.noConnectivity())
            val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
            eventManager!!.send(event)
        }
    }
}