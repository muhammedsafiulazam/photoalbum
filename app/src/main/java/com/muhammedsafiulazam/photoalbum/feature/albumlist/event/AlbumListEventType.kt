package com.muhammedsafiulazam.photoalbum.feature.albumlist.event

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

object AlbumListEventType {
    // Tag.
    const val TAG: String = "ALBUMLIST_EVENT_TYPE_"

    // Album list event types.
    const val UPDATE_LOADER: String = TAG + "UPDATE_BUSY"
    const val UPDATE_MESSAGE: String = TAG + "UPDATE_ERROR"

    const val REQUEST_LOAD_ALBUMS: String = TAG + "REQUEST_DATA"
    const val RESPONSE_LOAD_ALBUMS: String = TAG + "UPDATE_DATA"
}