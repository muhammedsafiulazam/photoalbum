package com.muhammedsafiulazam.photoalbum.utils

import com.muhammedsafiulazam.photoalbum.network.model.Error

object ErrorUtils {

    fun noConnectivity() : Error {
        return Error(503, "No connectivity.")
    }
}