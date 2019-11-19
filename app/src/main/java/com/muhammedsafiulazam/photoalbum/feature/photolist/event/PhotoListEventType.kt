package com.muhammedsafiulazam.photoalbum.feature.photolist.event

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

object PhotoListEventType {
    // Tag.
    const val TAG: String = "PHOTOLIST_EVENT_TYPE_"

    // Album list event types.
    const val UPDATE_LOADER: String = TAG + "UPDATE_BUSY"
    const val UPDATE_MESSAGE: String = TAG + "UPDATE_ERROR"

    const val REQUEST_LOAD_PHOTOS: String = TAG + "REQUEST_LOAD_PHOTOS"
    const val RESPONSE_LOAD_PHOTOS: String = TAG + "RESPONSE_LOAD_PHOTOS"
}