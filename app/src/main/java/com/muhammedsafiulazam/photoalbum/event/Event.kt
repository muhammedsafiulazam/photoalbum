package com.muhammedsafiulazam.photoalbum.event

import com.muhammedsafiulazam.photoalbum.network.model.Error

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

data class Event (val type: String, val data: Any? = null, val error: Error? = null)